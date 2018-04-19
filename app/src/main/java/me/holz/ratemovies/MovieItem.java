package me.holz.ratemovies;

import android.widget.ImageView;

/**
 * Created by Felix on 29/03/2018.
 */

public class MovieItem
{

    public int id;

    public String title;
    public String genre;
    public String releasedate;
    public String info;
    public String image;
    public String watchdate;

    public double imdb;
    public double averagerating;

    public MovieItem(int id, String title, String genre, String releasedate, String watchdate, String info, String image, double imdb, double averagerating)
    {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.releasedate = releasedate;
        this.watchdate = watchdate;
        this.info = info;

        this.imdb = imdb;
        this.averagerating = averagerating;

        this.image = image;

    }

}
