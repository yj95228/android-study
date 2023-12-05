package com.scsa.workshop7;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {

    private static final String TAG = "SMSReceiver_SCSA";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive() 호출됨::" + Thread.currentThread().getName());

        //문자메시지에서 내용 추출하여 toast한다.
        Bundle bundle = intent.getExtras();
        Object[] messages = (Object[]) bundle.get("pdus");
        SmsMessage[] smsMessage = new SmsMessage[messages.length];

        int cursor = 0;
        for (Object message : messages) {
            smsMessage[cursor++] = SmsMessage.createFromPdu((byte[]) message);
        }

        Toast.makeText(context,
                "SMSReceiver::" + smsMessage[0].getMessageBody(),
                Toast.LENGTH_SHORT).show();

        /************************************************************/
        /* 문자가 오면,  Br에서 Activity로 Message를 보내기*/
        Intent newIntent = new Intent("my_unique_name");
        newIntent.putExtra("message", smsMessage[0].getMessageBody());
        context.sendBroadcast(newIntent);
        /************************************************************/

    }
}