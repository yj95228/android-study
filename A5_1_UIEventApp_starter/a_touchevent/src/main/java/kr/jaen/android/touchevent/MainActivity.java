package kr.jaen.android.touchevent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity_SCSA";

    private static class MyTouchListener implements View.OnTouchListener {

        // Event Handler 메서드 작성
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Log.d(TAG, "화면 누름: " + event.getX() + ", " + event.getY());
                    break;

                case MotionEvent.ACTION_MOVE:
                    Log.d(TAG, "누른채로 움직임: " + event.getX() + ", " + event.getY());
                    break;

                case MotionEvent.ACTION_UP:
                    Log.d(TAG, "화면에서 뗌: " + event.getX() + ", " + event.getY());
                    break;
            }
            return false;   // true: 여기서 종료, false: 뒤를 수행 (onClick)
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Activity 화면 전체를 View 객체로 가져옴
         View view = new View(this);
         setContentView(view);

        // View에 Event Listener를 등록함
         view.setOnTouchListener(new MyTouchListener());
         view.setOnClickListener(v -> {
             Log.d(TAG, "onCreate: 클릭됨");
         });
    }
}