package kr.jaen.android.screen;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1.
        Button btnNext = findViewById(R.id.btn_next);
        btnNext.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NextActivity.class);
            startActivity(intent);
        });

        // 2.
        Button btnNextForResult = findViewById(R.id.btn_next_for_result);
        btnNextForResult.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NextActivity.class);
            intent.putExtra("name", "홍길동");
            // 2-1.
            //startActivity(intent);

            // 2-2.
            startActivityLauncher.launch(intent);
        });
    }

    // 2-2.
    private final ActivityResultContracts.StartActivityForResult contract =
            new ActivityResultContracts.StartActivityForResult();

    private final ActivityResultLauncher<Intent> startActivityLauncher =
            registerForActivityResult(contract, result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    String msg = data.getStringExtra("msg");
                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                }
            });
}