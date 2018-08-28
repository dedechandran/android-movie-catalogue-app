package com.example.genjeh.mycataloguemovieuiux.Loader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.widget.Toast;

import com.example.genjeh.mycataloguemovieuiux.Data.Movie;
import com.example.genjeh.mycataloguemovieuiux.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoaderMovieDetail extends AsyncTaskLoader<Movie> {
    private Movie movieDetail;
    private boolean hasResult=false;
    private int movieId;
    private static final String API_KEY = "a4efaa7ae55e845278da0fd4549e3246";

    public LoaderMovieDetail(@NonNull Context context, int movieId) {
        super(context);
        this.movieId = movieId;
        onContentChanged();
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if(hasResult){
            movieDetail=null;
            hasResult=false;
        }
    }

    @Override
    public void deliverResult(@Nullable Movie data) {
        movieDetail = data;
        hasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        if(takeContentChanged()){
            forceLoad();
        }else if(hasResult){
            deliverResult(movieDetail);
        }
    }

    @Nullable
    @Override
    public Movie loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();
        final Movie[] movieDetailItem = new Movie[1];
        String url = "https://api.themoviedb.org/3/movie/"+movieId+"?api_key="+API_KEY+"&language=en-US";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                try {
                    JSONObject object = new JSONObject(response);
                    movieDetailItem[0] = new Movie(object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
           }
        });
        return movieDetailItem[0];
    }


}
