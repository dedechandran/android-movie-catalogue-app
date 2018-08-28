package com.example.genjeh.mycataloguemovieuiux.Loader;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.content.CursorLoader;

import com.example.genjeh.mycataloguemovieuiux.Database.DbContract;
import com.example.genjeh.mycataloguemovieuiux.Database.MovieHelper;

public class LoaderMovieFavorit extends CursorLoader {
    private Cursor movieFavorite;
    private boolean hasResult = false;
    private MovieHelper database;

    @Override
    public Cursor loadInBackground() {
        Cursor cursor;
        cursor = database.query();
        return cursor;
    }

    @Override
    public void deliverResult(Cursor cursor) {
        movieFavorite = cursor;
        hasResult = true;
        super.deliverResult(cursor);
    }

    @Override
    protected void onStartLoading() {
        database.open();
        if (takeContentChanged()) {
            forceLoad();
        } else if (hasResult) {
            deliverResult(movieFavorite);
        }
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (hasResult) {
            hasResult = false;
            movieFavorite = null;
        }
    }

    @Override
    protected void onStopLoading() {
        //database.close();
        super.onStopLoading();
    }

    public LoaderMovieFavorit(@NonNull Context context) {
        super(context);
        onContentChanged();
        database = new MovieHelper(context);
    }
}
