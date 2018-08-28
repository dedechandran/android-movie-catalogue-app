package com.example.genjeh.mycataloguemovieuiux;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.genjeh.mycataloguemovieuiux.Activity.DetailActivity;
import com.example.genjeh.mycataloguemovieuiux.Data.Movie;

/**
 * Implementation of App Widget functionality.
 */
public class MovieFavoriteWidget extends AppWidgetProvider {
    public static final String ACTION_DETAIL_ACTIVITY = "action_detail_activity";
    public static final String EXTRA_MOVIE_TITLE = "extra_movie_title";
    public static final int REQUEST_CODE_OPEN_DETAIL = 0;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.movie_favorite_widget);

        Intent intent = new Intent(context, WidgetMovieFavoriteService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        views.setRemoteAdapter(R.id.stack_view, intent);
        views.setEmptyView(R.id.stack_view, R.id.empty_view);

        Intent intentOpenDetail = new Intent(context, MovieFavoriteWidget.class);
        intentOpenDetail.setAction(ACTION_DETAIL_ACTIVITY);
        intentOpenDetail.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        PendingIntent pendingIntentOpenDetail = PendingIntent.getBroadcast(
                context, REQUEST_CODE_OPEN_DETAIL, intentOpenDetail, PendingIntent.FLAG_UPDATE_CURRENT
        );
        views.setPendingIntentTemplate(R.id.stack_view, pendingIntentOpenDetail);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(ACTION_DETAIL_ACTIVITY)){
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
            String movieTitle = intent.getStringExtra(EXTRA_MOVIE_TITLE);
            Toast.makeText(context,movieTitle,Toast.LENGTH_SHORT).show();
        }
        super.onReceive(context, intent);
    }

}

