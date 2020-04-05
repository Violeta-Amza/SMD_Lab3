package com.example.lab3;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import java.util.concurrent.ThreadLocalRandom;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * helper methods.
 */
public class MyIntentService extends IntentService {
    private static final String ACTION_GIVE_MONEY = "GIVE_MONEY";
    private static final String EXTRA_MIN_VALUE = "MIN_VALUE";
    private static final String EXTRA_MAX_VALUE = "MAX_VALUE";
    private static final String DEBUG_TAG = "MY_INTENT_SERVICE";

    public MyIntentService() {
        super("MyIntentService");
    }

    /**
     * Starts this service to perform action Give money with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionGiveMoney(Context context, int minValue, int maxValue) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_GIVE_MONEY);
        intent.putExtra(EXTRA_MIN_VALUE, minValue);
        intent.putExtra(EXTRA_MAX_VALUE, maxValue);
        Log.d(DEBUG_TAG, "startActionGiveMoney: minValue-" + minValue + " maxValue-" + maxValue);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GIVE_MONEY.equals(action)) {
                final int minValue = intent.getIntExtra(EXTRA_MIN_VALUE, 10);
                final int maxValue = intent.getIntExtra(EXTRA_MAX_VALUE, 10000);
                Log.d(DEBUG_TAG, "onHandleIntent: minValue-" + minValue + " maxValue-" + maxValue);
                handleActionGiveMoney(minValue, maxValue);
            }
        }
    }

    /**
     * Handle action GIVE MONEY in the provided background thread with the provided
     * parameters.
     */
    private void handleActionGiveMoney(int minValue, int maxValue) {

        String str = String.valueOf(ThreadLocalRandom.current().nextInt(minValue, maxValue));
        Intent intent = new Intent();
        intent.setAction(MyIntentService.class.getName());
        intent.putExtra("money", str);

        Log.d(DEBUG_TAG, "CASH:" + str);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
