package me.holz.ratemovies;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;


public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.ViewHolder>
{
    public class ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView name;
        public RatingBar rating;

        public ViewHolder(View view)
        {
            super(view);

            name = view.findViewById(R.id.name);
            rating = view.findViewById(R.id.rating);

        }
    }

    private List<RatingItem> m_Ratings;

    public RatingAdapter(List<RatingItem> t_ratings)
    {
        this.m_Ratings = t_ratings;
    }

    @Override
    public RatingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rating_row_element, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RatingAdapter.ViewHolder holder, int position)
    {
        RatingItem ratingitem = m_Ratings.get(position);
        holder.name.setText(ratingitem.name);
        holder.rating.setRating(ratingitem.rating);
    }

    @Override
    public int getItemCount()
    {
        return m_Ratings.size();
    }
}
