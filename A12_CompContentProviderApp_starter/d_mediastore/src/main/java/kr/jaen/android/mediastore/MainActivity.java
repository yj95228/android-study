package kr.jaen.android.mediastore;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ListActivity;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import kr.jaen.android.mediastore.util.CheckPermission;

@RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity_SCSA";

    /** permission **/
    private final CheckPermission checkPermission = new CheckPermission(this);;

    private static final int PERMISSION_REQUEST_CODE = 20;
    private final String[] runtimePermissions = {
//            Manifest.permission.READ_MEDIA_AUDIO,
            "android.permission.READ_EXTERNAL_STORAGE"
    };
    /** end of permission def.*****/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!checkPermission.runtimeCheckPermission(this, runtimePermissions)) {
            ActivityCompat.requestPermissions(this, runtimePermissions, PERMISSION_REQUEST_CODE);
        } else { //이미 전체 권한이 있는 경우
            fetch();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (checkPermission.hasPermission(runtimePermissions, grantResults)) {
                fetch();
            } else {
                checkPermission.requestPermission();
            }
        }
    }

    private void fetch() {
        Uri uri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";

        Cursor cursor = getContentResolver().query(uri, null, null, null, sortOrder);
        /*cursor = new CursorLoader(this, uri, null, null, null, sortOrder)
                .loadInBackground();*/

        String[] from = new String[] { MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DISPLAY_NAME };

        int[] to = new int[] { R.id.title1, R.id.title2 };
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.item_row, cursor, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);


        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
//            Uri uri2 = ContentUris.withAppendedId(uri, id);
//            Intent intent = new Intent(Intent.ACTION_VIEW, uri2);

//            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel://0101112222"));
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.113,126.5254"));
            startActivity(intent);
        });
    }
}