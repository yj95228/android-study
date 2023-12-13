package kr.jaen.android.touchevent_path;

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
class PathWithPaint{
    Path path; Paint paint;

    public PathWithPaint(Path path, Paint paint) {
        this.path = path;
        this.paint = paint;
    }
}

public class DrawSample extends View {
    private static final String TAG = "DrawSample_SCSA";
    public DrawSample(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    List<Point> pointList = new ArrayList<>();
    int color = Color.RED;
    float stroke = 10F;
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        Path path = new Path();
        List<PathWithPaint> pathWithPaint = new ArrayList<>();

        for (int i = 0; i < pointList.size(); i++) {
            Point point = pointList.get(i);
            if( point.isContinue == false) {
                path = new Path();
                paint = new Paint();
                paint.setStyle(Paint.Style.STROKE);  // 선만 그리기.
                path.moveTo(pointList.get(i).x, pointList.get(i).y);
            }else {
                path.lineTo(pointList.get(i).x, pointList.get(i).y);

                // path는 paint객체를 담지 않으므로, path와 paint객체를 담는 collection을 추가.
                // 그리고, 동일한 path객체인 경우에는 기존 객체를 replace하도록 해서 path객체가 중복 담기지 않도록 처리.
                // 참고로, path가 중복으로 담긴다고 하여 오류 나지는 않음.
                if(pathWithPaint.size() == 0) { // 처음시작시 추가.
                    paint.setColor(pointList.get(i).color);
                    paint.setStrokeWidth(pointList.get(i).stroke);
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
                        paint.setColor(pointList.get(i).color);
                        paint.setStrokeWidth(pointList.get(i).stroke);
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