package com.example.genjeh.mycataloguemovieuiux.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.example.genjeh.mycataloguemovieuiux.Database.DbContract;
import com.example.genjeh.mycataloguemovieuiux.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {
    private Context context;
    private ArrayList<Movie> listMovies = new ArrayList<>();
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_movie, parent, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieHolder holder, int position) {
        Glide.with(context)
                .load(imgUrl + listMovies.get(position).getMoviePosterUrl())
                .into(holder.movieImage);
        holder.movieProperties.get(0).setText(getListMovies().get(position).getMovieTitle());
        holder.movieProperties.get(1).setText(getListMovies().get(position).getMovieOverview());
        holder.movieProperties.get(2).setText(getListMovies().get(position).getMovieReleaseDate());
        holder.movieBtn.get(0).setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View v, int position) {
                Intent intent = new Intent(context, DetailActivity.class);
                Movie movie = new Movie(getListMovies().get(position).getMovieId()
                        , getListMovies().get(position).getMovieTitle()
                        , getListMovies().get(position).getMovieOverview()
                        , getListMovies().get(position).getMovieReleaseDate()
                        , getListMovies().get(position).getMoviePosterUrl()
                );
                intent.putExtra(DetailActivity.EXTRA_MOVIE_FAVORITE, movie);
                context.startActivity(intent);

            }
        }));
        holder.movieBtn.get(1).setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View v, int position) {
                Toast.makeText(context, context.getResources().getString(R.string.share), Toast.LENGTH_SHORT).show();
            }
        }));
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    class MovieHolder extends RecyclerView.ViewHolder {
        @BindViews({R.id.movie_title, R.id.movie_overview, R.id.movie_release_date})
        List<TextView> movieProperties;

        @BindView(R.id.movie_image)
        ImageView movieImage;

        @BindViews({R.id.btn_detail, R.id.btn_share})
        List<Button> movieBtn;

        MovieHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
