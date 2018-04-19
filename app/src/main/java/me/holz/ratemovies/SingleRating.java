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

import java.util.ArrayList;
import java.util.List;

public class SingleRating extends AppCompatActivity {

    public List<RatingItem> ratings = new ArrayList<>();

    private String ratingCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_rating);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ratingCategory = getIntent().getExtras().getString("category");

        toolbar.setTitle(ratingCategory);


        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        RatingAdapter adapter = new RatingAdapter(ratings);
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
        ratings.add(new RatingItem("gas", 3));
        ratings.add(new RatingItem("werner", 6));


    }
}
