package kr.jaen.android.notification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int NOTI_ID = 100;

    private NotificationManager nm;

    //33버전 부터 Runtime Permission 필요함. 권한없으면 설치 한 후 알림 보낼 수 없음.
    private final String [] REQUIRED_PERMISSIONS = new String []{
            "android.permission.POST_NOTIFICATIONS"
    };
    private final int REQUEST_CODE_PERMISSIONS = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Button btnFire = findViewById(R.id.btn_fire);
        btnFire.setOnClickListener(view -> {
            if(hasPermission){
                showNotification();
            }else{
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
        });


        // 33부터 runtime permission 필요.
        if (Build.VERSION.SDK_INT >= 33) {
            ActivityCompat.requestPermissions(
                    this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }

    }
    boolean hasPermission = true;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            for (int i = 0 ; i < grantResults.length ; i++){
                if(grantResults[i] == PackageManager.PERMISSION_DENIED){
                    hasPermission = false;
                    break;
                }
            }
            if ( hasPermission) {
                Toast.makeText(MainActivity.this,
                        "권한 획득 성공!", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this,
                        "권한 획득 실패!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    
    
    @Override
    protected void onDestroy() {
        nm.cancel(NOTI_ID);
        super.onDestroy();
    }

    private void showNotification() {

        String channelId = getPackageName() + "-" + getClass().getName();

        // Oreo 부터는 Notification Channel을 만들어야 함
        // 26: Build.VERSION_CODES.O
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    channelId,
                    "Simple Notification",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            nm.createNotificationChannel(serviceChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId);
        Intent intent = new Intent(this, MainActivity.class);
        builder.setSmallIcon(R.drawable.baseline_alarm_24);
        builder.setTicker(null);
        builder.setContentTitle("알림");
        builder.setContentText("알림이 발생했습니다.");
        builder.setAutoCancel(true);
        builder.setContentIntent(
                PendingIntent.getActivity(
                        this,
                        0,
                        intent,
                        PendingIntent.FLAG_IMMUTABLE
                )
        );

        // Android 13 (SDK 33) 부터는
        // AndroidManifest.xml에 아래 권한 추가 필요
        // android.permission.POST_NOTIFICATIONS
        nm.notify(NOTI_ID, builder.build());
    }


}