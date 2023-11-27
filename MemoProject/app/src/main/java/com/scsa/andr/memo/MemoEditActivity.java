package com.scsa.andr.memo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MemoEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_edit);

        Button register_button = findViewById(R.id.register_button);
        register_button.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            finish();
        });
    }
}