/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.ContentProvider
 *  android.content.ContentResolver
 *  android.content.ContentUris
 *  android.content.ContentValues
 *  android.content.Context
 *  android.content.UriMatcher
 *  android.database.ContentObserver
 *  android.database.Cursor
 *  android.database.SQLException
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteDatabase$CursorFactory
 *  android.database.sqlite.SQLiteOpenHelper
 *  android.database.sqlite.SQLiteQueryBuilder
 *  android.net.Uri
 *  android.util.Log
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 */
package com.mobileapptracker;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class MATProvider
extends ContentProvider {
    public static final String CAMPAIGN_ID = "campaign_id";
    private static final String DATABASE_CREATE = "create table referrer_apps (_id integer primary key autoincrement, publisher_package_name text not null, publisher_advertiser_id text, publisher_id text, campaign_id text, tracking_id text, unique(publisher_package_name) on conflict replace);";
    private static final String DATABASE_NAME = "MAT";
    private static final String DATABASE_TABLE = "referrer_apps";
    private static final int DATABASE_VERSION = 1;
    public static final String PUBLISHER_ADVERTISER_ID = "publisher_advertiser_id";
    public static final String PUBLISHER_ID = "publisher_id";
    public static final String PUBLISHER_PACKAGE_NAME = "publisher_package_name";
    private static final int REFERRER_APPS = 1;
    public static final String TRACKING_ID = "tracking_id";
    public static final String _ID = "_id";
    private static final UriMatcher uriMatcher = new UriMatcher(-1);
    private SQLiteDatabase matDB;

    static {
        uriMatcher.addURI("*", DATABASE_TABLE, 1);
    }

    public int delete(Uri uri, String string2, String[] arrstring) {
        switch (uriMatcher.match(uri)) {
            default: {
                throw new IllegalArgumentException("Unknown URI " + (Object)uri);
            }
            case 1: 
        }
        int n = this.matDB.delete(DATABASE_TABLE, string2, arrstring);
        this.getContext().getContentResolver().notifyChange(uri, null);
        return n;
    }

    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            default: {
                throw new IllegalArgumentException("Unsupported URI: " + (Object)uri);
            }
            case 1: 
        }
        return "vnd.android.cursor.dir/vnd.mobileapptracker.referrer_apps ";
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        long l = this.matDB.insert(DATABASE_TABLE, "", contentValues);
        if (l > 0L) {
            Uri uri2 = ContentUris.withAppendedId((Uri)uri, (long)l);
            this.getContext().getContentResolver().notifyChange(uri2, null);
            return uri2;
        }
        throw new SQLException("Failed to insert row into " + (Object)uri);
    }

    public boolean onCreate() {
        this.matDB = new DatabaseHelper(this.getContext()).getWritableDatabase();
        return this.matDB != null;
    }

    public Cursor query(Uri uri, String[] arrstring, String string2, String[] arrstring2, String string3) {
        SQLiteQueryBuilder sQLiteQueryBuilder = new SQLiteQueryBuilder();
        sQLiteQueryBuilder.setTables(DATABASE_TABLE);
        if (string3 == null || string3 == "") {
            string3 = PUBLISHER_PACKAGE_NAME;
        }
        Cursor cursor = sQLiteQueryBuilder.query(this.matDB, arrstring, string2, arrstring2, null, null, string3);
        cursor.setNotificationUri(this.getContext().getContentResolver(), uri);
        return cursor;
    }

    public int update(Uri uri, ContentValues contentValues, String string2, String[] arrstring) {
        switch (uriMatcher.match(uri)) {
            default: {
                throw new IllegalArgumentException("Unknown URI " + (Object)uri);
            }
            case 1: 
        }
        int n = this.matDB.update(DATABASE_TABLE, contentValues, string2, arrstring);
        this.getContext().getContentResolver().notifyChange(uri, null);
        return n;
    }

    private static class DatabaseHelper
    extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, MATProvider.DATABASE_NAME, null, 1);
        }

        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL(MATProvider.DATABASE_CREATE);
        }

        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int n, int n2) {
            Log.w((String)"Content provider database", (String)("Upgrading database from version " + n + " to " + n2 + ", which will destroy all old data"));
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS siteids");
            this.onCreate(sQLiteDatabase);
        }
    }

}

