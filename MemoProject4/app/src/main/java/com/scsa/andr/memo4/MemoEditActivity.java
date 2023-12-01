package com.scsa.andr.memo4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MemoEditActivity extends AppCompatActivity {

    private static final String TAG = "ListViewActivity_SCSA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_edit);

        Intent fromIntent = getIntent();
        MemoDto dto = (MemoDto) fromIntent.getSerializableExtra("dto");
        EditText editTextText = findViewById(R.id.editTextText);
        EditText editTextTextMultiLine = findViewById(R.id.editTextTextMultiLine);

        int position = fromIntent.getIntExtra("position", -1);

        if (dto != null) {
            editTextText.setText(dto.getTitle());
            editTextTextMultiLine.setText(dto.getBody());
        } else {
            findViewById(R.id.delete_button).setVisibility(View.GONE);
        }

        Intent intent = new Intent(this, MemoMainActivity.class);
        intent.putExtra("position", position);

        findViewById(R.id.save_button).setOnClickListener(v -> {
            intent.putExtra("dto",
                    new MemoDto(
                        editTextText.getText().toString(),
                        editTextTextMultiLine.getText().toString(),
                        System.currentTimeMillis()
                    )
            );

            intent.putExtra("type", 1);
            startActivity(intent);
        });

        findViewById(R.id.delete_button).setOnClickListener(v -> {
            intent.putExtra("type", 2);
            startActivity(intent);
        });

        findViewById(R.id.cancel_button).setOnClickListener(v -> {
            intent.putExtra("type", 3);
            startActivity(intent);
        });
    }

}