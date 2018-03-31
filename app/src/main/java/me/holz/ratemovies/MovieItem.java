package me.holz.ratemovies;

import android.widget.ImageView;

/**
 * Created by Felix on 29/03/2018.
 */

public class MovieItem
{

    public String title;
    public String genre;
    public String date;
    public String info;
    public String image;

    public MovieItem(String title, String genre, String date, String info, String image)
    {
        this.title = title;
        this.genre = genre;
        this.date = date;
        this.info = info;

        this.image = image;
    }

}
