package me.holz.ratemovies;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

public class AddRatingView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rating_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        RatingBar ratingbar = findViewById(R.id.ratingBar2);

        setTitle(getIntent().getExtras().getString("movie"));
        /* Back Button crashes app when pressed, tempoarily disabled */
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
