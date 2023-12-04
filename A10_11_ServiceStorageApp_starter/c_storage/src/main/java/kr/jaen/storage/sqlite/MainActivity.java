package kr.jaen.storage.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import kr.jaen.storage.databinding.ActivityMainSqliteBinding;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity_SCSA";

    private ActivityMainSqliteBinding binding;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainSqliteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbHelper = new DBHelper(this, "mydb.db", null, 1);
        dbHelper.open();

        refresh();

        binding.btnInsert.setOnClickListener(view -> {
            String message = String.valueOf(binding.etMessage.getText());
            dbHelper.insert(message);
            refresh();

            Toast.makeText(this, "추가되었습니다.",
                    Toast.LENGTH_SHORT).show();
        });

        binding.btnSelect.setOnClickListener(view -> {
            String id = String.valueOf(binding.etId.getText());
            String result = dbHelper.select(id);
            binding.tvResult.setText(result);
        });

        binding.btnUpdate.setOnClickListener(view -> {
            String id = String.valueOf(binding.etId.getText());
            String message = String.valueOf(binding.etMessage.getText());
            dbHelper.update(id, message);
            refresh();

            Toast.makeText(this, "수정되었습니다.",
                    Toast.LENGTH_SHORT).show();
        });

        binding.btnDelete.setOnClickListener(view -> {
            String id = String.valueOf(binding.etId.getText());
            dbHelper.delete(id);
            refresh();

            Toast.makeText(this, "삭제되었습니다.",
                    Toast.LENGTH_SHORT).show();
        });
    }

    private void refresh() {
        String result = dbHelper.selectAll();
        binding.tvResult.setText(result);
    }
}