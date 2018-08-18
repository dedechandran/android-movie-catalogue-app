package com.example.genjeh.mycataloguemovieuiux.Loader;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.content.CursorLoader;

import com.example.genjeh.mycataloguemovieuiux.Database.DbContract;

public class LoaderMovieFavorit extends CursorLoader {
    private Cursor movieFavorite;
    private boolean hasResult = false;

    @Override
    public Cursor loadInBackground() {
        return getContext().getContentResolver().query(DbContract.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void deliverResult(Cursor cursor) {
        movieFavorite = cursor;
        hasResult = true;
        super.deliverResult(cursor);
    }

    @Override
    protected void onStartLoading() {
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

    public LoaderMovieFavorit(@NonNull Context context) {
        super(context);
        onContentChanged();
    }
}
