package com.scsa.andr.memo;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button edit_button = findViewById(R.id.edit_button);
        edit_button.setOnClickListener(v -> {
            Intent intent = new Intent(this, MemoEditActivity.class);
            startActivity(intent);
            Toast.makeText(this, "편집 화면으로", Toast.LENGTH_SHORT).show();
        });

        Button info_button = findViewById(R.id.info_button);
        info_button.setOnClickListener(v -> {
            Intent intent = new Intent(this, MemoInfoActivity.class);
            startActivity(intent);
            Toast.makeText(this, "조회 화면으로", Toast.LENGTH_SHORT).show();
        });
    }
}