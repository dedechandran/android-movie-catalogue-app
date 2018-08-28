package com.example.genjeh.mycataloguemovieuiux;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.genjeh.mycataloguemovieuiux.Activity.DetailActivity;
import com.example.genjeh.mycataloguemovieuiux.Data.Movie;
import com.example.genjeh.mycataloguemovieuiux.Database.MovieHelper;

import java.util.concurrent.ExecutionException;


public class WidgetMovieFavoriteService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetMovieFavoriteFactory(this.getApplicationContext(), intent);
    }

    class WidgetMovieFavoriteFactory implements RemoteViewsService.RemoteViewsFactory {
        private Context context;
        private int appWidgetId;
        private MovieHelper database;
        private Cursor cursor;
        private static final String imgUrl = "http://image.tmdb.org/t/p/w185";

        public WidgetMovieFavoriteFactory(Context context, Intent intent) {
            this.context = context;
            appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
            database = new MovieHelper(context);
        }

        @Override
        public void onCreate() {
            database.open();
            cursor = database.query();
        }

        @Override
        public void onDataSetChanged() {
            cursor = database.query();
        }

        @Override
        public void onDestroy() {
            if (cursor != null) {
                cursor = null;
            }
            database.close();
        }

        @Override
        public int getCount() {
            return cursor.getCount();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_favorite_item);
            Bitmap bitmap = null;
            Movie movie = getMovie(position);
            Intent fillIntent = new Intent();

            try {
                bitmap = Glide.with(context)
                        .load(imgUrl + movie.getMoviePosterUrl())
                        .asBitmap()
                        .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            remoteViews.setImageViewBitmap(R.id.widget_movie_favorite, bitmap);
            fillIntent.putExtra(MovieFavoriteWidget.EXTRA_MOVIE_TITLE, movie.getMovieTitle());
            remoteViews.setOnClickFillInIntent(R.id.widget_movie_favorite, fillIntent);

            return remoteViews;
        }

        private Movie getMovie(int position) {
            if (!cursor.moveToPosition(position)) {
                throw new IllegalStateException("Invalid Position");
            }

            return new Movie(cursor);
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
