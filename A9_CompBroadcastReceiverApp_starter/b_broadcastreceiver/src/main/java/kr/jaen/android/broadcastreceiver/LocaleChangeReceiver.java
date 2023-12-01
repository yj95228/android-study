package kr.jaen.android.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class LocaleChangeReceiver extends BroadcastReceiver {
    private static final String TAG = "LocaleChangeReceiver_SCSA";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: receive 받기: " + intent.getAction());

    }
}