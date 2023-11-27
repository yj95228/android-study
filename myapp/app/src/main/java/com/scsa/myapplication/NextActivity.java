package com.scsa.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        // 모든 activity는 나를 호출한 Intent를 가져올 수 있다
        Intent fromIntent = getIntent();
        String msg = fromIntent.getStringExtra("hello");
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

        findViewById(R.id.button_back).setOnClickListener(v -> {
            // back stack에 적재
            // Intent intent = new Intent(this, MainActivity.class);
            // startActivity(intent);

            // 결과는 Intent로 set 하기
            Intent intent = new Intent();
            intent.putExtra("from_next", "next activity에서 값을 전달");
            setResult(RESULT_OK, intent);

            // 현재 activity 종료
            finish();
        });
    }
}