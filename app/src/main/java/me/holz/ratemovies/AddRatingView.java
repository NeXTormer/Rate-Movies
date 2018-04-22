package me.holz.ratemovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import me.holz.ratemovies.util.loadJSON;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import java.util.prefs.PreferencesFactory;

public class AddRatingView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rating_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Preferences prefs = Preferences.userRoot();



        setTitle(getIntent().getExtras().getString("movie"));


        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final RatingBar rb_story = findViewById(R.id.rb_story);
        final RatingBar rb_writing = findViewById(R.id.rb_writing);
        final RatingBar rb_music = findViewById(R.id.rb_music);
        final RatingBar rb_acting = findViewById(R.id.rb_actors);
        final RatingBar rb_effects = findViewById(R.id.rb_effects);
        final RatingBar rb_camera = findViewById(R.id.rb_camera);
        final RatingBar rb_entertaining = findViewById(R.id.rb_entertaining);
        final RatingBar rb_overall = findViewById(R.id.rb_overall);
        final RatingBar rb_expectedoverall = findViewById(R.id.rb_expectedoverall);

        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double story = rb_story.getRating();
                double writing = rb_writing.getRating();
                double music = rb_music.getRating();
                double acting = rb_acting.getRating();
                double effects = rb_effects.getRating();
                double camera = rb_camera.getRating();
                double entertaining = rb_entertaining.getRating();
                double overall = rb_overall.getRating();
                double expectedoverall = rb_expectedoverall.getRating();

                int movieid = getIntent().getExtras().getInt("movieid");

                SharedPreferences prefs = getSharedPreferences("key", MODE_PRIVATE);
                String apikey = prefs.getString("apikey", "");

                String url = "http://faoiltiarna.ddns.net:4443/ratemovies/rate/" + apikey + "/" + movieid + "/" + story + "/" + writing + "/" + music +"/" + acting + "/" + effects + "/" + camera + "/" + entertaining + "/" + overall + "/" + expectedoverall;
                loadJSON lj = new loadJSON(url);

                lj.thread.start();

                onBackPressed();

            }
        });

    }

}
