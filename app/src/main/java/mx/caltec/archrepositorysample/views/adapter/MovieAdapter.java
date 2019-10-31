package mx.caltec.archrepositorysample.views.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import mx.caltec.archrepositorysample.R;
import mx.caltec.archrepositorysample.data.model.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {

    public interface MovieListener {
        void onTap(Movie movie);
    }

    private List<Movie> mMovieList;
    private MovieListener mListener;

    public MovieAdapter(@NonNull MovieListener listener) {
        mListener = listener;
        mMovieList = new ArrayList<>();
    }

    public void setmMovieList(List<Movie> mMovieList) {
        this.mMovieList = mMovieList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_movie, parent, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        Movie movie = mMovieList.get(position);
        holder.textViewTitle.setText(movie.getTitle());
        holder.textViewSynopsis.setText(movie.getSypnosis());

        holder.itemView.setOnClickListener( v -> mListener.onTap(movie) );
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    static class MovieHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewSynopsis;

        MovieHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewSynopsis = itemView.findViewById(R.id.textViewSynopsis);
        }
    }


}
