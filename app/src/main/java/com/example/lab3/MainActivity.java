package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    protected MyStartedService myStartedService;
    protected MyBoundService myBoundService;
    protected MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindServices();
        initializeUIComponents();
    }

    /**
     * startServiceButton :
     *      start a service
     *      onStartCommand  call a method which prints a Toast with a message of this format: Bits saved: 973. The number needs to be randomly generated.
     * endServiceButton
     *      end the service
     * getCurrentDataButton
     *      call service.getCurrentDate()
     * getMoneyButton
     *      start a intent service
     */
    protected void initializeUIComponents() {

        Button startServiceButton = findViewById(R.id.start_service_button);
        Button endServiceButton = findViewById(R.id.end_service_button);
        Button getCurrentDataButton = findViewById(R.id.get_current_data_button);
        Button getMoneyButton = findViewById(R.id.get_money_button);

        IntentFilter filter = new IntentFilter(MyIntentService.class.getName());
        LocalBroadcastManager.getInstance(this).registerReceiver(myBroadcastReceiver, filter);

        startServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MyStartedService.class);
                startService(intent);
            }
        });

        endServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MyStartedService.class);
                stopService(intent);
            }
        });

        getCurrentDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(MainActivity.this, myBoundService.getCurrentDate(), Toast.LENGTH_LONG);
                toast.show();
            }
        });

        getMoneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String ACTION_GIVE_MONEY = "GIVE_MONEY";
                final String EXTRA_MIN_VALUE = "MIN_VALUE";
                final String EXTRA_MAX_VALUE = "MAX_VALUE";

                Intent intent = new Intent(MainActivity.this, MyIntentService.class);
                intent.setAction(ACTION_GIVE_MONEY);
                intent.putExtra(EXTRA_MIN_VALUE, 100);
                intent.putExtra(EXTRA_MAX_VALUE, 1000);
                startService(intent);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindServices();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myBroadcastReceiver);
    }

    private void bindServices() {
        bindServicesHelper(myBoundService, myBoundServiceConnection, MyBoundService.class);

    }

    private void bindServicesHelper(Service service, ServiceConnection serviceConnection, Class classInstance) {
        if (service == null) {
            Intent intent = new Intent(this, classInstance);
            bindService(intent, serviceConnection, BIND_AUTO_CREATE);
        }
    }

    private void unbindServices() {
        unbindService(myBoundServiceConnection);
    }

    private ServiceConnection myStartedServiceConnection =  new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            myStartedService = ((MyStartedService.MyStartedServiceBinder) iBinder).get();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            myStartedService = null;
        }
    };

    private ServiceConnection myBoundServiceConnection =  new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            myBoundService = ((MyBoundService.MyBoundServiceBinder) iBinder).get();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            myBoundService = null;
        }
    };
}
