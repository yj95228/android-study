package kr.jaen.android.touchevent3;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import kr.jaen.android.touchevent3.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity_SCSA";


    ActivityMainBinding binding;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonRed.setOnClickListener( v ->
            binding.draw.color = Color.RED
        );

        binding.buttonGreen.setOnClickListener( v ->
            binding.draw.color = Color.BLACK
        );

        binding.buttonBlue.setOnClickListener( v ->
            binding.draw.color = Color.BLUE
        );

        binding.buttonClear.setOnClickListener( v ->
                binding.draw.clear()
        );

        binding.seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                binding.textview.setText("width : " + progress + "F");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                binding.draw.stroke = seekBar.getProgress() * 1.0f; //실수로 변환

            }
        });
    }
}