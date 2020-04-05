package com.example.lab3;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.Random;

public class MyStartedService extends Service {

    public static final String DEBUG_TAG = "MY_STARTED_SERVICE";
    public static final String ON_CREATE = "onCreate";
    public static final String ON_START_COMMAND = "onStartCommand";
    public static final String ON_BIND = "onBind";
    public static final String ON_DESTROY = "onDestroy";
    public static final String TOAST_MSG = "Bits saved: ";
    //public static final int MAX_VALUE = Integer.MAX_VALUE;
    public static final int MAX_VALUE = 10000;


    private MyStartedServiceBinder binder = new MyStartedServiceBinder();

    class MyStartedServiceBinder extends Binder {
        private MyStartedService startedService = MyStartedService.this;
        public MyStartedService get() {
            return startedService;
        }
    }

    public MyStartedService() {
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
        printToastMessage();
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(DEBUG_TAG, ON_BIND + ": "+ Thread.currentThread().toString());
        return binder;
    }

    @Override
    public void onDestroy() {
        Log.d(DEBUG_TAG, ON_DESTROY + ": "+ Thread.currentThread().toString());
        super.onDestroy();
    }

    private void printToastMessage() {
        String generatedNumber = String.valueOf(new Random().nextInt(MAX_VALUE));
        Toast toast = Toast.makeText(getApplicationContext(), TOAST_MSG  + generatedNumber + ".", Toast.LENGTH_LONG);
        toast.show();
    }
}
