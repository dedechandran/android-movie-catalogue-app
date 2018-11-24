package com.example.genjeh.mycataloguemovieuiux.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.genjeh.mycataloguemovieuiux.Activity.DetailActivity;
import com.example.genjeh.mycataloguemovieuiux.CustomListener.CustomOnItemClickListener;
import com.example.genjeh.mycataloguemovieuiux.Data.Movie;
import com.example.genjeh.mycataloguemovieuiux.R;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class MovieFavoriteAdapter extends RecyclerView.Adapter<MovieFavoriteAdapter.MovieFavoriteHolder> {
    private Context context;
    private Cursor dataMovieFav;
    private static final String imgUrl = "http://image.tmdb.org/t/p/w185";

    public MovieFavoriteAdapter(Context context) {
        this.context = context;
    }

    public void setDataMovieFav(Cursor dataMovieFav) {
        this.dataMovieFav = dataMovieFav;
    }

    @NonNull
    @Override
    public MovieFavoriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_movie, parent, false);
        return new MovieFavoriteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieFavoriteHolder holder, int position) {
        final Movie movie = getItem(position);
        Glide.with(context)
                .load(imgUrl + movie.getMoviePosterUrl())
                .into(holder.movieImage);
        holder.movieFavProperties.get(0).setText(movie.getMovieTitle());
        holder.movieFavProperties.get(1).setText(movie.getMovieOverview());
        holder.movieFavProperties.get(2).setText(movie.getMovieReleaseDate());
        holder.movieBtn.get(0).setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View v, int position) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_MOVIE_FAVORITE, movie);
                context.startActivity(intent);
            }
        }));
    }

    @Override
    public int getItemCount() {
        if (dataMovieFav == null) {
            return 0;
        }
        return dataMovieFav.getCount();
    }

    private Movie getItem(int position) {
        if (!dataMovieFav.moveToPosition(position)) {
            throw new IllegalStateException("Position Invalid");
        }
        return new Movie(dataMovieFav);
    }

    public class MovieFavoriteHolder extends RecyclerView.ViewHolder {
        @BindViews({R.id.movie_title, R.id.movie_overview, R.id.movie_release_date})
        List<TextView> movieFavProperties;

        @BindView(R.id.movie_image)
        ImageView movieImage;

        @BindViews({R.id.btn_detail, R.id.btn_share})
        List<Button> movieBtn;

        MovieFavoriteHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
