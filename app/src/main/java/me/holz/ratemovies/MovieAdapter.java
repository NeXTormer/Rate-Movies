package me.holz.ratemovies;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import me.holz.ratemovies.util.DownloadImageTask;

/**
 * Created by Felix on 29/03/2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>
{
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView title;
        public TextView year;
        public TextView genre;
        public TextView info;
        public ImageView image;

        public ViewHolder(View view)
        {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.genre);
            year = (TextView) view.findViewById(R.id.year);
            info = (TextView) view.findViewById(R.id.info);
            image = (ImageView) view.findViewById(R.id.previewImage);
        }
    }

    private List<MovieItem> m_Movies;

    public MovieAdapter(List<MovieItem> movies)
    {
        this.m_Movies = movies;
    }

    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_element, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MovieAdapter.ViewHolder holder, int position)
    {
        MovieItem movie = m_Movies.get(position);
        holder.title.setText(movie.title);
        holder.genre.setText(movie.genre);
        holder.year.setText(movie.releasedate);
        holder.info.setText(movie.averagerating + "");

        Picasso.get().load(movie.image).into(holder.image);


        //not working as intended
        //new DownloadImageTask(holder.image).execute(movie.image);
    }

    @Override
    public int getItemCount()
    {
        return m_Movies.size();
    }
}
