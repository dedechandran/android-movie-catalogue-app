package com.example.genjeh.mycataloguemovieuiux.Activity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.genjeh.mycataloguemovieuiux.Data.Movie;
import com.example.genjeh.mycataloguemovieuiux.Loader.LoaderMovieDetail;
import com.example.genjeh.mycataloguemovieuiux.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Movie> {
    private CollapsingToolbarLayout collapsingToolbarLayout;

    private ProgressBar progressBar;
    public static final String EXTRA_MOVIE_ID="movie_id";
    private static final String imgUrl = "http://image.tmdb.org/t/p/w342";

    private ImageView movieImage;
    private TextView movieOverview;
    private TextView movieTitle;
    private TextView movieReleaseDate;
    private TextView movieGenre;
    private TextView movieLanguage;
    private TextView moviePopularity;
    private TextView movieAverageVote;
    private TextView movieVoteCount;
    private TextView movieCompanies;
    private TextView movieCountries;
    private TextView movieHomePage;
    private TextView movieDuration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        progressBar = findViewById(R.id.detail_image_progressbar);
        collapsingToolbarLayout = findViewById(R.id.detail_collapse_toolbar);
        collapsingToolbarLayout.setTitle(getResources().getString(R.string.detail_activity));
        collapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);

        movieImage = findViewById(R.id.detail_image);
        movieOverview = findViewById(R.id.detail_overview);
        movieTitle = findViewById(R.id.detail_title);
        movieReleaseDate = findViewById(R.id.detail_release_date);
        movieLanguage = findViewById(R.id.detail_language);
        moviePopularity = findViewById(R.id.detail_popularity);
        movieAverageVote = findViewById(R.id.detail_average_vote);
        movieVoteCount = findViewById(R.id.detail_vote_count);
        movieGenre = findViewById(R.id.detail_genre);
        movieCompanies = findViewById(R.id.detail_companies);
        movieCountries = findViewById(R.id.detail_country);
        movieHomePage = findViewById(R.id.detail_official_sites);
        movieDuration = findViewById(R.id.detail_duration);


        int movieId = getIntent().getIntExtra(EXTRA_MOVIE_ID,0);
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_MOVIE_ID,movieId);
        getSupportLoaderManager().initLoader(4,bundle,this);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @NonNull
    @Override
    public Loader<Movie> onCreateLoader(int id, @Nullable Bundle args) {
        int movieId = 0;
        if(args!=null){
            movieId = args.getInt(EXTRA_MOVIE_ID);
        }
        return new LoaderMovieDetail(this,movieId);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Movie> loader, final Movie data) {
        Glide.with(this)
                .load(imgUrl+data.getMoviePosterUrl())
                .into(movieImage);
        progressBar.setVisibility(View.INVISIBLE);
        displaySingleDetail(movieTitle,"title",data);
        displaySingleDetail(movieOverview,"overview",data);
        displaySingleDetail(movieReleaseDate,"releasedate",data);
        displaySingleDetail(movieHomePage,"homepage",data);
        displaySingleDetail(movieDuration,"duration",data);
        movieVoteCount.setText(String.valueOf(data.getMovieVoteCount()));
        String voteAvg = new DecimalFormat("#.#").format(data.getMovieAverageVote());
        String popularity = new DecimalFormat("#.#").format(data.getMoviePopularity());
        moviePopularity.setText(popularity);
        movieAverageVote.setText(voteAvg);
        displayMultipleDetail(movieLanguage,"languages",data);
        displayMultipleDetail(movieGenre,"genres",data);
        displayMultipleDetail(movieCompanies,"companies",data);
        displayMultipleDetail(movieCountries,"countries",data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Movie> loader) {

    }

    private ArrayList<String> getMultipleDetail(String key,Movie movie){
        ArrayList<String> tempDetail = new ArrayList<>();
        switch (key){
            case "languages":
                tempDetail = movie.getMovieLanguage();
                break;
            case "genres":
                tempDetail = movie.getMovieGenre();
                break;
            case "countries":
                tempDetail = movie.getMovieProductionCountries();
                break;
            case "companies":
                tempDetail = movie.getMovieProductionCompanies();
                break;
        }

        return tempDetail;
    }

    private void displayMultipleDetail(TextView view,String key,Movie movie){
        ArrayList<String> tempDetail = getMultipleDetail(key,movie);
        if(tempDetail.size()==0){
            view.setText("-");
        }else{
            for(int i=0;i<tempDetail.size();i++){
                if(i==tempDetail.size()-1){
                    view.append(tempDetail.get(i));
                }else{
                    view.append(tempDetail.get(i)+",");
                }
            }
        }
    }

    private String getDetail(String key,Movie movie){
        String tempDetail=null;
        switch (key){
            case "title":
                tempDetail = movie.getMovieTitle();
                break;

            case "overview":
                tempDetail = movie.getMovieOverview();
                break;

            case "releasedate":
                tempDetail = movie.getMovieReleaseDate();
                break;

            case "homepage":
                tempDetail = movie.getMovieHomePage();
                break;

            case "duration":
                if(movie.getMovieDuration().equals("null")){
                    tempDetail= "-";
                }else{
                    tempDetail = movie.getMovieDuration() + " min";
                }
                break;
        }

        return tempDetail;
    }

    private void displaySingleDetail(TextView view,String key,Movie movie){
        String detail = getDetail(key,movie);
        if(detail.equals("null")){
            view.setText("-");
        }else{
            view.setText(detail);
        }
    }
}
