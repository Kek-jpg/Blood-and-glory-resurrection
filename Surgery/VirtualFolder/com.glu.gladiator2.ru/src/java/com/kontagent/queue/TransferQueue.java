/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.Cursor
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteDatabase$CursorFactory
 *  android.database.sqlite.SQLiteDiskIOException
 *  android.database.sqlite.SQLiteOpenHelper
 *  android.database.sqlite.SQLiteStatement
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.util.Log
 *  java.lang.IllegalArgumentException
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.Thread
 *  java.lang.Throwable
 *  java.util.ArrayList
 *  java.util.List
 *  java.util.concurrent.BlockingQueue
 *  java.util.concurrent.LinkedBlockingQueue
 *  java.util.concurrent.ThreadPoolExecutor
 *  java.util.concurrent.TimeUnit
 */
package com.kontagent.queue;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDiskIOException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.kontagent.KontagentLog;
import com.kontagent.Stateful;
import com.kontagent.connectivity.ConnectivityListener;
import com.kontagent.connectivity.ConnectivityTracker;
import com.kontagent.network.asynchttpclient.AsyncHttpClient;
import com.kontagent.network.asynchttpclient.AsyncHttpResponseHandler;
import com.kontagent.queue.IKTQueue;
import com.kontagent.queue.ITransferQueueListener;
import com.kontagent.queue.Message;
import com.kontagent.util.Waiter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TransferQueue
extends Stateful
implements IKTQueue,
ConnectivityListener {
    public static final int MAX_MESSAGE_QUEUE_SIZE = 500;
    private static final String TAG = TransferQueue.class.getSimpleName();
    private final long ERROR_CASE_SEND_OPERATION_INTERVAL = 10000L;
    private final int MSG_CLEAR_MESSAGE_QUEUE = 5;
    private final int MSG_ENQUEUE_MESSAGE = 2;
    private final int MSG_PROCESS_QUEUE = 0;
    private final int MSG_QUIT = 1;
    private final int MSG_REMOVE_MESSAGE = 3;
    private final long RETRY_CASE_SEND_OPERATION_INTERVAL = 1000L;
    private final ConnectivityTracker connectivityTracker;
    private final Context context;
    private Message currentMessage;
    private DBHelper dbHelper;
    private String dbName;
    private boolean hasNetwork;
    private final AsyncHttpClient httpClient;
    private Handler mHandler;
    private RunnerThread mRunner;
    private int maxQueueSize = 500;
    private String queueId;
    private final Runnable queueProcessTimer;
    private boolean runnerIsRunning;
    private ITransferQueueListener transferQueueListener;

    public TransferQueue(Context context, String string2) {
        this.queueProcessTimer = new Runnable(){

            public void run() {
                TransferQueue.this.doProcessQueue();
            }
        };
        if (string2 == null || string2.length() == 0) {
            throw new IllegalArgumentException("queueId must be either not null or have positive length");
        }
        this.context = context;
        this.queueId = string2;
        this.connectivityTracker = new ConnectivityTracker(this.context);
        this.hasNetwork = true;
        this.httpClient = new AsyncHttpClient();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, (BlockingQueue)new LinkedBlockingQueue());
        this.httpClient.setThreadPool(threadPoolExecutor);
        this.runnerIsRunning = false;
    }

    private void changed() {
        this.setChanged();
        this.notifyObservers();
    }

    private void doClear() {
        KontagentLog.d(TAG, "clear()");
        if (!this.runnerIsRunning) {
            return;
        }
        this.dbHelper.deleteAll();
        this.changed();
    }

    private void doEnqueueMessage(Message message) {
        KontagentLog.d("Trying to enqueue a message: " + message.toString());
        if (!this.runnerIsRunning) {
            return;
        }
        if (this.queueSize() > this.maxQueueSize) {
            KontagentLog.w("Cannot enqueue a message - queue is full. Dropped message: " + message.toString(), null);
            return;
        }
        if (this.dbHelper.insert(message) == -1L) {
            KontagentLog.e("Cannot enqueue a message - failed to insert a row into internal DB. Dropped message: " + message.toString(), null);
            return;
        }
        if (this.transferQueueListener != null) {
            this.transferQueueListener.queueDidAddMessage((IKTQueue)this, message);
        }
        TransferQueue.super.doProcessQueue();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void doProcessQueue() {
        block7 : {
            block6 : {
                KontagentLog.d("doProcessQueue()");
                if (!this.runnerIsRunning) break block6;
                if (!this.hasNetwork) {
                    KontagentLog.i(TAG, "No network available, scheduling timer...");
                    this.setupTimer(10000L);
                    return;
                }
                if (this.httpClient.isRunning()) {
                    this.setupTimer(1000L);
                    return;
                }
                this.currentMessage = null;
                Message message = this.dbHelper.peek();
                if (message != null && !message.equals((Object)this.currentMessage)) {
                    String string2 = TAG;
                    Object[] arrobject = new Object[]{message.getSessionId(), message.getId(), message.getUrl()};
                    KontagentLog.i(string2, String.format((String)"Processing message: [session ID = %s, message ID = %s, URL = %s]", (Object[])arrobject));
                    this.currentMessage = message;
                    this.httpClient.get(this.currentMessage.getUrl(), new KTHttpResponseHandler(this, null));
                    return;
                }
                if (this.transferQueueListener != null) break block7;
            }
            return;
        }
        this.transferQueueListener.queueDidFinishedProcessing(this);
    }

    private void doRemoveMessage(Long l) {
        if (!this.runnerIsRunning) {
            return;
        }
        KontagentLog.d("Trying to remove a message id: " + l.toString());
        this.dbHelper.removeMessage(l);
        if (this.transferQueueListener != null) {
            this.transferQueueListener.queueDidRemoveMessage((IKTQueue)this, l);
        }
        TransferQueue.super.doProcessQueue();
    }

    private void processQueue() {
        if (this.mHandler != null) {
            this.mHandler.sendEmptyMessage(0);
        }
    }

    private void removeMessage(Long l) {
        if (this.mHandler != null) {
            android.os.Message message = new android.os.Message();
            message.what = 3;
            message.obj = l;
            this.mHandler.sendMessage(message);
        }
    }

    private void setupTimer(long l) {
        TransferQueue.super.stopTimer();
        if (this.mHandler != null) {
            this.mHandler.postDelayed(this.queueProcessTimer, l);
        }
    }

    private void stopQueueProcessing() {
        if (this.mHandler != null) {
            KontagentLog.d(TAG, "Got MSG_QUIT message. Stopping transfer queue processing");
            this.mHandler.sendEmptyMessage(1);
        }
    }

    private void stopTimer() {
        if (this.mHandler != null) {
            KontagentLog.d(TAG, "Stopping queue processing timer");
            this.mHandler.removeCallbacks(this.queueProcessTimer);
        }
    }

    @Override
    public void clear() {
        if (this.mHandler != null) {
            this.mHandler.sendEmptyMessage(5);
        }
    }

    @Override
    public void enqueue(Message message) {
        if (this.mHandler != null) {
            android.os.Message message2 = new android.os.Message();
            message2.what = 2;
            message2.obj = message;
            this.mHandler.sendMessage(message2);
            return;
        }
        KontagentLog.e("Unable to enqueue a message! Run loop hasn't been yet started", null);
    }

    public ConnectivityTracker getConnectivityTracker() {
        return this.connectivityTracker;
    }

    public String getDbName() {
        return this.dbName;
    }

    public int getMaxQueueSize() {
        return this.maxQueueSize;
    }

    @Override
    public void onConnectivityChanged(ConnectivityTracker connectivityTracker, boolean bl) {
        if (this.transferQueueListener != null) {
            this.transferQueueListener.queueDidReachabilityChanged(bl);
        }
        this.hasNetwork = bl;
        TransferQueue.super.processQueue();
    }

    @Override
    protected void onPause() {
        KontagentLog.d(TAG, "onPause()");
        this.stopTimer();
        this.connectivityTracker.stopTracking();
        this.connectivityTracker.setListener(null);
        this.httpClient.cancelRequests(this.context, true);
        this.runnerIsRunning = false;
    }

    @Override
    protected void onResume() {
        KontagentLog.d(TAG, "onResume()");
        this.connectivityTracker.startTracking();
        this.connectivityTracker.setListener(this);
        if (this.mRunner == null) {
            this.mRunner = new RunnerThread(this, null);
            this.mRunner.start();
            KontagentLog.d("mRunner.start()");
        }
        this.runnerIsRunning = true;
        this.processQueue();
    }

    @Override
    protected void onStart() {
        KontagentLog.d(TAG, "onStart()");
        this.dbName = this.queueId;
        this.dbHelper = new DBHelper(this.context, this.dbName);
    }

    @Override
    protected void onStop() {
        KontagentLog.d(TAG, "onStop()");
        this.stopQueueProcessing();
    }

    public List<Message> peekAll() {
        return this.dbHelper.peekAll();
    }

    @Override
    public int queueSize() {
        if (this.mRunner != null && this.mHandler != null && !this.runnerIsRunning) {
            return 0;
        }
        return this.dbHelper.queueSize();
    }

    public void setMaxQueueSize(int n) {
        this.maxQueueSize = n;
    }

    public TransferQueue setTransferQueueListener(ITransferQueueListener iTransferQueueListener) {
        this.transferQueueListener = iTransferQueueListener;
        KontagentLog.d(TAG, String.format((String)"New transfer queue listener set: %s", (Object[])new Object[]{iTransferQueueListener}));
        return this;
    }

    private static class DBHelper {
        private static final String COUNT = "select count(*) from api_calls";
        private static final int DATABASE_VERSION = 3;
        private static final String DELETE = "delete from api_calls where msg_id=?";
        private static final String INSERT = "insert into api_calls(url,name,session_id,timestamp,msg_id) values (?,?,?,?,?)";
        private static final String PEEK = "select id,url,name,session_id,timestamp,msg_id from api_calls where id=(select min(id) from api_calls)";
        private static final String PEEK_ALL = "select url,name,session_id,timestamp,msg_id from api_calls";
        private static final String TABLE_NAME = "api_calls";
        private static final String TAG = DBHelper.class.getSimpleName();
        private final SQLiteDatabase db;
        private final SQLiteStatement deleteStmt;
        private final SQLiteStatement insertStmt;

        public DBHelper(Context context, String string2) {
            this.db = new OpenHelper(context, string2).getWritableDatabase();
            this.insertStmt = this.db.compileStatement(INSERT);
            this.deleteStmt = this.db.compileStatement(DELETE);
        }

        public void close() {
            this.db.close();
            this.insertStmt.close();
            this.deleteStmt.close();
        }

        public void deleteAll() {
            try {
                this.db.delete(TABLE_NAME, null, null);
                return;
            }
            catch (SQLiteDiskIOException sQLiteDiskIOException) {
                Log.e((String)TAG, (String)"Failed to delete offline records.", (Throwable)sQLiteDiskIOException);
                return;
            }
        }

        public long insert(Message message) {
            this.insertStmt.bindString(1, message.getUrl());
            this.insertStmt.bindString(2, message.getName());
            this.insertStmt.bindString(3, message.getSessionId());
            this.insertStmt.bindString(4, message.getTimestamp());
            this.insertStmt.bindLong(5, message.getId().longValue());
            return this.insertStmt.executeInsert();
        }

        public Message peek() {
            Cursor cursor = this.db.rawQuery(PEEK, null);
            if (cursor != null && cursor.moveToFirst()) {
                Message message = new Message(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getLong(5));
                if (!cursor.isClosed()) {
                    cursor.close();
                    return message;
                }
            }
            return null;
        }

        public List<Message> peekAll() {
            ArrayList arrayList = new ArrayList();
            Cursor cursor = this.db.rawQuery(PEEK_ALL, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    arrayList.add((Object)new Message(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getLong(4)));
                } while (cursor.moveToNext());
                if (!cursor.isClosed()) {
                    cursor.close();
                }
            }
            return arrayList;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public int queueSize() {
            Cursor cursor = this.db.rawQuery(COUNT, null);
            if (cursor == null) {
                return 0;
            }
            boolean bl = cursor.moveToFirst();
            int n = 0;
            if (bl) {
                n = cursor.getInt(0);
            }
            if (cursor.isClosed()) return n;
            cursor.close();
            return n;
        }

        public void removeMessage(Long l) {
            this.deleteStmt.bindLong(1, l.longValue());
            this.deleteStmt.execute();
        }

        private static class OpenHelper
        extends SQLiteOpenHelper {
            OpenHelper(Context context, String string2) {
                super(context, string2, null, 3);
            }

            public void onCreate(SQLiteDatabase sQLiteDatabase) {
                sQLiteDatabase.execSQL("CREATE TABLE api_calls (id INTEGER PRIMARY KEY, url TEXT, name TEXT, session_id TEXT, timestamp TEXT, msg_id INTEGER)");
            }

            public void onUpgrade(SQLiteDatabase sQLiteDatabase, int n, int n2) {
            }
        }

    }

    private class KTHttpResponseHandler
    extends AsyncHttpResponseHandler {
        final /* synthetic */ TransferQueue this$0;

        private KTHttpResponseHandler(TransferQueue transferQueue) {
            this.this$0 = transferQueue;
        }

        /* synthetic */ KTHttpResponseHandler(TransferQueue transferQueue, 1 var2_2) {
            super(transferQueue);
        }

        @Override
        public void onFailure(Throwable throwable, String string2) {
            Object[] arrobject = new Object[]{10000L};
            KontagentLog.i(String.format((String)"FAILED to complete HTTP operation => re-set timer to %s seconds", (Object[])arrobject));
            if (this.this$0.transferQueueListener != null && this.this$0.currentMessage != null) {
                this.this$0.transferQueueListener.queueDidTransferElementFailed(this.this$0, this.this$0.currentMessage.getId());
            }
            this.this$0.setupTimer(10000L);
        }

        @Override
        public void onSuccess(String string2) {
            KontagentLog.i("HTTP operation completed successfully => schedule processing of next operation...");
            if (this.this$0.currentMessage != null) {
                this.this$0.stopTimer();
                this.this$0.removeMessage(this.this$0.currentMessage.getId());
            }
        }
    }

    private class RunnerThread
    extends Thread {
        final /* synthetic */ TransferQueue this$0;

        private RunnerThread(TransferQueue transferQueue) {
            this.this$0 = transferQueue;
        }

        /* synthetic */ RunnerThread(TransferQueue transferQueue, com.kontagent.queue.TransferQueue$1 var2_2) {
            super(transferQueue);
        }

        public void run() {
            Looper.prepare();
            this.this$0.mHandler = new Handler(){

                /*
                 * Enabled aggressive block sorting
                 */
                public void handleMessage(android.os.Message message) {
                    switch (message.what) {
                        default: {
                            return;
                        }
                        case 2: {
                            RunnerThread.this.this$0.doEnqueueMessage((Message)((Object)message.obj));
                            return;
                        }
                        case 0: {
                            RunnerThread.this.this$0.doProcessQueue();
                            return;
                        }
                        case 5: {
                            RunnerThread.this.this$0.doClear();
                            return;
                        }
                        case 3: {
                            RunnerThread.this.this$0.doRemoveMessage((Long)message.obj);
                            return;
                        }
                        case 1: {
                            RunnerThread.this.this$0.httpClient.cancelRequests(RunnerThread.this.this$0.context, true);
                            RunnerThread.this.this$0.dbHelper.close();
                            RunnerThread.this.this$0.dbHelper = null;
                            RunnerThread.this.this$0.mRunner = null;
                            RunnerThread.this.this$0.mHandler = null;
                            String string2 = TAG;
                            Object[] arrobject = new Object[]{RunnerThread.this.this$0.queueId};
                            KontagentLog.d(string2, String.format((String)"Message queue [id=%s] has been stopped.", (Object[])arrobject));
                            if (RunnerThread.this.this$0.transferQueueListener == null) return;
                            RunnerThread.this.this$0.transferQueueListener.queueDidStop(RunnerThread.this.this$0);
                            return;
                        }
                    }
                }
            };
            KontagentLog.d("Starting queue processing loop...");
            Waiter.getInstance().notifyOperationCompleted();
            if (this.this$0.transferQueueListener != null) {
                this.this$0.transferQueueListener.queueDidStart(this.this$0);
            }
            Looper.loop();
        }

    }

}

