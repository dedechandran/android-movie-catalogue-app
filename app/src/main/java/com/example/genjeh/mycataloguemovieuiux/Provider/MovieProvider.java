package com.example.genjeh.mycataloguemovieuiux.Provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.genjeh.mycataloguemovieuiux.Database.DbContract;
import com.example.genjeh.mycataloguemovieuiux.Database.MovieHelper;

public class MovieProvider extends ContentProvider {
    private static final int MOVIE_FAVORITE = 1;
    private static final int MOVIE_FAVORITE_ID = 2;


    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(DbContract.AUTHORITY, DbContract.TABLE_NAME, MOVIE_FAVORITE);
        uriMatcher.addURI(DbContract.AUTHORITY, DbContract.TABLE_NAME + "/#", MOVIE_FAVORITE_ID);
    }

    private MovieHelper movieHelper;

    @Override
    public boolean onCreate() {
        movieHelper = new MovieHelper(getContext());
        movieHelper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case MOVIE_FAVORITE:
                cursor = movieHelper.query();
                break;
            case MOVIE_FAVORITE_ID:
                cursor = movieHelper.queryMovieId(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long added;
        switch (uriMatcher.match(uri)) {
            case MOVIE_FAVORITE:
                added = movieHelper.insert(values);
                break;
            default:
                added = 0;
                break;
        }

        if (added > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return Uri.parse(DbContract.CONTENT_URI + "/" + added);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int deleted;

        switch (uriMatcher.match(uri)) {
            case MOVIE_FAVORITE_ID:
                deleted = movieHelper.deleteMovieId(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }

        if (deleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int updated;

        switch (uriMatcher.match(uri)) {
            case MOVIE_FAVORITE_ID:
                updated = movieHelper.update(uri.getLastPathSegment(), values);
                break;
            default:
                updated = 0;
                break;
        }

        if (updated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return updated;
    }
}
