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

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultMovieFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {
    private MovieAdapter movieAdapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private String movieName;
    private Context mContext;
    private TextView tvResult;

    public ResultMovieFragment() {
        // Required empty public constructor
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext=null;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result_movie, container, false);
        recyclerView = view.findViewById(R.id.rv_movie);
        recyclerView.setNestedScrollingEnabled(true);
        progressBar = view.findViewById(R.id.progressbar);
        movieAdapter = new MovieAdapter(getActivity());
        movieAdapter.notifyDataSetChanged();
        tvResult = view.findViewById(R.id.tv_result);
        getLoaderManager().initLoader(22, null, this);

        return view;
    }

    @NonNull
    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, @Nullable Bundle args) {

        return new LoaderMovieResult(mContext,getMovieName());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {
        progressBar.setVisibility(View.INVISIBLE);
        movieAdapter.setListMovies(data);
        if(movieAdapter.getListMovies().size()!=0){
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(movieAdapter);
        }else{
            tvResult.setText(getResources().getString(R.string.movie_result));
            tvResult.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Movie>> loader) {
        movieAdapter.setListMovies(null);
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
}
