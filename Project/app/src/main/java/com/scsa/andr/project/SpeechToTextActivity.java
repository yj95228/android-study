package com.scsa.andr.project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class SpeechToTextActivity extends AppCompatActivity {
    private final int GOOGLE_STT = 1000;
    private TextView tv;
    private ArrayList<String> result;
    private String selected;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_to_text);
        setTitle("음성인식");

        FloatingActionButton bt=(FloatingActionButton)findViewById(R.id.bt);
        tv = findViewById(R.id.tv);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                i.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
                i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
                i.putExtra(RecognizerIntent.EXTRA_PROMPT, "말을 하세요.");
                startActivityForResult(i, GOOGLE_STT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null || resultCode != RESULT_OK || requestCode != GOOGLE_STT) {
            Toast.makeText(SpeechToTextActivity.this, "오류발생", Toast.LENGTH_SHORT).show();
            return;
        }

        result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);        //인식된 데이터 list 받아옴.
        String[] sa = result.toArray(new String[result.size()]);

        AlertDialog ad = new AlertDialog.Builder(this)
                .setTitle("선택하세요.")
                .setSingleChoiceItems(sa, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selected = result.get(which);
                    }
                })
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tv.setText("인식결과 : " + selected);
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tv.setText("");
                        selected = null;
                    }
                }).create();
        ad.show();
    }
}