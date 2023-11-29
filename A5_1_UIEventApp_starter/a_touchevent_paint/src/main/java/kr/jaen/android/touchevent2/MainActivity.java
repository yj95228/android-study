package kr.jaen.android.touchevent2;

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
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

class Point {
    float x;
    float y;

    boolean isContinue;

    public Point(float x, float y, boolean isContinue) {
        this.x = x;
        this.y = y;
        this.isContinue = isContinue;
    }
}

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity_SCSA";
    List<Point> list = new ArrayList<>();

    class MyView extends View {

        public MyView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(@NonNull Canvas canvas) {
            super.onDraw(canvas);
            Paint paint = new Paint();
            paint.setStrokeWidth(50f);
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.STROKE);

//            Log.d(TAG, "onDraw: " + list);
//            for (int i = 0; i < list.size(); i++) {
//                Point point = list.get(i);
//                if(point.isContinue){ // true
//                    canvas.drawLine(
//                            list.get(i-1).x, list.get(i-1).y, // from
//                            point.x, point.y, // to
//                            paint // paint 객체
//                    );
//                }
//            }

            // path로 그리기
            Path path = new Path();
            for (int i = 0; i < list.size(); i++) {
                if(list.get(i).isContinue){
                    path.lineTo(list.get(i).x, list.get(i).y);
                }else{
                    path.moveTo(list.get(i).x, list.get(i).y);
                }
            }
            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
//                    Log.d(TAG, "화면 누름: " + event.getX() + ", " + event.getY());
                    list.add(new Point(event.getX(), event.getY(), false));
                    break;

                case MotionEvent.ACTION_MOVE:
//                    Log.d(TAG, "누른채로 움직임: " + event.getX() + ", " + event.getY());
                    list.add(new Point(event.getX(), event.getY(), true));
                    break;

                case MotionEvent.ACTION_UP:
//                    Log.d(TAG, "화면에서 뗌: " + event.getX() + ", " + event.getY());
                    list.add(new Point(event.getX(), event.getY(), true));
                    break;
            }
            invalidate();  // 다시 그리도록 onDraw 호출
            return true;   // true: 여기서 끝, false: 다음 있으면 수행
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        LinearLayout layout = findViewById(R.id.paint_view);
        MyView myView = new MyView(this);
        layout.addView(myView);

    }
}