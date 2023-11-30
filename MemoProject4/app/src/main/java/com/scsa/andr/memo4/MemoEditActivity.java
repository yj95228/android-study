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

        if(dto != null){
            editTextText.setText(dto.getTitle());
            editTextTextMultiLine.setText(dto.getBody());
        }

        int visibility = fromIntent.getIntExtra("visibility", View.VISIBLE);
        findViewById(R.id.delete_button).setVisibility(visibility);

        findViewById(R.id.save_button).setOnClickListener(v -> {
            Intent intent = new Intent(this, MemoMainActivity.class);
            String title = editTextText.getText().toString();
            String body = editTextTextMultiLine.getText().toString();
            long date = System.currentTimeMillis();
            MemoDto new_dto = new MemoDto(title, body, date);

            intent.putExtra("dto", new_dto);
            startActivity(intent);
        });

        findViewById(R.id.cancel_button).setOnClickListener(v -> {
            Intent intent = new Intent(this, MemoMainActivity.class);
            startActivity(intent);
        });
    }

}