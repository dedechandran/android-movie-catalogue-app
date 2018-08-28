package com.example.genjeh.mycataloguemovieuiux.Loader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.genjeh.mycataloguemovieuiux.Data.Movie;
import com.example.genjeh.mycataloguemovieuiux.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.loopj.android.http.AsyncHttpClient.log;

public class LoaderMovieNowPlaying extends AsyncTaskLoader<ArrayList<Movie>> {
    private ArrayList<Movie> dataMovieNowplaying;
    private static final String API_KEY="a4efaa7ae55e845278da0fd4549e3246";
    private boolean hasResult=false;


    public LoaderMovieNowPlaying(@NonNull Context context) {
        super(context);
        onContentChanged();
    }

    @Override
    protected void onStartLoading() {
        if(takeContentChanged()){
            forceLoad();
        }else if(hasResult){
            deliverResult(dataMovieNowplaying);
        }
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if(hasResult){
            dataMovieNowplaying=null;
            hasResult=false;
        }
    }

    @Override
    public void deliverResult(@Nullable ArrayList<Movie> data) {
        dataMovieNowplaying=data;
        hasResult=true;
        super.deliverResult(data);
    }

    @Nullable
    @Override
    public ArrayList<Movie> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();
        final ArrayList<Movie> moviesNowPlaying = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key="+API_KEY+"&language=en-US";
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
                    JSONArray listMovieNowplaying = object.getJSONArray("results");
                    for(int i=0;i<listMovieNowplaying.length();i++){
                        JSONObject nowplayingMovieItem = listMovieNowplaying.getJSONObject(i);
                        int id = nowplayingMovieItem.getInt("id");
                        moviesNowPlaying.add(new Movie(nowplayingMovieItem,id));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        return moviesNowPlaying;
    }

}
