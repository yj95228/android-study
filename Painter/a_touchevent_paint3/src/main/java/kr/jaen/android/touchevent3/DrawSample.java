package kr.jaen.android.touchevent3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

class Point{
    float x; float y; boolean isContinue; int color; float stroke;

    public Point(float x, float y, boolean isContinue, int color, float stroke) {
        this.x = x;
        this.y = y;
        this.isContinue = isContinue;
        this.color = color;
        this.stroke = stroke;
    }
}

public class DrawSample extends View {
    private static final String TAG = "DrawSample_SCSA";
    public DrawSample(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    List<Point> pointList = new ArrayList<>();
    int color = Color.RED;
    Paint paint = new Paint();
    float stroke = 10F;
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < pointList.size(); i++) {
            Point point = pointList.get(i);
            if( point.isContinue ) {
                paint.setColor(pointList.get(i).color);
                paint.setStrokeWidth(pointList.get(i).stroke);
                canvas.drawLine(pointList.get(i-1).x, pointList.get(i-1).y, point.x, point.y, paint);
            }
        }
    }

    public boolean onTouchEvent(MotionEvent event) {

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN :
                pointList.add(new Point(event.getX(), event.getY(), false, color, stroke));
            break;
            case MotionEvent.ACTION_MOVE :
                pointList.add(new Point(event.getX(), event.getY(), true, color, stroke));
            break;
            case MotionEvent.ACTION_UP :
                pointList.add(new Point(event.getX(), event.getY(), true, color, stroke));
            break;
        }
        invalidate();
        return true;
    }

    public void  clear(){
        pointList.clear();
        invalidate();
    }

}