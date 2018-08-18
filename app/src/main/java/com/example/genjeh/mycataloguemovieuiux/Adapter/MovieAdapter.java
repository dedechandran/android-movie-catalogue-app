package com.example.genjeh.mycataloguemovieuiux.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.genjeh.mycataloguemovieuiux.Activity.DetailActivity;
import com.example.genjeh.mycataloguemovieuiux.CustomListener.CustomOnItemClickListener;
import com.example.genjeh.mycataloguemovieuiux.Data.Movie;
import com.example.genjeh.mycataloguemovieuiux.R;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {
    private Context context;
    private ArrayList<Movie> listMovies ;
    private static final String imgUrl = "http://image.tmdb.org/t/p/w185";

    public MovieAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<Movie> getListMovies() {
        return listMovies;
    }

    public void setListMovies(ArrayList<Movie> listMovies) {
        this.listMovies = listMovies;
    }

    @NonNull
    @Override
    public MovieAdapter.MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_movie,parent,false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieHolder holder, int position) {
        Glide.with(context)
                .load(imgUrl+listMovies.get(position).getMoviePosterUrl())
                .into(holder.movieImage);
        holder.movieTitle.setText(getListMovies().get(position).getMovieTitle());
        holder.movieOverview.setText(getListMovies().get(position).getMovieOverview());
        holder.movieReleaseDate.setText(getListMovies().get(position).getMovieReleaseDate());
        holder.movieBtnDetail.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View v, int position) {
                Intent intent = new Intent(context,DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_MOVIE_ID,getListMovies().get(position).getMovieId());
                context.startActivity(intent);

            }
        }));
        holder.movieBtnFav.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View v, int position) {
                Toast.makeText(context,"Favorite",Toast.LENGTH_SHORT).show();
            }
        }));
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    class MovieHolder extends RecyclerView.ViewHolder {
        private ImageView movieImage;
        private TextView movieTitle;
        private TextView movieOverview;
        private TextView movieReleaseDate;
        private Button movieBtnDetail;
        private Button movieBtnFav;

        MovieHolder(View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.movie_image);
            movieTitle = itemView.findViewById(R.id.movie_title);
            movieOverview = itemView.findViewById(R.id.movie_overview);
            movieReleaseDate = itemView.findViewById(R.id.movie_release_date);
            movieBtnDetail = itemView.findViewById(R.id.btn_detail);
            movieBtnFav = itemView.findViewById(R.id.btn_favorite);
        }
    }
}
