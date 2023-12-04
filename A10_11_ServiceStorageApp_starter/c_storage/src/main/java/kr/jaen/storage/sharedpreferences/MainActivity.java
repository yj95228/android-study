package kr.jaen.storage.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicInteger;

import kr.jaen.storage.R;

public class MainActivity extends AppCompatActivity {

    private static final int DEFAULT_VALUE = 0;
    private TextView tvCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCount = findViewById(R.id.tv_count);

        // key-value로 저장되어있는 형태의 preference 제공
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences sharedPreferences = getSharedPreferences("abc", MODE_PRIVATE);
        AtomicInteger currentValue = new AtomicInteger(preferences.getInt("count", 0));

        updateTvCount(currentValue.get());

        findViewById(R.id.btn_add_value).setOnClickListener(v -> {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("count", currentValue.incrementAndGet());
            editor.apply();
            updateTvCount(currentValue.get());
        });

        findViewById(R.id.btn_init_value).setOnClickListener(v -> {


        });
    }

    private void updateTvCount(int value) {
        tvCount.setText("SharedPreferences Test: " + value);
    }
}