package com.scsa.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.scsa.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity_SCSA";
    // 화면 xml -> java : viewBinding : java이름 xml + Binding
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ActivityMainBinding.inflate(getLayoutInflater()) == R.layout.activity_main;
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // binding.nextButton == findViewById(R.id.next_button)
        binding.nextButton.setOnClickListener(v -> {
            Log.d(TAG, "onCreate: 버튼 클릭됨");

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