package com.example.genjeh.mycataloguemovieuiux;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;


import com.example.genjeh.mycataloguemovieuiux.Activity.NotificationSettingActivity;


public class AlarmReceiver extends BroadcastReceiver {
    public static final int ID_NOTIFICATION_DAILY = 1;
    public static final int ID_NOTIFICATION_MOVIE_RELEASED = 2;
    public static final String CHANNEL_ID_DAILY = "channel_id_daily";
    public static final String CHANNEL_ID_MOVIE_RELEASED = "channel_id_movie_released";
    private static final String API_KEY = BuildConfig.API_KEY;
    public static final String EXTRA_URL = "extra_url";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(NotificationSettingActivity.ACTION_DAILY_ON)) {
            showNotification(context, CHANNEL_ID_DAILY, ID_NOTIFICATION_DAILY, context.getResources().getString(R.string.app_name)
                    , context.getResources().getString(R.string.content_text_daily));
        } else if (intent.getAction().equals(NotificationSettingActivity.ACTION_MOVIE_RELEASED_ON)) {
            String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=" + API_KEY + "&language=en-US";
            Intent serviceIntent = new Intent(context, ReleasedMoviesService.class);
            serviceIntent.putExtra(EXTRA_URL, url);
            context.startService(serviceIntent);

        }

    }

    public static void showNotification(Context context, String channelId, int notifId, String title, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_local_movies_white_48dp)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_local_movies_white_48dp))
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(notifId, builder.build());
    }


}
