package com.example.tanyayuferova.lifestylenews.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.tanyayuferova.lifestylenews.utils.DateConverter;

import java.util.Calendar;
import java.util.Date;

import static com.example.tanyayuferova.lifestylenews.data.ArticlesContract.*;

/**
 * Created by Tanya Yuferova on 12/22/2017.
 */

public class ArticlesContentProvider extends ContentProvider {

    public static final int CODE_RECENT = 100;
    public static final int CODE_FAVORITE = 200;

    private static final UriMatcher uriMatcher = buildUriMatcher();
    private ArticlesDbHelper dbHelper;

    public static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = ArticlesContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, PATH_RECENT, CODE_RECENT);
        matcher.addURI(authority, PATH_FAVORITE, CODE_FAVORITE);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        dbHelper = new ArticlesDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case CODE_RECENT: {
                if(sortOrder == null) sortOrder = "";
                sortOrder = ArticleEntry.COLUMN_PUBLISHED_AT + " desc " + sortOrder;
                cursor = dbHelper.getReadableDatabase().query(
                        ArticleEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }

            case CODE_FAVORITE: {
                if(sortOrder == null) sortOrder = "";
                sortOrder = ArticleEntry.COLUMN_FAVORITE_DATE + " desc " + sortOrder;
                selection = (selection == null ? " " : selection + " and ") + ArticleEntry.COLUMN_FAVORITE + " = 1 ";
                cursor = dbHelper.getReadableDatabase().query(
                        ArticleEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri result;
        switch (uriMatcher.match(uri)) {
            case CODE_FAVORITE: {
                Date currentDate = Calendar.getInstance().getTime();
                values.put(ArticleEntry.COLUMN_FAVORITE, true);
                values.put(ArticleEntry.COLUMN_FAVORITE_DATE, DateConverter.dateToISO8601Format(currentDate));


                long newId = dbHelper.getWritableDatabase().insert(ArticleEntry.TABLE_NAME,
                        null, values);
                if (newId > 0) {
                    result = ContentUris.withAppendedId(CONTENT_FAVORITE_URI, newId);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }

            case CODE_RECENT: {
                long newId = dbHelper.getWritableDatabase().insert(ArticleEntry.TABLE_NAME,
                        null, values);
                if (newId > 0) {
                    result = ContentUris.withAppendedId(CONTENT_FAVORITE_URI, newId);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return result;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int result;
        switch (uriMatcher.match(uri)) {
            case CODE_RECENT: {
                selection = (selection == null ? " " : selection + " and ") + ArticleEntry.COLUMN_FAVORITE + " = 0 ";
                result = dbHelper.getWritableDatabase().delete(
                        ArticleEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;
            }

            case CODE_FAVORITE: {
                selection = (selection == null ? " " : selection + " and ") + ArticleEntry.COLUMN_FAVORITE + " = 1 ";
                result = dbHelper.getWritableDatabase().delete(
                        ArticleEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (result > 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return result;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int result;
        switch (uriMatcher.match(uri)) {
            case CODE_RECENT: {
                result = dbHelper.getWritableDatabase().update(
                        ArticleEntry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            }

            case CODE_FAVORITE: {
                result = dbHelper.getWritableDatabase().update(
                        ArticleEntry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (result > 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return result;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        switch (uriMatcher.match(uri)) {
            case CODE_RECENT:
                db.beginTransaction();
                int rowsInserted = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(ArticleEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }

                if (rowsInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                return rowsInserted;

            default:
                return super.bulkInsert(uri, values);
        }
    }


    @Nullable
    @Override
    public String getType(Uri uri) {
        throw new RuntimeException("GetType function is not implemented");
    }
}
