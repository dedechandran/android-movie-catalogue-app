package com.example.genjeh.mycataloguemovieuiux.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;



public class MovieHelper {
    private Context context;
    private SQLiteDatabase database;
    private static final String FAVORITE_TABLE_NAME = DbContract.TABLE_NAME;



    public MovieHelper(Context context) {
        this.context = context;
    }

    public MovieHelper open() throws SQLException{
        DbHelper dbHelper = new DbHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        database.close();
    }

    public Cursor query(){
        return database.query(FAVORITE_TABLE_NAME,null,null,null,null,null, DbContract.FavoriteColumns._ID + " DESC",null);
    }

    public Cursor queryMovieId(String movieId){
        return database.query(FAVORITE_TABLE_NAME,null,DbContract.FavoriteColumns.MOVIE_ID + " = ?",new String[]{movieId},null,null,null,null);
    }

    public long insert(ContentValues contentValues){
        return database.insert(FAVORITE_TABLE_NAME,null,contentValues);
    }

    public int update(String movieId,ContentValues contentValues){
        return database.update(FAVORITE_TABLE_NAME,contentValues,DbContract.FavoriteColumns.MOVIE_ID + " = ?",new String[]{movieId});
    }

    public int deleteMovieId(String movieId){
        return database.delete(FAVORITE_TABLE_NAME,DbContract.FavoriteColumns.MOVIE_ID + " = ?",new String[]{movieId});
    }
}
