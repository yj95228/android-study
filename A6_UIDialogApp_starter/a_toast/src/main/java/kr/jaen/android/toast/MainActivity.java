package kr.jaen.android.toast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.zip.Inflater;

import kr.jaen.android.toast.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCallToast1.setOnClickListener(view -> {
            Toast toast = Toast.makeText(this,
                    "나 토스트야 ~",
                    Toast.LENGTH_LONG);
            toast.show();
        });

        binding.btnCallToast2.setOnClickListener(view -> {
            Toast toast = Toast.makeText(this,
                    "나 안드로이드야 ~~", Toast.LENGTH_SHORT);
            View v = getLayoutInflater().inflate(R.layout.layout_toast, null);
            toast.setView(v);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
        });
    }
}