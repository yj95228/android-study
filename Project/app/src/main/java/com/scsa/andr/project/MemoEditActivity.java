package com.scsa.andr.project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.scsa.andr.project.databinding.ActivityMemoEditBinding;

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
//        int position = fromMain.getIntExtra("position", -1);// 의미 x
        int id = fromMain.getIntExtra("id", -1);
        Log.d(TAG, "onCreate: "+memo);
        Log.d(TAG, "onCreate: "+id);

        if(memo != null){ // 수정
            initData(memo);
            initView(id);
        }else{ // 입력
            initView(-1);
        }
        initEvent(id);

    }

    private void initEvent(int id) {
        binding.save.setOnClickListener(v -> {
            MemoDto dto = new MemoDto(id, binding.title.getText().toString(), binding.body.getText().toString(), System.currentTimeMillis());
            Intent intent = new Intent();
            intent.putExtra("action", "save");
            intent.putExtra("id", id);
            intent.putExtra("result", dto);

            setResult(RESULT_OK, intent);
            finish();
        });

        binding.delete.setOnClickListener( v -> {
            Intent intent = new Intent();
            intent.putExtra("action", "delete");
            intent.putExtra("id", id);

            setResult(RESULT_OK, intent);
            finish();
        });

        binding.cancel.setOnClickListener( v -> {
            Intent intent = new Intent();
            intent.putExtra("action", "cancel");
            intent.putExtra("id", id);

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