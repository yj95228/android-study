package kr.jaen.android.broadcastreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

public class BroadcastActivity extends AppCompatActivity {
    private static final String TAG = "BroadcastActivity_SCSA";

    BroadcastReceiver receiver;

    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);
        setTitle("BroadcastActivity");

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if(action.equals(Intent.ACTION_SCREEN_ON)){
                    Log.d(TAG,"ACTION_SCREEN_ON");

                }else if(action.equals(Intent.ACTION_SCREEN_OFF)){
                    Log.d(TAG,"ACTION_SCREEN_OFF");
                }else{
                    Log.d(TAG, "low battery");
                }
            }
       };

        /***** Screen On/Off *******/
        IntentFilter intentFilter2 = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        intentFilter2.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter2.addAction(Intent.ACTION_BATTERY_LOW);
        registerReceiver(receiver, intentFilter2);
        /***** Screen On/Off *******/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

}