package kr.jaen.android.thread;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import kr.jaen.android.thread.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.button0.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, ThreadActivity.class)));
        binding.button1.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, ThreadHandlerActivity.class)));
        binding.button2.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, ThreadHandlerPostActivity.class)));
        binding.button3.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, RunOnUiThreadActivity.class)));
        binding.button4.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, AsyncTaskActivity.class)));

    }
}