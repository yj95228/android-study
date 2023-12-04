package kr.jaen.storage.file;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import kr.jaen.storage.R;

public class FileInputActivity extends AppCompatActivity {

    private static final String TAG = "FileInputActivity_SCSA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_input);

        TextView tvMessage = findViewById(R.id.tv_message);

        Log.d(TAG, String.valueOf(getAssets()));
        //try (BufferedReader br = new BufferedReader((new InputStreamReader(new FileInputStream(new File(getFilesDir(), "data.txt")))))) {
        try (BufferedReader br = new BufferedReader((new InputStreamReader(getAssets().open("data.txt"))))) {

            String message = br.readLine();
            while (message != null) {
                tvMessage.append(message + "\n");
                message = br.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "파일 입력 오류!");
        }
    }
}