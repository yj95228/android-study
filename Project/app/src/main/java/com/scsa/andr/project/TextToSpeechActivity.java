package com.scsa.andr.project;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.scsa.andr.project.databinding.ActivityTextToSpeechBinding;

import java.util.Locale;

import com.scsa.andr.project.databinding.ActivityTextToSpeechBinding;

public class TextToSpeechActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity_SCSA";

    private TextToSpeech tts;

    private ActivityTextToSpeechBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTextToSpeechBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("TTS");

        tts = new TextToSpeech(this , new TextToSpeech.OnInitListener(){
            @Override
            public void onInit(int status) {

                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.KOREA);
//                    int result = tts.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        binding.messageTV.setText("가능하지 않은 언어입니다");
                    } else {
                        binding.speakBtn.setEnabled(true);
                    }
                } else {
                    binding.messageTV.setText("TTS 변환 실패하였습니다");
                }
            }
        });

        binding.speakBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText edit = (EditText) findViewById(R.id.inputET);
                String s = edit.getText().toString();
                // tts.speak(s , TextToSpeech.QUEUE_FLUSH , null ); //old
                tts.speak(s, TextToSpeech.QUEUE_FLUSH, null, null);//from min 21
            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (tts != null) {
            // 음성 출력을 중단하고 대기 Queue 의 데이터를 비운다.
            tts.stop();
            // TTS 엔진에 할당된 리소스를 해제하기위해 반드시 호출한다.
            tts.shutdown();
        }
    }

    // 리소스 파일에 정의된 음의 빠르기(Speech Rate) 에 대한 해당 ID 의 버튼을 얻고,
    public void speechQuick(View view){
        tts.setSpeechRate( (float)2 );
    }
    public void speechNormal(View view){
        tts.setSpeechRate( (float)1 );
    }
    public void speechSlow(View view){
        tts.setSpeechRate( (float)0.5 );
    }
    // 리소스 파일에 정의된 음의 높낮이(Pitch) 에 대한 해당 ID 의 버튼을 얻고,
    public void pitchHigh(View view){
        tts.setPitch( (float)2 );
    }
    public void pitchNormal(View view){
        tts.setPitch( (float)1 );
    }
    public void pitchLow(View view){
        tts.setPitch( (float)0.5 );
    }
}
