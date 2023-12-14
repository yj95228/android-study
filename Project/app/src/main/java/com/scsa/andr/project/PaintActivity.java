package com.scsa.andr.project;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.scsa.andr.project.databinding.ActivityPaintBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
    ActivityPaintBinding binding;
    boolean palette;
    boolean brush;
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
        binding = ActivityPaintBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        setTitle("메모장");
        
        LinearLayout layout = binding.paintView;
        MyView myView = new MyView(this);
        layout.addView(myView);

        palette = true;
        brush = true;

        binding.palette.setOnClickListener(v -> {
            palette = paletteOpen();
        });

        binding.brush.setOnClickListener(v -> {
            brush = brushOpen();
        });

        binding.cleanLayout.setVisibility(View.INVISIBLE);
        binding.clean.setOnClickListener(v -> {
            binding.cleanLayout.setVisibility(
                    (binding.cleanLayout.getVisibility() == View.VISIBLE)
                            ? View.INVISIBLE
                            : View.VISIBLE
            );
        });

        binding.blackButton.setOnClickListener(v -> {
            color = Color.BLACK;
            palette = paletteOpen();
        });

        binding.redButton.setOnClickListener(v -> {
            color = Color.RED;
            palette = paletteOpen();
        });

        binding.blueButton.setOnClickListener(v -> {
            color = Color.BLUE;
            palette = paletteOpen();
        });

        binding.brush1button.setOnClickListener(v -> {
            stroke = 4f;
            brush = brushOpen();
        });

        binding.brush2button.setOnClickListener(v -> {
            stroke = 8f;
            brush = brushOpen();
        });

        binding.brush3button.setOnClickListener(v -> {
            stroke = 16f;
            brush = brushOpen();
        });

        binding.brush4button.setOnClickListener(v -> {
            stroke = 32f;
            brush = brushOpen();
        });

        binding.brush5button.setOnClickListener(v -> {
            stroke = 64f;
            brush = brushOpen();
        });

        binding.eraser.setOnClickListener(v -> {
            color = Color.WHITE;
            binding.cleanLayout.setVisibility(View.GONE);
        });

        binding.clearButton.setOnClickListener(v -> {
            myView.clearCanvas();
            binding.cleanLayout.setVisibility(View.GONE);
        });

        binding.saveButton.setOnClickListener(v -> {
            saveImage(myView);
        });
    }

    public boolean paletteOpen(){
        if(palette){
            ValueAnimator blackAnimation = ObjectAnimator.ofFloat(binding.blackButton, "translationX", 180f);
            blackAnimation.setDuration(500);
            blackAnimation.start();
            ValueAnimator redAnimation = ObjectAnimator.ofFloat(binding.redButton, "translationX", 310f);
            redAnimation.setDuration(500);
            redAnimation.start();
            ValueAnimator blueAnimation = ObjectAnimator.ofFloat(binding.blueButton, "translationX", 440f);
            blueAnimation.setDuration(500);
            blueAnimation.start();
            return false;
        }else{
            ValueAnimator blackAnimation = ObjectAnimator.ofFloat(binding.blackButton, "translationX", 0);
            blackAnimation.setDuration(500);
            blackAnimation.start();
            ValueAnimator redAnimation = ObjectAnimator.ofFloat(binding.redButton, "translationX", 0);
            redAnimation.setDuration(500);
            redAnimation.start();
            ValueAnimator blueAnimation = ObjectAnimator.ofFloat(binding.blueButton, "translationX", 0);
            blueAnimation.setDuration(500);
            blueAnimation.start();
            return true;
        }
    }

    public boolean brushOpen(){
        Log.d(TAG, "brushOpen: "+brush);
        if(brush){
            ValueAnimator brush1Animation = ObjectAnimator.ofFloat(binding.brush1, "translationX", 180f);
            brush1Animation.setDuration(500);
            brush1Animation.start();
            ValueAnimator brush2Animation = ObjectAnimator.ofFloat(binding.brush2, "translationX", 310f);
            brush2Animation.setDuration(500);
            brush2Animation.start();
            ValueAnimator brush3Animation = ObjectAnimator.ofFloat(binding.brush3, "translationX", 440f);
            brush3Animation.setDuration(500);
            brush3Animation.start();
            ValueAnimator brush4Animation = ObjectAnimator.ofFloat(binding.brush4, "translationX", 570f);
            brush4Animation.setDuration(500);
            brush4Animation.start();
            ValueAnimator brush5Animation = ObjectAnimator.ofFloat(binding.brush5, "translationX", 700f);
            brush5Animation.setDuration(500);
            brush5Animation.start();
            return false;
        }else{
            ValueAnimator brush1Animation = ObjectAnimator.ofFloat(binding.brush1, "translationX", 0);
            brush1Animation.setDuration(500);
            brush1Animation.start();
            ValueAnimator brush2Animation = ObjectAnimator.ofFloat(binding.brush2, "translationX", 0);
            brush2Animation.setDuration(500);
            brush2Animation.start();
            ValueAnimator brush3Animation = ObjectAnimator.ofFloat(binding.brush3, "translationX", 0);
            brush3Animation.setDuration(500);
            brush3Animation.start();
            ValueAnimator brush4Animation = ObjectAnimator.ofFloat(binding.brush4, "translationX", 0);
            brush4Animation.setDuration(500);
            brush4Animation.start();
            ValueAnimator brush5Animation = ObjectAnimator.ofFloat(binding.brush5, "translationX", 0);
            brush5Animation.setDuration(500);
            brush5Animation.start();
            return true;
        }
    }

    public void saveImage(View view) {
        // MyView에서 그려진 이미지를 Bitmap으로 변환
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        // 저장할 디렉토리 생성
        File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyPaintApp");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // 파일명 생성 (현재 시간 기반으로 설정)
        String fileName = "painting_" + System.currentTimeMillis() + ".png";

        // 파일 생성
        File file = new File(directory, fileName);
        try {
            file.createNewFile();
            FileOutputStream ostream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, ostream);
            ostream.flush();
            ostream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 갤러리에 이미지 스캔 요청
        MediaScannerConnection.scanFile(this, new String[]{file.toString()}, null, null);

        // 사용자에게 저장 완료 메시지 표시
        Toast.makeText(this, "이미지가 저장되었습니다.", Toast.LENGTH_SHORT).show();
    }
}