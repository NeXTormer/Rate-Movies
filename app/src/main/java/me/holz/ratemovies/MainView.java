package me.holz.ratemovies;

import android.content.Intent;
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

                intent.putExtra("movie", movieList.get(pos).title);
                intent.putExtra("image", movieList.get(pos).image);
                intent.putExtra("info", movieList.get(pos).infolong);

                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        loadMovieDataFormServer();
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
                String infolong = jo.getString("infolong");
                String image = jo.getString("image");

                movieList.add(new MovieItem(title, genres, releasedate, info, image, infolong));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    public void changeName(MenuItem menuitem)
    {
        Toast.makeText(getApplicationContext(), "Change Name", Toast.LENGTH_SHORT).show();
    }

}
