package com.scsa.andr.paint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

class Point {
    float x;
    float y;
    int color;

    boolean isContinue;

    public Point(float x, float y, int color, boolean isContinue) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.isContinue = isContinue;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                ", color=" + color +
                ", isContinue=" + isContinue +
                '}';
    }
}

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity_SCSA";
    List<Point> list = new ArrayList<>();
    int color = Color.BLACK;

    class MyView extends View {

        public MyView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(@NonNull Canvas canvas) {
            super.onDraw(canvas);
            Paint paint = new Paint();
            paint.setStrokeWidth(10f);
            paint.setStyle(Paint.Style.STROKE);

            Log.d(TAG, "onDraw: list"+list);
            for (int i = 0; i < list.size(); i++) {
                Point point = list.get(i);
                paint.setColor(list.get(i).color);
                if(point.isContinue){ // true
                    canvas.drawLine(
                            list.get(i-1).x, list.get(i-1).y, // from
                            point.x, point.y, // to
                            paint // paint 객체
                    );
                }
            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
//                    Log.d(TAG, "화면 누름: " + event.getX() + ", " + event.getY());
                    list.add(new Point(event.getX(), event.getY(), color,false));
                    break;

                case MotionEvent.ACTION_MOVE:
//                    Log.d(TAG, "누른채로 움직임: " + event.getX() + ", " + event.getY());
                    list.add(new Point(event.getX(), event.getY(), color,true));
                    break;

                case MotionEvent.ACTION_UP:
//                    Log.d(TAG, "화면에서 뗌: " + event.getX() + ", " + event.getY());
                    list.add(new Point(event.getX(), event.getY(), color,true));
                    break;
            }
            invalidate();  // 다시 그리도록 onDraw 호출
            return true;   // true: 여기서 끝, false: 다음 있으면 수행
        }

        public void clearCanvas() {
            list.clear();
            invalidate();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout layout = findViewById(R.id.paint_view);
        MyView myView = new MyView(this);
        layout.addView(myView);

        findViewById(R.id.black_button).setOnClickListener(v -> {
            color = Color.BLACK;
            Log.d(TAG, "onCreate: 검정색 "+list);
        });

        findViewById(R.id.red_button).setOnClickListener(v -> {
            color = Color.RED;
            Log.d(TAG, "onCreate: 빨강색 "+list);
        });

        findViewById(R.id.blue_button).setOnClickListener(v -> {
            color = Color.BLUE;
            Log.d(TAG, "onCreate: 파랑색 "+list);
        });

        findViewById(R.id.clear_button).setOnClickListener(v -> {
            myView.clearCanvas();
            Log.d(TAG, "onCreate: clear "+list);
        });
    }
}