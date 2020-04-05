package com.example.lab3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.Objects;

public class MyBroadcastReceiver extends BroadcastReceiver {

    public static final String DEBUG_TAG = "MY_BROADCAST_RECEIVER";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            if (Objects.equals(intent.getAction(), MyIntentService.class.getName())) {
                String money = intent.getStringExtra("money");
                Log.d(DEBUG_TAG, money);
                Toast toast = Toast.makeText(context, "Your money " + money + " RON", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }
}
