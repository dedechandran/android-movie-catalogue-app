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

import com.example.genjeh.mycataloguemovieuiux.Adapter.MovieAdapter;
import com.example.genjeh.mycataloguemovieuiux.Data.Movie;
import com.example.genjeh.mycataloguemovieuiux.Loader.LoaderMovieNowPlaying;
import com.example.genjeh.mycataloguemovieuiux.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class NowPlayingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {
    private MovieAdapter movieAdapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Context mContext;

    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext=null;
    }

    public NowPlayingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        recyclerView = view.findViewById(R.id.rv_movie);
        recyclerView.setNestedScrollingEnabled(true);
        progressBar = view.findViewById(R.id.progressbar);
        movieAdapter = new MovieAdapter(getActivity());
        movieAdapter.notifyDataSetChanged();
        getLoaderManager().initLoader(1, null, this);


        return view;
    }

    @NonNull
    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, @Nullable Bundle args) {
        return new LoaderMovieNowPlaying(mContext);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {
        progressBar.setVisibility(View.INVISIBLE);
        movieAdapter.setListMovies(data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(movieAdapter);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Movie>> loader) {
        movieAdapter.setListMovies(null);
    }
}
