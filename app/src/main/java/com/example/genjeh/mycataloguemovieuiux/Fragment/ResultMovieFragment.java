package com.example.genjeh.mycataloguemovieuiux.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.genjeh.mycataloguemovieuiux.Adapter.MovieAdapter;
import com.example.genjeh.mycataloguemovieuiux.Data.Movie;
import com.example.genjeh.mycataloguemovieuiux.Loader.LoaderMovieResult;
import com.example.genjeh.mycataloguemovieuiux.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultMovieFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {

    @BindView(R.id.rv_movie)
    RecyclerView recyclerView;

    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    @BindView(R.id.tv_result)
    TextView tvResult;

    private String movieName;
    private MovieAdapter movieAdapter;
    private static final int LOAD_ID_MOVIE_RESULT = 100;

    public ResultMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result_movie, container, false);
        ButterKnife.bind(this, view);
        movieAdapter = new MovieAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(movieAdapter);
        getLoaderManager().initLoader(LOAD_ID_MOVIE_RESULT, null, this);

        return view;
    }

    @NonNull
    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, @Nullable Bundle args) {

        return new LoaderMovieResult(getContext(), getMovieName());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {
        progressBar.setVisibility(View.GONE);
        movieAdapter.setListMovies(data);
        movieAdapter.notifyDataSetChanged();
        if (movieAdapter.getListMovies().size() == 0) {
            tvResult.setText(getResources().getString(R.string.movie_result));
            tvResult.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Movie>> loader) {
        movieAdapter.setListMovies(null);
        movieAdapter.notifyDataSetChanged();
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
}
