package kr.jaen.android.smsreceiver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity_SCSA";
    private static final int REQUEST_PERMISSIONS = 100;

    private SMSReceiver receiver;

    private final String [] REQUIRED_PERMISSIONS = new String []{
            "android.permission.RECEIVE_SMS"
    };

    boolean hasPermission = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. 권한이 있는지 확인.
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                REQUIRED_PERMISSIONS[0]);

        // 2. 권한이 없으면 런타임 퍼미션 창 띄우기. 있으면 정상진행.
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_PERMISSIONS);
        }else{
            hasPermission = true;
        }
    }

    // requestPermissions의 call back method
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean allPermissionGranted = true;

        if (requestCode == REQUEST_PERMISSIONS) {
            for (int grantResult : grantResults) {
                if (grantResult == PackageManager.PERMISSION_DENIED) {
                    allPermissionGranted = false;
                    break;
                }
            }
            if ( allPermissionGranted) {
                Toast.makeText(MainActivity.this,
                        "권한 획득 성공!", Toast.LENGTH_SHORT).show();
                hasPermission = true;
                regist();

            }
            else {
                Toast.makeText(MainActivity.this,
                        "권한 획득 실패!", Toast.LENGTH_SHORT).show();

                showDialog();

            }
        }
    }

    private void showDialog(){
        AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle("권한확인")
                .setMessage("서비스를 정상적으로 이용하려면, 권한이 필요합니다. 설정화면으로 이동합니다.")
                .setPositiveButton("예", (dialogInterface, i) -> {
                    //권한설정화면으로 이동.
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            .setData(Uri.parse("package:" + getPackageName()));
                    startActivity(intent);
                })
                .setNegativeButton("아니오", (dialogInterface, which) -> {
                    Toast.makeText(MainActivity.this, "권한이 필요합니다.", Toast.LENGTH_SHORT).show();
                })
                .create();
        dialog.show();
    }


    // 2. BroadcastReceiver를 registerReceiver()를 이용하여 등록 후 사용하기
    private void regist() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");

        receiver = new SMSReceiver();
        registerReceiver(receiver, filter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(hasPermission){
            regist();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // RECEIVER_EXPORTED : 방송 중에서 외부 방송 수신할 것이다
            // RECEIVER_NOT_EXPORTED : 내부 방송만 수신
            registerReceiver(myReceiver, new IntentFilter("my_message"), RECEIVER_EXPORTED);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(receiver != null){
            unregisterReceiver(receiver);
        }
    }

    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            TextView textView = findViewById(R.id.sms);
            textView.setText("받은 문자메시지: "+message);
        }
    };
}