package kr.jaen.android.mp3player;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import kr.jaen.android.mp3player.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        Intent intent = new Intent(this, MusicService.class);
        Intent intent = new Intent(this, MusicForegroundService.class);

        binding.btnStartMusic.setOnClickListener( v -> {
            startService(intent);
        });

        binding.btnStopMusic.setOnClickListener( v -> {
            stopService(intent);
        });
    }
}