package kr.jaen.android.layout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import kr.jaen.android.layout.databinding.ActivityFramelayoutBinding;

public class FrameLayoutActivity extends AppCompatActivity {
    ActivityFramelayoutBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFramelayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //setContentView(R.layout.activity_framelayout);

        //ImageView iv01 = findViewById(R.id.imageView01);
        //ImageView iv02 = findViewById(R.id.imageView02);
        //Button btnChangeImage = findViewById(R.id.btnChangeImage);

        binding.btnChangeImage.setOnClickListener(v -> {
            if (binding.imageView01.getVisibility() == View.VISIBLE) {
                binding.imageView01.setVisibility(View.INVISIBLE);
                binding.imageView02.setVisibility(View.VISIBLE);
            }
            else if (binding.imageView01.getVisibility() == View.INVISIBLE) {
                binding.imageView01.setVisibility(View.VISIBLE);
                binding.imageView02.setVisibility(View.INVISIBLE);
            }
        });
    }
}