package com.example.genjeh.mycataloguemovieuiux.Database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public final class DbContract {
    public static String TABLE_NAME = "favorite";
    public static String CREATE_TABLE_FAVORITE = String.format(
            "CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "  %s TEXT NOT NULL,"
                    + "  %s TEXT NOT NULL,"
                    + "  %s TEXT NOT NULL,"
                    + "  %s TEXT NOT NULL,"
                    + "  %s TEXT NOT NULL);"
            ,TABLE_NAME,FavoriteColumns._ID,FavoriteColumns.MOVIE_ID,FavoriteColumns.MOVIE_TITLE,FavoriteColumns.MOVIE_DESC,FavoriteColumns.MOVIE_DATE,FavoriteColumns.MOVIE_PATH_IMG
    );

    public static final class FavoriteColumns implements BaseColumns {
        public static String MOVIE_ID = "movie_id";
        public static String MOVIE_TITLE = "movie_title";
        public static String MOVIE_DESC = "movie_desc";
        public static String MOVIE_DATE = "movie_date";
        public static String MOVIE_PATH_IMG = "movie_path_image";
    }



    public static final String AUTHORITY = "com.example.genjeh.mycataloguemovieuiux";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();

    public static String getColumnString(Cursor cursor,String columnName){
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor,String columnName){
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor,String columnName){
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

}
