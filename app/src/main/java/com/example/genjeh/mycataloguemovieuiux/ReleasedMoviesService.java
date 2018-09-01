package com.example.genjeh.mycataloguemovieuiux;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.genjeh.mycataloguemovieuiux.Data.Movie;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;


public class ReleasedMoviesService extends IntentService {

    public ReleasedMoviesService() {
        super("ReleasedMoviesService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            String url = intent.getStringExtra(AlarmReceiver.EXTRA_URL);
            ArrayList<Movie> nowPlayingMovies = getMovies(url);
            boolean noMovies = true;
            SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date currentDate = Calendar.getInstance().getTime();
            String formatedDate = sdformat.format(currentDate);
            for (int i = 0; i < nowPlayingMovies.size(); i++) {
                String movieDate = nowPlayingMovies.get(i).getMovieReleaseDate();
                if (formatedDate.equals(movieDate)) {
                    noMovies = false;
                    AlarmReceiver.showNotification(getApplicationContext(),
                            AlarmReceiver.CHANNEL_ID_MOVIE_RELEASED,
                            AlarmReceiver.ID_NOTIFICATION_MOVIE_RELEASED + i,
                            nowPlayingMovies.get(i).getMovieTitle(),
                            getApplicationContext().getResources().getString(R.string.content_text_today)
                                    + " " + nowPlayingMovies.get(i).getMovieTitle() + " "
                                    + getApplicationContext().getResources().getString(R.string.content_text_released)
                    );
                }
            }

            if(noMovies){
                AlarmReceiver.showNotification(getApplicationContext(),
                        AlarmReceiver.CHANNEL_ID_MOVIE_RELEASED,
                        AlarmReceiver.ID_NOTIFICATION_MOVIE_RELEASED,
                        getApplicationContext().getResources().getString(R.string.app_name),
                        getApplication().getResources().getString(R.string.content_text_no_released_movie)
                );
            }
        }
    }

    private ArrayList<Movie> getMovies(String url) {
        SyncHttpClient client = new SyncHttpClient();
        final ArrayList<Movie> listMovieItems = new ArrayList<>();
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
                    Log.d("MOVIE_FAVORITE_SIZE", String.valueOf(listUpcomingMovies.length()));
                    for (int i = 0; i < listUpcomingMovies.length(); i++) {
                        JSONObject upcomingMovieItem = listUpcomingMovies.getJSONObject(i);
                        int id = upcomingMovieItem.getInt("id");
                        listMovieItems.add(new Movie(upcomingMovieItem, id));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

        return listMovieItems;
    }
}
