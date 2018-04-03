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

        final EditText nametext = findViewById(R.id.nametext);
        Button submitbutton = findViewById(R.id.button);
        final Preferences prefs = Preferences.userRoot();

        nametext.setText(prefs.get("name", ""));

        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs.put("name", nametext.getText().toString());
                try {
                    prefs.flush();
                } catch (BackingStoreException e) {
                    e.printStackTrace();
                }
            }
        });


        RatingBar ratingbar = findViewById(R.id.ratingBar2);

        setTitle(getIntent().getExtras().getString("movie"));



        /* Back Button crashes app when pressed, tempoarily disabled */
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
