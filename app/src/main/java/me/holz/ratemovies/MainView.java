package me.holz.ratemovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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


    private void prepareMovieData() {
        /*
        MovieItem movie = new MovieItem("Mad Max: Fury Road", "Action & Adventure", "2015", "schub", "https://upload.wikimedia.org/wikipedia/commons/f/fb/Mad_Max_Fury_Road_film_Logo.png");
        movieList.add(movie);

        movie = new MovieItem("Inside Out", "Animation, Kids & Family", "2015", "d;f", "https://i5.walmartimages.com/asr/03fc65f9-33df-43bd-96df-42fe222d44ce_1.04d1ed676b80f7413cafa6ce9902e0f8.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF");
        movieList.add(movie);

        movie = new MovieItem("Star Wars: Episode VII - The Force Awakens", "Action", "2015", "d;f", "https://i5.walmartimages.com/asr/03fc65f9-33df-43bd-96df-42fe222d44ce_1.04d1ed676b80f7413cafa6ce9902e0f8.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF");
        movieList.add(movie);

        movie = new MovieItem("Shaun the Sheep", "Animation", "2015", "d;f", "https://i5.walmartimages.com/asr/03fc65f9-33df-43bd-96df-42fe222d44ce_1.04d1ed676b80f7413cafa6ce9902e0f8.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF");
        movieList.add(movie);

        movie = new MovieItem("The Martian", "Science Fiction & Fantasy", "2015", "d;f", "https://i5.walmartimages.com/asr/03fc65f9-33df-43bd-96df-42fe222d44ce_1.04d1ed676b80f7413cafa6ce9902e0f8.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF");
        movieList.add(movie);

        movie = new MovieItem("Mission: Impossible Rogue Nation", "Action", "2015", "d;f", "https://i5.walmartimages.com/asr/03fc65f9-33df-43bd-96df-42fe222d44ce_1.04d1ed676b80f7413cafa6ce9902e0f8.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF");
        movieList.add(movie);

        movie = new MovieItem("Up", "Animation", "2009", "d;f", "https://i5.walmartimages.com/asr/03fc65f9-33df-43bd-96df-42fe222d44ce_1.04d1ed676b80f7413cafa6ce9902e0f8.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF");
        movieList.add(movie);

        movie = new MovieItem("Star Trek", "Science Fiction", "2009", "d;f", "https://i5.walmartimages.com/asr/03fc65f9-33df-43bd-96df-42fe222d44ce_1.04d1ed676b80f7413cafa6ce9902e0f8.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF");
        movieList.add(movie);

        movie = new MovieItem("The LEGO Movie", "Animation", "2014", "d;f", "https://i5.walmartimages.com/asr/03fc65f9-33df-43bd-96df-42fe222d44ce_1.04d1ed676b80f7413cafa6ce9902e0f8.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF");
        movieList.add(movie);

        movie = new MovieItem("Iron Man", "Action & Adventure", "2008", "d;f", "https://i5.walmartimages.com/asr/03fc65f9-33df-43bd-96df-42fe222d44ce_1.04d1ed676b80f7413cafa6ce9902e0f8.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF");
        movieList.add(movie);

        movie = new MovieItem("Aliens", "Science Fiction", "1986", "d;f", "https://i5.walmartimages.com/asr/03fc65f9-33df-43bd-96df-42fe222d44ce_1.04d1ed676b80f7413cafa6ce9902e0f8.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF");
        movieList.add(movie);

        movie = new MovieItem("Chicken Run", "Animation", "2000", "d;f", "https://i5.walmartimages.com/asr/03fc65f9-33df-43bd-96df-42fe222d44ce_1.04d1ed676b80f7413cafa6ce9902e0f8.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF");
        movieList.add(movie);

        movie = new MovieItem("Back to the Future", "Science Fiction", "1985", "d;f", "https://i5.walmartimages.com/asr/03fc65f9-33df-43bd-96df-42fe222d44ce_1.04d1ed676b80f7413cafa6ce9902e0f8.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF");
        movieList.add(movie);

        movie = new MovieItem("Raiders of the Lost Ark", "Action & Adventure", "1981", "d;f", "https://i5.walmartimages.com/asr/03fc65f9-33df-43bd-96df-42fe222d44ce_1.04d1ed676b80f7413cafa6ce9902e0f8.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF");
        movieList.add(movie);

        movie = new MovieItem("Goldfinger", "Action & Adventure", "1965", "d;f", "https://i5.walmartimages.com/asr/03fc65f9-33df-43bd-96df-42fe222d44ce_1.04d1ed676b80f7413cafa6ce9902e0f8.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF");
        movieList.add(movie);

        movie = new MovieItem("Guardians of the Galaxy", "Science Fiction & Fantasy", "2014", "d;f", "https://i5.walmartimages.com/asr/03fc65f9-33df-43bd-96df-42fe222d44ce_1.04d1ed676b80f7413cafa6ce9902e0f8.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF");
        movieList.add(movie);

        mAdapter.notifyDataSetChanged();
        */
    }

}
