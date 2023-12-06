package kr.jaen.android.calllog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSIONS = 100;
    private Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 사용자에게 권한 허용 요청하기 (Android 6.0, API 23 이상부터 필요)
        String[] permissions = { "android.permission.READ_CALL_LOG" };
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                "android.permission.READ_CALL_LOG");
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS);
        }
        else {
            fetch();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSIONS) {
            if ("android.permission.READ_CALL_LOG".equals(permissions[0])
                    && grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(MainActivity.this,
                        "권한 획득 성공!", Toast.LENGTH_SHORT).show();

                fetch();
            }
            else {
                Toast.makeText(MainActivity.this,
                        "권한 획득 실패!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void fetch() {
        Uri uri = CallLog.Calls.CONTENT_URI;
        c = getContentResolver().query(uri, null, null, null, null);
        /*c = new CursorLoader(this, uri, null, null, null, null)
                .loadInBackground();*/

        String[] from = new String[] { CallLog.Calls.NUMBER, CallLog.Calls.TYPE };
        int[] to = new int[] { R.id.title1, R.id.title2};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.item_row, c, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            c.moveToPosition(position);
            String number = c.getString(c.getColumnIndexOrThrow(CallLog.Calls.NUMBER));
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel://" + number));
            startActivity(intent);
        });
    }

}