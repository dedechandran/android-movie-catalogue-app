package com.example.myfavoritemovie;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myfavoritemovie.Data.Movie;
import com.example.myfavoritemovie.Database.DbContract;
import com.example.myfavoritemovie.Loader.LoaderMovieDetail;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Movie> {
    public static final String EXTRA_MOVIE_ID = "movie_id";
    public static final String EXTRA_MOVIE_FAVORITE = "movie_favorite";
    private static final String imgUrl = "http://image.tmdb.org/t/p/w342";
    @BindView(R.id.fab_favorite)
    FloatingActionButton fabFavorite;

    @BindView(R.id.detail_image)
    ImageView movieImage;

    @BindViews({
            R.id.detail_title,
            R.id.detail_overview,
            R.id.detail_official_sites,
            R.id.detail_country,
            R.id.detail_language,
            R.id.detail_release_date,
            R.id.detail_genre,
            R.id.detail_companies,
            R.id.detail_duration,
            R.id.detail_vote_count,
            R.id.detail_average_vote,
            R.id.detail_popularity
    })
    List<TextView> detailMovies;

    @BindView(R.id.detail_image_progressbar)
    ProgressBar progressBar;

    @BindView(R.id.detail_collapse_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.detail_toolbar)
    Toolbar toolbar;

    private boolean flag = false;
    private Movie movie;
    private static final int LOAD_FAVORITE_MOVIE_DETAIL_ID = 700;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        fabFavorite.setEnabled(false);

        collapsingToolbarLayout.setTitle(getResources().getString(R.string.title_activity_detail));
        collapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);

        movie = getIntent().getParcelableExtra(EXTRA_MOVIE_FAVORITE);

        //mengecek kedalam database apakah movie tertentu sudah pernah dijadikan movie favorite atau belum
        Uri uri = Uri.parse(DbContract.CONTENT_URI + "/" + movie.getMovieId());
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor.getCount() > 0) {
            flag = true;
            fabFavorite.setImageResource(R.drawable.ic_star_white_24dp);
        }
        cursor.close();


        fabFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    fabFavorite.setImageResource(R.drawable.ic_star_border_white_24dp);
                    flag = false;
                    Uri uri = Uri.parse(DbContract.CONTENT_URI + "/" + movie.getMovieId());
                    getContentResolver().delete(uri, null, null);
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.confirmation_delete), Toast.LENGTH_SHORT).show();
                } else {
                    flag = true;
                    fabFavorite.setImageResource(R.drawable.ic_star_white_24dp);
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(DbContract.FavoriteColumns.MOVIE_ID, movie.getMovieId());
                    contentValues.put(DbContract.FavoriteColumns.MOVIE_TITLE, movie.getMovieTitle());
                    contentValues.put(DbContract.FavoriteColumns.MOVIE_DESC, movie.getMovieOverview());
                    contentValues.put(DbContract.FavoriteColumns.MOVIE_DATE, movie.getMovieReleaseDate());
                    contentValues.put(DbContract.FavoriteColumns.MOVIE_PATH_IMG, movie.getMoviePosterUrl());
                    getContentResolver().insert(DbContract.CONTENT_URI, contentValues);
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.confirmation_added), Toast.LENGTH_SHORT).show();
                }
            }
        });

        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_MOVIE_ID, movie.getMovieId());

        getSupportLoaderManager().initLoader(LOAD_FAVORITE_MOVIE_DETAIL_ID,bundle,this);
    }


    @NonNull
    @Override
    public Loader<Movie> onCreateLoader(int id, @Nullable Bundle args) {
        int movieId = 0;
        if (args != null) {
            movieId = args.getInt(EXTRA_MOVIE_ID);
        }
        return new LoaderMovieDetail(this,movieId);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Movie> loader, Movie data) {
        Glide.with(this)
                .load(imgUrl + data.getMoviePosterUrl())
                .into(movieImage);
        progressBar.setVisibility(View.INVISIBLE);
        displaySingleDetail(detailMovies.get(0), "title", data);
        displaySingleDetail(detailMovies.get(1), "overview", data);
        displaySingleDetail(detailMovies.get(2), "homepage", data);
        displayMultipleDetail(detailMovies.get(3), "countries", data);
        displayMultipleDetail(detailMovies.get(4), "languages", data);
        displaySingleDetail(detailMovies.get(5), "releasedate", data);
        displayMultipleDetail(detailMovies.get(6), "genres", data);
        displayMultipleDetail(detailMovies.get(7), "companies", data);
        displaySingleDetail(detailMovies.get(8), "duration", data);

        detailMovies.get(9).setText(String.valueOf(data.getMovieVoteCount()));
        String voteAvg = new DecimalFormat("#.#").format(data.getMovieAverageVote());
        String popularity = new DecimalFormat("#.#").format(data.getMoviePopularity());
        detailMovies.get(10).setText(voteAvg);
        detailMovies.get(11).setText(popularity);
        fabFavorite.setEnabled(true);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Movie> loader) {

    }

    private ArrayList<String> getMultipleDetail(String key, Movie movie) {
        ArrayList<String> tempDetail = new ArrayList<>();
        switch (key) {
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

    private void displayMultipleDetail(TextView view, String key, Movie movie) {
        ArrayList<String> tempDetail = getMultipleDetail(key, movie);
        if (tempDetail.size() == 0) {
            view.setText("-");
        } else {
            for (int i = 0; i < tempDetail.size(); i++) {
                if (i == tempDetail.size() - 1) {
                    view.append(tempDetail.get(i));
                } else {
                    view.append(tempDetail.get(i) + ",");
                }
            }
        }
    }

    private String getDetail(String key, Movie movie) {
        String tempDetail = null;
        switch (key) {
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
                if (movie.getMovieDuration().equals("null")) {
                    tempDetail = "-";
                } else {
                    tempDetail = movie.getMovieDuration() + " min";
                }
                break;
        }

        return tempDetail;
    }

    private void displaySingleDetail(TextView view, String key, Movie movie) {
        String detail = getDetail(key, movie);
        if (detail.equals("null")) {
            view.setText("-");
        } else {
            view.setText(detail);
        }
    }
}
