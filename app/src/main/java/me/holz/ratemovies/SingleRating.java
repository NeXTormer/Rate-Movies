package me.holz.ratemovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.holz.ratemovies.util.loadJSON;

public class SingleRating extends AppCompatActivity {

    public List<RatingItem> ratings = new ArrayList<>();

    private String ratingCategory;
    private RatingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_rating);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ratingCategory = getIntent().getExtras().getString("category");

        if(ratingCategory.equalsIgnoreCase("actors")) ratingCategory = "acting";
        else if(ratingCategory.equalsIgnoreCase("expectedoverall")) ratingCategory = "Expected overall score";
        else if(ratingCategory.equalsIgnoreCase("overall")) ratingCategory = "Overall score";
        else if(ratingCategory.equalsIgnoreCase("camera")) ratingCategory = "Camera and scenes";

        setTitle(ratingCategory.substring(0, 1).toUpperCase() + ratingCategory.substring(1));


        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        adapter = new RatingAdapter(ratings);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        loadRatingsFromAPI();

        adapter.notifyDataSetChanged();
    }
    private void loadRatingsFromAPI()
    {
        try
        {
            String url = "http://faoiltiarna.ddns.net:4443/ratemovies/singlerating/" + getIntent().getExtras().getInt("movieid") + "/" + getIntent().getExtras().getString("category");
            loadJSON lj = new loadJSON(url);
            lj.thread.start();
            lj.thread.join();
            String jsonraw = lj.result;

            System.out.println(jsonraw);
            JSONArray ja = new JSONArray(jsonraw);
            for(int i = 0; i < ja.length(); i++)
            {
                JSONObject jo = ja.getJSONObject(i);
                String name = jo.getString("username");
                double rating = jo.getDouble("rating");

                ratings.add(new RatingItem(name, (float) rating));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        adapter.notifyDataSetChanged();
    }
}
