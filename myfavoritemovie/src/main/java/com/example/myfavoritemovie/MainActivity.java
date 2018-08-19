package com.example.myfavoritemovie;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myfavoritemovie.Adapter.MovieFavoriteAdapter;
import com.example.myfavoritemovie.Loader.LoaderMovieFavorit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    @BindView(R.id.rv_movie)
    RecyclerView rvMovieFavorite;

    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    @BindView(R.id.tv_result_movie_favorite)
    TextView tvResultMovieFavorite;

    private MovieFavoriteAdapter adapter;
    private static final int LOAD_FAVORITE_MOVIE_ID = 600;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        adapter = new MovieFavoriteAdapter(this);
        rvMovieFavorite.setLayoutManager(new LinearLayoutManager(this));
        rvMovieFavorite.setAdapter(adapter);
        tvResultMovieFavorite.setVisibility(View.INVISIBLE);
        getSupportLoaderManager().initLoader(LOAD_FAVORITE_MOVIE_ID,null,this);
    }

    @Override
    protected void onRestart() {
        getSupportLoaderManager().restartLoader(LOAD_FAVORITE_MOVIE_ID,null,this);
        super.onRestart();
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new LoaderMovieFavorit(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        progressBar.setVisibility(View.GONE);
        adapter.setMovieData(data);
        adapter.notifyDataSetChanged();
        if(adapter.getItemCount()==0){
            tvResultMovieFavorite.setVisibility(View.VISIBLE);
        }else{
            tvResultMovieFavorite.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adapter.setMovieData(null);
        adapter.notifyDataSetChanged();
    }
}
