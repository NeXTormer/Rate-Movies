package me.holz.ratemovies;

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


        RatingBar rb_story = findViewById(R.id.rb_story);
        RatingBar rb_writing = findViewById(R.id.rb_writing);
        RatingBar rb_music = findViewById(R.id.rb_music);
        RatingBar rb_acting = findViewById(R.id.rb_actors);
        RatingBar rb_effects = findViewById(R.id.rb_effects);
        RatingBar rb_camera = findViewById(R.id.rb_camera);
        RatingBar rb_entertaining = findViewById(R.id.rb_entertaining);
        RatingBar rb_overall = findViewById(R.id.rb_overall);
        RatingBar rb_expectedoverall = findViewById(R.id.rb_expectedoverall);

    }

}
