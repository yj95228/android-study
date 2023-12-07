package com.scsa.andr.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.scsa.andr.project.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initEvent();
    }

    private void initEvent() {
        binding.alarmButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AlarmMainActivity.class);
            startActivity(intent);
        });

        binding.mouseButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MouseActivity.class);
            startActivity(intent);
        });

        binding.speechToTextButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SpeechToTextActivity.class);
            startActivity(intent);
        });

        binding.textToSpeechButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TextToSpeechActivity.class);
            startActivity(intent);
        });

        binding.newsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NewsActivity.class);
            startActivity(intent);
        });
    }
}