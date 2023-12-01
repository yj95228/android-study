package kr.jaen.android.thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import kr.jaen.android.thread.databinding.ActivityThreadBinding;

public class ThreadActivity extends AppCompatActivity {

    private static final String TAG = "ThreadActivity_SCSA";
    private int mainCount = 0;
    private int threadCount = 0;
    private boolean isRunning = true;


    private ActivityThreadBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityThreadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnIncrease.setOnClickListener(view -> {
            mainCount++;
            binding.tvMain.setText("mainCount=" + mainCount);
            binding.tvThread.setText("threadCount=" + threadCount);
        });

        // Thread 객체 생성
        CountThread countThread = new CountThread();
        countThread.start();
    }



    private class CountThread extends Thread {

        @Override
        public void run() {
            while (isRunning) {
                threadCount++;
                Log.d(TAG, "threadCount: " + threadCount);

                // Main 스레드가 아닌 스레드가 Activity 자원에 접근이 불가능함
                // CalledFromWrongThreadException: Only the original thread that created a view hierarchy can touch its views.
                //binding.tvThread.setText("threadCount=" + threadCount);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onPause() {
        isRunning = false;
        super.onPause();
    }
}