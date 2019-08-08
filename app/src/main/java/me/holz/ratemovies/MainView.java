package me.holz.ratemovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.snapchat.kit.sdk.SnapLogin;
import com.snapchat.kit.sdk.login.models.MeData;
import com.snapchat.kit.sdk.login.models.UserDataResponse;
import com.snapchat.kit.sdk.login.networking.FetchUserDataCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import me.holz.ratemovies.util.loadJSON;

public class MainView extends AppCompatActivity {

    private List<MovieItem> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MovieAdapter mAdapter;
    private SwipeRefreshLayout swipelayout;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        SharedPreferences prefs = getSharedPreferences("key", MODE_PRIVATE);
        String username = prefs.getString("username", "Guest");

        setTitle("Rate-Movies (" + username + ")");

        swipelayout = findViewById(R.id.swiperefresh);
        swipelayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        loadMovieDataFormServer();
                        swipelayout.setRefreshing(false);

                        boolean isUserLoggedIn = SnapLogin.isUserLoggedIn(getApplicationContext());

                        if(isUserLoggedIn)
                        {
                            String query = "{me{externalId}}";
                            SnapLogin.fetchUserData(getApplicationContext(), query, null, new FetchUserDataCallback() {
                                @Override
                                public void onSuccess(@Nullable UserDataResponse userDataResponse) {
                                    if (userDataResponse == null || userDataResponse.getData() == null) {
                                        return;
                                    }

                                    MeData meData = userDataResponse.getData().getMe();
                                    if (meData == null) {
                                        return;
                                    }

                                    String userid = userDataResponse.getData().getMe().getExternalId();
                                    setTitle("Rate-Movies (" + userid + ")");
                                    System.out.println("External User ID: " + userid);
                                }

                                @Override
                                public void onFailure(boolean isNetworkError, int statusCode) {
                                    System.out.println("External User ID Error: " + statusCode);
                                }
                            });
                        }

                    }
                }
        );






        mAdapter = new MovieAdapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new MainViewTouchListener(this, recyclerView ,new MainViewTouchListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), SingleMovie.class);
                int pos = recyclerView.getChildLayoutPosition(view);

                intent.putExtra("title", movieList.get(pos).title);
                intent.putExtra("releasedate", movieList.get(pos).releasedate);
                intent.putExtra("avgrating", movieList.get(pos).averagerating);
                intent.putExtra("imdb", movieList.get(pos).imdb);
                intent.putExtra("watchdate", movieList.get(pos).watchdate);
                intent.putExtra("image", movieList.get(pos).image);
                intent.putExtra("movieid", movieList.get(pos).id);


                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        getIntent().setAction("1oncreate");
        loadMovieDataFormServer();
    }

    @Override
    public void onResume()
    {
        super.onResume();


    }


    private void loadMovieDataFormServer()
    {
        movieList.clear();
        try
        {
            loadJSON lj = new loadJSON("http://faoiltiarna.ddns.net:4443/ratemovies/movielist");
            lj.thread.start();
            lj.thread.join();
            String jsonraw = lj.result;

            System.out.println(jsonraw);
            JSONArray ja = new JSONArray(jsonraw);
            for(int i = 0; i < ja.length(); i++)
            {
                JSONObject jo = ja.getJSONObject(i);
                String title = jo.getString("title");
                String genres = jo.getString("genres");
                String releasedate = jo.getString("releasedate");
                String info = jo.getString("info");
                String image = jo.getString("image");
                double imdb = jo.getDouble("imdb");

                String watchdate = jo.getString("watchdate");
                int id = jo.getInt("id");

                double avgrating = loadAverageRating(id);
                //normalize rating to 10
                avgrating /= 8.0;
                avgrating *= 10;

                avgrating = Math.round(avgrating * 10.0) / 10.0;

                movieList.add(new MovieItem(id, title, genres, releasedate, watchdate, info, image, imdb, avgrating));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mAdapter.notifyDataSetChanged();
    }

    private double loadAverageRating(int id)
    {
        loadJSON lj = new loadJSON("http://faoiltiarna.ddns.net:4443/ratemovies/averagerating/" + id);
        lj.thread.start();
        try {
            lj.thread.join();
            JSONArray ja = new JSONArray(lj.result);
            JSONObject jo = ja.getJSONObject(0);

            double rating = jo.getDouble("averagerating");

            return rating;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    public void changeName(MenuItem menuitem)
    {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

}
