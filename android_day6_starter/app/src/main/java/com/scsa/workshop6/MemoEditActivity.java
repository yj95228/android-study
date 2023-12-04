package com.scsa.workshop6;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.scsa.workshop6.databinding.ActivityMemoEditBinding;

public class MemoEditActivity extends AppCompatActivity {
    private static final String TAG = "MemoEdit_SCSA";
    ActivityMemoEditBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMemoEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent fromMain = getIntent();
        MemoDto memo = (MemoDto) fromMain.getSerializableExtra("memo") ;
        int position = fromMain.getIntExtra("position", -1);
        Log.d(TAG, "onCreate: "+memo);
        Log.d(TAG, "onCreate: "+position);

        if(memo != null){ // 수정
            initData(memo);
            initView(position);
        }else{ // 입력
            initView(-1);
        }
        initEvent(position);

    }

    private void initEvent(int position) {
        binding.save.setOnClickListener(v -> {
            MemoDto dto = new MemoDto(binding.title.getText().toString(), binding.body.getText().toString(), System.currentTimeMillis());
            Intent intent = new Intent();
            intent.putExtra("action", "save");
            intent.putExtra("position", position);
            intent.putExtra("result", dto);

            setResult(RESULT_OK, intent);
            finish();
        });

        binding.delete.setOnClickListener( v -> {
            Intent intent = new Intent();
            intent.putExtra("action", "delete");
            intent.putExtra("position", position);

            setResult(RESULT_OK, intent);
            finish();
        });

        binding.cancel.setOnClickListener( v -> {
            Intent intent = new Intent();
            intent.putExtra("action", "cancel");
            intent.putExtra("position", position);

            setResult(RESULT_OK, intent);
            finish();
        });
    }

    private void initView(int position) {
        if(position == -1) {
            binding.delete.setVisibility(View.GONE);
            binding.title.setEnabled(true);
            binding.regDateText.setVisibility(View.GONE);
            binding.regDate.setVisibility(View.GONE);
        }else{
            binding.delete.setVisibility(View.VISIBLE);
            binding.title.setEnabled(false);
            binding.regDate.setEnabled(false);
        }
    }

    private void initData(MemoDto memo) {
        binding.title.setText(memo.getTitle());
        binding.body.setText(memo.getBody());
        binding.regDate.setText(Util.getFormattedDate(memo.getRegDate()));
    }
}