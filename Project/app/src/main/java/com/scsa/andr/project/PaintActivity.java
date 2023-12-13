package com.scsa.andr.project;

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

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

class Point {
    float x;
    float y;
    int color;
    float stroke;

    boolean isContinue;

    public Point(float x, float y, int color, float stroke, boolean isContinue) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.stroke = stroke;
        this.isContinue = isContinue;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                ", color=" + color +
                ", stroke=" + stroke +
                ", isContinue=" + isContinue +
                '}';
    }
}

class PathWithPaint{
    Path path; Paint paint;

    public PathWithPaint(Path path, Paint paint) {
        this.path = path;
        this.paint = paint;
    }
}

public class PaintActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity_SCSA";
    List<Point> list = new ArrayList<>();
    int color = Color.BLACK;
    float stroke = 16f;

    class MyView extends View {

        public MyView(Context context) {
            super(context);
        }

        @Override
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            Paint paint = new Paint();
            Path path = new Path();
            List<PathWithPaint> pathWithPaint = new ArrayList<>();

            for (int i = 0; i < list.size(); i++) {
                Point point = list.get(i);
                if( point.isContinue == false) {
                    path = new Path();
                    paint = new Paint();
                    paint.setStyle(Paint.Style.STROKE);  // 선만 그리기.
                    path.moveTo(list.get(i).x, list.get(i).y);
                }else {
                    path.lineTo(list.get(i).x, list.get(i).y);

                    // path는 paint객체를 담지 않으므로, path와 paint객체를 담는 collection을 추가.
                    // 그리고, 동일한 path객체인 경우에는 기존 객체를 replace하도록 해서 path객체가 중복 담기지 않도록 처리.
                    // 참고로, path가 중복으로 담긴다고 하여 오류 나지는 않음.
                    if(pathWithPaint.size() == 0) { // 처음시작시 추가.
                        paint.setColor(list.get(i).color);
                        paint.setStrokeWidth(list.get(i).stroke);
                        pathWithPaint.add(new PathWithPaint(path, paint));
                    }else{
                        PathWithPaint last = pathWithPaint.get(pathWithPaint.size()-1); // last element
                        if(last.path == path ){
                            // 이전에 이어서 들어온 path이므로, 동일한 위치에 replace
                            Log.d(TAG, "onDraw: 이어서");
                            pathWithPaint.set(pathWithPaint.size() - 1, new PathWithPaint(path, paint));
                        }else{
                            // 새로운 path이므로 추가.
                            Log.d(TAG, "onDraw: 새로운...");
                            paint.setColor(list.get(i).color);
                            paint.setStrokeWidth(list.get(i).stroke);
                            pathWithPaint.add(new PathWithPaint(path, paint));
                        }
                    }
                }
            }

            Log.d(TAG, "onDraw: size : "+pathWithPaint.size());
            pathWithPaint.forEach( (e) -> {
                canvas.drawPath(e.path, e.paint);
            } );
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
//                    Log.d(TAG, "화면 누름: " + event.getX() + ", " + event.getY());
                    list.add(new Point(event.getX(), event.getY(), color, stroke,false));
                    break;

                case MotionEvent.ACTION_MOVE:
//                    Log.d(TAG, "누른채로 움직임: " + event.getX() + ", " + event.getY());
                    list.add(new Point(event.getX(), event.getY(), color, stroke,true));
                    break;

                case MotionEvent.ACTION_UP:
//                    Log.d(TAG, "화면에서 뗌: " + event.getX() + ", " + event.getY());
                    list.add(new Point(event.getX(), event.getY(), color, stroke,true));
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
        setContentView(R.layout.activity_paint);
        
        setTitle("메모장");
        
        LinearLayout layout = findViewById(R.id.paint_view);
        MyView myView = new MyView(this);
        layout.addView(myView);

        findViewById(R.id.black_button).setOnClickListener(v -> {
            color = Color.BLACK;
        });

        findViewById(R.id.red_button).setOnClickListener(v -> {
            color = Color.RED;
        });

        findViewById(R.id.blue_button).setOnClickListener(v -> {
            color = Color.BLUE;
        });

        findViewById(R.id.eraser).setOnClickListener(v -> {
            color = Color.WHITE;
        });

        findViewById(R.id.clear_button).setOnClickListener(v -> {
            myView.clearCanvas();
            Log.d(TAG, "onCreate: clear "+list);
        });

        findViewById(R.id.ink5).setOnClickListener(v -> {
            stroke = 16f;
        });

        findViewById(R.id.ink4).setOnClickListener(v -> {
            stroke = 12f;
        });

        findViewById(R.id.ink3).setOnClickListener(v -> {
            stroke = 8f;
        });

        findViewById(R.id.ink2).setOnClickListener(v -> {
            stroke = 6f;
        });

        findViewById(R.id.ink1).setOnClickListener(v -> {
            stroke = 4f;
        });
    }
}