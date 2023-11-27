package com.scsa.andr.memo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MemoInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_info);

        Button back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            finish();
        });
    }
}