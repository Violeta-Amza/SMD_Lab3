package com.example.lab3;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyBoundService extends Service {

    public static final String DEBUG_TAG = "MY_BOUND_SERVICE";
    public static final String ON_CREATE = "onCreate";
    public static final String ON_START_COMMAND = "onStartCommand";
    public static final String ON_BIND = "onBind";
    public static final String ON_DESTROY = "onDestroy";
    private static final int STOP_ACTION_ICON = 3;
    public boolean serviceIsForeground = false;

    public MyBoundService() {
    }

    private MyBoundService.MyBoundServiceBinder binder = new MyBoundService.MyBoundServiceBinder();

    class MyBoundServiceBinder extends Binder {
        private MyBoundService boundService = MyBoundService.this;
        public MyBoundService get() {
            return boundService;
        }
    }

    @Override
    public void onCreate() {
        Log.d(DEBUG_TAG, ON_CREATE + ": "+ Thread.currentThread().toString());
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(DEBUG_TAG, ON_START_COMMAND + ": "+ Thread.currentThread().toString());
        // If the process is killed with no remaining start commands to deliver, then the
        // service will be stopped instead of restarted. It can be recreated later with an explicit
        // startService call
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(DEBUG_TAG, ON_BIND + ": "+ Thread.currentThread().toString());
        return binder;
    }

    public String getCurrentDate() {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formatter.format(new Date());
    }

    @Override
    public void onDestroy() {
        Log.d(DEBUG_TAG, ON_DESTROY + ": "+ Thread.currentThread().toString());
        super.onDestroy();
    }
}
