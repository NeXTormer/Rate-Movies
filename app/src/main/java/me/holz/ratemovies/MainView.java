package me.holz.ratemovies;

import android.content.Intent;
import android.content.SharedPreferences;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        SharedPreferences prefs = getSharedPreferences("key", MODE_PRIVATE);
        String username = prefs.getString("username", "Guest");

        setTitle("Rate-Movies (" + username + ")");



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

        String action = getIntent().getAction();
        if(action == null || !action.equals("1oncreate")) {
            finish();
            startActivity(getIntent());
        }
        else
        {
            getIntent().setAction(null);
        }
    }

    private void loadMovieDataFormServer()
    {
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
