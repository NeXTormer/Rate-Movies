package me.holz.ratemovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.holz.ratemovies.util.loadJSON;

public class SingleMovie extends AppCompatActivity {

    public String username;
    public int userid = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_movie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getIntent().getExtras().getString("title"));

        ImageView titleimage = findViewById(R.id.titleimage);

        Picasso.get().load(getIntent().getExtras().getString("image")).into(titleimage);

        TextView infolong = findViewById(R.id.info);

        double imdb = getIntent().getExtras().getDouble("imdb");
        String watchdate = getIntent().getExtras().getString("watchdate");
        String releasedate = getIntent().getExtras().getString("releasedate");
        double averagerating = getIntent().getExtras().getDouble("avgrating");

        infolong.setText("Releasedate: " + releasedate + "\nWatchdate: " + watchdate + "\nAverage Rating: " + averagerating + "\nIMDB: " + imdb);


        RatingBar rb_story = findViewById(R.id.rb_story);
        RatingBar rb_writing = findViewById(R.id.rb_writing);
        RatingBar rb_music = findViewById(R.id.rb_music);
        RatingBar rb_acting = findViewById(R.id.rb_actors);
        RatingBar rb_effects = findViewById(R.id.rb_effects);
        RatingBar rb_camera = findViewById(R.id.rb_camera);
        RatingBar rb_entertaining = findViewById(R.id.rb_entertaining);
        RatingBar rb_overall = findViewById(R.id.rb_overall);
        RatingBar rb_expectedoverall = findViewById(R.id.rb_expectedoverall);

        loadRatingFromAPI(rb_story, rb_writing, rb_music, rb_acting, rb_effects, rb_camera, rb_entertaining, rb_overall, rb_expectedoverall);


        rb_story.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent intent = new Intent(getApplicationContext(), SingleRating.class);
                intent.putExtra("category", "story");
                intent.putExtra("movieid", getIntent().getExtras().getString("movieid"));
                startActivity(intent);

                return false;
            }
        });


        rb_writing.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Intent intent = new Intent(getApplicationContext(), SingleRating.class);
                intent.putExtra("category", "writing");
                intent.putExtra("movieid", getIntent().getExtras().getString("movieid"));
                startActivity(intent);

                return false;
            }
        });


        rb_music.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Intent intent = new Intent(getApplicationContext(), SingleRating.class);
                intent.putExtra("category", "music");
                intent.putExtra("movieid", getIntent().getExtras().getString("movieid"));
                startActivity(intent);

                return false;
            }
        });


        rb_acting.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Intent intent = new Intent(getApplicationContext(), SingleRating.class);
                intent.putExtra("category", "actor");
                intent.putExtra("movieid", getIntent().getExtras().getString("movieid"));
                startActivity(intent);

                return false;
            }
        });


        rb_effects.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Intent intent = new Intent(getApplicationContext(), SingleRating.class);
                intent.putExtra("category", "effects");
                intent.putExtra("movieid", getIntent().getExtras().getString("movieid"));
                startActivity(intent);

                return false;
            }
        });


        rb_camera.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Intent intent = new Intent(getApplicationContext(), SingleRating.class);
                intent.putExtra("category", "camera");
                intent.putExtra("movieid", getIntent().getExtras().getString("movieid"));
                startActivity(intent);

                return false;
            }
        });

        rb_entertaining.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Intent intent = new Intent(getApplicationContext(), SingleRating.class);
                intent.putExtra("category", "entertaining");
                intent.putExtra("movieid", getIntent().getExtras().getString("movieid"));
                startActivity(intent);

                return false;
            }
        });


        rb_overall.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Intent intent = new Intent(getApplicationContext(), SingleRating.class);
                intent.putExtra("category", "overall");
                intent.putExtra("movieid", getIntent().getExtras().getString("movieid"));
                startActivity(intent);

                return false;
            }
        });

        rb_expectedoverall.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Intent intent = new Intent(getApplicationContext(), SingleRating.class);
                intent.putExtra("category", "expectedoverall");
                intent.putExtra("movieid", getIntent().getExtras().getString("movieid"));
                startActivity(intent);

                return false;
            }
        });




        loadFAB();

        /*RatingBar rb_story = findViewById(R.id.avgstory);

        rb_story.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Toast.makeText(getApplicationContext(), "gas", Toast.LENGTH_SHORT).show();

                return false;
            }
        });
*/

    }


    private void loadFAB()
    {
        try
        {
            loadJSON lj = new loadJSON("http://faoiltiarna.ddns.net:4443/ratemovies/alreadyrated/" + getIntent().getExtras().getInt("movieid") + "/" + userid);
            lj.thread.start();
            lj.thread.join();
            String jsonraw = lj.result;

            JSONObject jo = new JSONObject(jsonraw);

            boolean rated = jo.getBoolean("msg");
            if(!rated)
            {

                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), AddRatingView.class);
                        intent.putExtra("movie", getTitle());

                        startActivity(intent);
                    }
                });
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            }
            else
            {
                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                fab.hide();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void loadRatingFromAPI(RatingBar rb_story, RatingBar rb_writing, RatingBar rb_music, RatingBar rb_acting, RatingBar rb_effects, RatingBar rb_camera, RatingBar rb_entertaining, RatingBar rb_overall, RatingBar rb_expectedoverall)
    {
        try
        {
            loadJSON lj = new loadJSON("http://faoiltiarna.ddns.net:4443/ratemovies/averageratings/" + getIntent().getExtras().getInt("movieid"));
            lj.thread.start();
            lj.thread.join();
            String jsonraw = lj.result;

            System.out.println(jsonraw);
            JSONArray ja = new JSONArray(jsonraw);

            JSONObject jo = ja.getJSONObject(0);

            rb_story.setRating((float) jo.getDouble("story"));
            rb_writing.setRating((float) jo.getDouble("writing"));
            rb_music.setRating((float) jo.getDouble("music"));
            rb_acting.setRating((float) jo.getDouble("acting"));
            rb_effects.setRating((float) jo.getDouble("effects"));
            rb_camera.setRating((float) jo.getDouble("camera"));
            rb_entertaining.setRating((float) jo.getDouble("entertaining"));
            rb_overall.setRating((float) jo.getDouble("overall"));
            rb_expectedoverall.setRating((float) jo.getDouble("expectedoverall"));

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
