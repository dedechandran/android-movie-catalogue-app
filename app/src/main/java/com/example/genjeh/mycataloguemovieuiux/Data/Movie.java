package com.example.genjeh.mycataloguemovieuiux.Data;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.example.genjeh.mycataloguemovieuiux.Database.DbContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Movie implements Parcelable {
    private int movieId;
    private String movieTitle;
    private String movieOverview;
    private String movieReleaseDate;
    private ArrayList<String> movieGenre;
    private ArrayList<String> movieLanguage;
    private double moviePopularity;
    private double movieAverageVote;
    private int movieVoteCount;
    private ArrayList<String> movieProductionCompanies;
    private ArrayList<String> movieProductionCountries;
    private String movieHomePage;
    private String moviePosterUrl;
    private String movieDuration;

    public Movie(JSONObject object, int id) {
        try {
            movieId = id;
            movieTitle = object.getString("title");
            movieOverview = object.getString("overview");
            movieReleaseDate = object.getString("release_date");
            moviePosterUrl = object.getString("poster_path");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Movie(int id, String movieTitle, String movieOverview, String movieReleaseDate, String moviePosterUrl) {
        movieId = id;
        this.movieTitle = movieTitle;
        this.movieOverview = movieOverview;
        this.movieReleaseDate = movieReleaseDate;
        this.moviePosterUrl = moviePosterUrl;
    }

    public Movie(Cursor cursor) {
        this.movieId = DbContract.getColumnInt(cursor, DbContract.FavoriteColumns.MOVIE_ID);
        this.movieTitle = DbContract.getColumnString(cursor, DbContract.FavoriteColumns.MOVIE_TITLE);
        this.movieOverview = DbContract.getColumnString(cursor, DbContract.FavoriteColumns.MOVIE_DESC);
        this.movieReleaseDate = DbContract.getColumnString(cursor, DbContract.FavoriteColumns.MOVIE_DATE);
        this.moviePosterUrl = DbContract.getColumnString(cursor, DbContract.FavoriteColumns.MOVIE_PATH_IMG);
    }

    public Movie(JSONObject object) {
        try {
            movieTitle = object.getString("title");
            movieOverview = object.getString("overview");
            movieReleaseDate = object.getString("release_date");
            moviePopularity = object.getDouble("popularity");
            movieAverageVote = object.getDouble("vote_average");
            movieVoteCount = object.getInt("vote_count");
            moviePosterUrl = object.getString("poster_path");
            movieHomePage = object.getString("homepage");
            movieDuration = object.getString("runtime");

            movieGenre = new ArrayList<>();
            movieLanguage = new ArrayList<>();
            movieProductionCompanies = new ArrayList<>();
            movieProductionCountries = new ArrayList<>();

            JSONArray genres = object.getJSONArray("genres");
            JSONArray languages = object.getJSONArray("spoken_languages");
            JSONArray companies = object.getJSONArray("production_companies");
            JSONArray countries = object.getJSONArray("production_countries");
            for (int i = 0; i < genres.length(); i++) {
                movieGenre.add(genres.getJSONObject(i).getString("name"));
            }
            for (int i = 0; i < languages.length(); i++) {
                movieLanguage.add(languages.getJSONObject(i).getString("name"));
            }
            for (int i = 0; i < companies.length(); i++) {
                movieProductionCompanies.add(companies.getJSONObject(i).getString("name"));
            }
            for (int i = 0; i < countries.length(); i++) {
                movieProductionCountries.add(countries.getJSONObject(i).getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getMovieId() {
        return movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }


    public ArrayList<String> getMovieGenre() {
        return movieGenre;
    }

    public ArrayList<String> getMovieLanguage() {
        return movieLanguage;
    }

    public double getMoviePopularity() {
        return moviePopularity;
    }

    public double getMovieAverageVote() {
        return movieAverageVote;
    }

    public int getMovieVoteCount() {
        return movieVoteCount;
    }

    public ArrayList<String> getMovieProductionCompanies() {
        return movieProductionCompanies;
    }

    public ArrayList<String> getMovieProductionCountries() {
        return movieProductionCountries;
    }

    public String getMovieHomePage() {
        return movieHomePage;
    }

    public String getMoviePosterUrl() {
        return moviePosterUrl;
    }

    public String getMovieDuration() {
        return movieDuration;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.movieId);
        dest.writeString(this.movieTitle);
        dest.writeString(this.movieOverview);
        dest.writeString(this.movieReleaseDate);
        dest.writeStringList(this.movieGenre);
        dest.writeStringList(this.movieLanguage);
        dest.writeDouble(this.moviePopularity);
        dest.writeDouble(this.movieAverageVote);
        dest.writeInt(this.movieVoteCount);
        dest.writeStringList(this.movieProductionCompanies);
        dest.writeStringList(this.movieProductionCountries);
        dest.writeString(this.movieHomePage);
        dest.writeString(this.moviePosterUrl);
        dest.writeString(this.movieDuration);
    }

    protected Movie(Parcel in) {
        this.movieId = in.readInt();
        this.movieTitle = in.readString();
        this.movieOverview = in.readString();
        this.movieReleaseDate = in.readString();
        this.movieGenre = in.createStringArrayList();
        this.movieLanguage = in.createStringArrayList();
        this.moviePopularity = in.readDouble();
        this.movieAverageVote = in.readDouble();
        this.movieVoteCount = in.readInt();
        this.movieProductionCompanies = in.createStringArrayList();
        this.movieProductionCountries = in.createStringArrayList();
        this.movieHomePage = in.readString();
        this.moviePosterUrl = in.readString();
        this.movieDuration = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
