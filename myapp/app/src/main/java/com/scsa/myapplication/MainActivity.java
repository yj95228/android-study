package com.scsa.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.next_button);
        button.setOnClickListener(v -> {
            // Toast.makeText(this, "클릭됨", Toast.LENGTH_SHORT).show();
            // Snackbar.make(v, "클릭됨", Snackbar.LENGTH_SHORT).show();
            Intent intent = new Intent(this, NextActivity.class);
            intent.putExtra("hello", "from main activity");
            // startActivity(intent);
            startActivityForResult(intent, 1);
        });
    }

    ActivityResultLauncher launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                String msg = result.getData().getStringExtra("from_next");
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            }
    );

    // startActivityForResult의 결과로 호출됨
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String msg = data.getStringExtra("from_next");
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}