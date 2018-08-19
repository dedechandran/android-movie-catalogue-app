package com.example.myfavoritemovie.Adapter;

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
import com.example.myfavoritemovie.CustomOnClickListener;
import com.example.myfavoritemovie.Data.Movie;
import com.example.myfavoritemovie.DetailActivity;
import com.example.myfavoritemovie.R;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class MovieFavoriteAdapter extends RecyclerView.Adapter<MovieFavoriteAdapter.ViewHolder> {
    private Context context;
    private Cursor movieData;
    private static final String imgUrl = "http://image.tmdb.org/t/p/w185";

    public MovieFavoriteAdapter(Context context) {
        this.context = context;
    }

    public void setMovieData(Cursor movieData) {
        this.movieData = movieData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_favorite_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Movie movie = getItem(position);
        Glide.with(context)
                .load(imgUrl + movie.getMoviePosterUrl())
                .into(holder.movieImage);
        holder.movieFavProperties.get(0).setText(movie.getMovieTitle());
        holder.movieFavProperties.get(1).setText(movie.getMovieOverview());
        holder.movieFavProperties.get(2).setText(movie.getMovieReleaseDate());
        holder.movieBtn.get(0).setOnClickListener(new CustomOnClickListener(position, new CustomOnClickListener.OnItemClickListener() {
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
        if(movieData==null){
            return 0;
        }
        return movieData.getCount();
    }

    private Movie getItem(int position){
        if(!movieData.moveToPosition(position)){
            throw new IllegalStateException("Invalid Position");
        }
        return new Movie(movieData);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindViews({R.id.movie_title, R.id.movie_overview, R.id.movie_release_date})
        List<TextView> movieFavProperties;

        @BindView(R.id.movie_image)
        ImageView movieImage;

        @BindViews({R.id.btn_detail, R.id.btn_share})
        List<Button> movieBtn;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
