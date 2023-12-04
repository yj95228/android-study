package kr.jaen.android.service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity_SCSA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStart = findViewById(R.id.btn_start);
        Button btnStop = findViewById(R.id.btn_stop);

        //startService
        btnStart.setOnClickListener(view -> {
            Intent intent = new Intent(this, MyService.class);
            ComponentName name = new ComponentName(
                    "kr.jaen.android.service",
                    "kr.jaen.android.service.MyService");
            intent.setComponent(name);
            startService(intent);
        });

        //stopService
        btnStop.setOnClickListener(view -> {
            Intent intent = new Intent(this, MyService.class);
            stopService(intent);
        });
    }
}