package com.example.genjeh.mycataloguemovieuiux.Loader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.view.View;
import android.widget.ProgressBar;

import com.example.genjeh.mycataloguemovieuiux.Data.Movie;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.loopj.android.http.AsyncHttpClient.log;

public class LoaderMovieUpComing extends AsyncTaskLoader<ArrayList<Movie>> {
    private static final String API_KEY="a4efaa7ae55e845278da0fd4549e3246";
    private ArrayList<Movie> dataMovieUpcoming;
    private boolean hasResult=false;


    public LoaderMovieUpComing(@NonNull Context context) {
        super(context);
        onContentChanged();
    }

    @Override
    protected void onStartLoading() {
        if(takeContentChanged()){
            forceLoad();
        }else if(hasResult){
            deliverResult(dataMovieUpcoming);
        }
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if(hasResult){
            dataMovieUpcoming=null;
            hasResult = false;
        }
    }

    @Override
    public void deliverResult(@Nullable ArrayList<Movie> data) {
        dataMovieUpcoming = data;
        hasResult=true;
        super.deliverResult(data);
    }

    @Nullable
    @Override
    public ArrayList<Movie> loadInBackground() {

        SyncHttpClient client = new SyncHttpClient();
        final ArrayList<Movie> upcomingmovieItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/movie/upcoming?api_key="+API_KEY+"&language=en-US";
        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String response = new String(responseBody);
                    JSONObject object = new JSONObject(response);
                    JSONArray listUpcomingMovies = object.getJSONArray("results");
                    for(int i=0;i<listUpcomingMovies.length();i++){
                        JSONObject upcomingMovieItem = listUpcomingMovies.getJSONObject(i);
                        int id = upcomingMovieItem.getInt("id");
                        upcomingmovieItems.add(new Movie(upcomingMovieItem,id));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        return upcomingmovieItems;
    }


}
