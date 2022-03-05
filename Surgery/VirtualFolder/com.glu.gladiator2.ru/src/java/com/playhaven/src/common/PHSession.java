/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.os.SystemClock
 *  android.preference.PreferenceManager
 *  java.lang.Object
 *  java.lang.String
 */
package com.playhaven.src.common;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import com.playhaven.src.utils.PHStringUtil;

public class PHSession {
    public static final String SCOUNT_PREF = "com_playhaven_time_in_game_scount";
    public static final String SSUM_PREF = "com_playhaven_time_in_game_ssum";
    private static PHSession mSession = null;
    private long mCurTime;
    private long mSessionCount;
    private boolean mSessionPaused = true;
    private boolean mSessionStarted = false;
    private long mSessionTime;
    private long mTotalTime;

    private PHSession(Context context) {
        PHSession.super.inflate(context);
    }

    public static PHSession getInstance(Context context) {
        if (mSession == null) {
            mSession = new PHSession(context);
        }
        return mSession;
    }

    private long getLastElapsedTime() {
        if (!this.mSessionStarted || this.mSessionPaused) {
            return 0L;
        }
        return (SystemClock.uptimeMillis() - this.mCurTime) / 1000L;
    }

    private void inflate(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences((Context)context.getApplicationContext());
        this.mTotalTime = sharedPreferences.getLong(SSUM_PREF, 0L);
        this.mSessionCount = sharedPreferences.getLong(SCOUNT_PREF, 0L);
    }

    private void pauseSession() {
        this.mSessionTime = this.getSessionTime();
        this.mSessionPaused = true;
    }

    public static PHSession regenerateInstance(Context context) {
        PHSession.getInstance(context).clear(context);
        PHSession.getInstance(context).reset();
        mSession = null;
        return PHSession.getInstance(context);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void register(Activity activity) {
        block3 : {
            block2 : {
                if (activity == null) break block2;
                mSession = PHSession.getInstance((Context)activity);
                if (PHSession.mSession.mSessionPaused) break block3;
            }
            return;
        }
        mSession.resumeSession();
    }

    private void resumeSession() {
        this.mCurTime = SystemClock.uptimeMillis();
        this.mSessionPaused = false;
    }

    private void save(Context context) {
        if (!this.mSessionStarted) {
            return;
        }
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences((Context)context.getApplicationContext()).edit();
        editor.putLong(SSUM_PREF, this.mTotalTime + this.getSessionTime());
        editor.putLong(SCOUNT_PREF, 1L + this.mSessionCount);
        editor.commit();
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void unregister(Activity activity) {
        block3 : {
            block2 : {
                if (activity == null) break block2;
                mSession = PHSession.getInstance((Context)activity);
                if (PHSession.mSession.mSessionPaused) break block2;
                mSession.pauseSession();
                if (PHSession.mSession.mSessionStarted && activity.isFinishing()) break block3;
            }
            return;
        }
        mSession.save((Context)activity);
    }

    public void clear(Context context) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences((Context)context.getApplicationContext()).edit();
        editor.remove(SSUM_PREF);
        editor.remove(SCOUNT_PREF);
        editor.commit();
    }

    public long getSessionCount() {
        return this.mSessionCount;
    }

    public long getSessionTime() {
        return this.mSessionTime + this.getLastElapsedTime();
    }

    public long getTotalTime() {
        return this.mTotalTime + this.getSessionTime();
    }

    public void reset() {
        this.mTotalTime = 0L;
        this.mSessionCount = 0L;
        this.mSessionTime = 0L;
        this.mCurTime = SystemClock.uptimeMillis();
        this.mSessionStarted = false;
        this.mSessionPaused = true;
    }

    public void start() {
        PHStringUtil.log("Starting a new session.");
        if (this.mSessionStarted) {
            this.mTotalTime += this.getSessionTime();
            this.mSessionCount = 1L + this.mSessionCount;
        }
        this.mSessionTime = 0L;
        this.mCurTime = SystemClock.uptimeMillis();
        this.mSessionStarted = true;
    }

    public void startAndReset() {
        this.start();
        this.mTotalTime = 0L;
        this.mSessionCount = 0L;
    }
}

