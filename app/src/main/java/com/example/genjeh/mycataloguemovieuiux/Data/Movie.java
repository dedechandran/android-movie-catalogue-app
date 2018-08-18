package com.example.genjeh.mycataloguemovieuiux.Data;

import android.graphics.Bitmap;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Movie {
    private  int movieId;
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

    public Movie(JSONObject object,int id){
        try {
            movieId = id;
            movieTitle = object.getString("title");
            movieOverview=object.getString("overview");
            movieReleaseDate=object.getString("release_date");
            moviePosterUrl = object.getString("poster_path");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Movie(JSONObject object){
        try {
            Log.d("Constructor", "Movie: InConstructor");
            movieTitle = object.getString("title");
            movieOverview=object.getString("overview");
            movieReleaseDate=object.getString("release_date");
            moviePopularity= object.getDouble("popularity");
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
            for(int i=0;i<genres.length();i++){
                movieGenre.add(genres.getJSONObject(i).getString("name"));
            }
            for(int i=0;i<languages.length();i++){
                movieLanguage.add(languages.getJSONObject(i).getString("name"));
            }
            for(int i=0;i<companies.length();i++){
                movieProductionCompanies.add(companies.getJSONObject(i).getString("name"));
            }
            for(int i=0;i<countries.length();i++){
                movieProductionCountries.add(countries.getJSONObject(i).getString("name"));
            }
            Log.d("Constructor", "Movie: EndConstructor");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
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
}
