package kr.jaen.android.contacts;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import kr.jaen.android.contacts.util.CheckPermission;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity_SCSA";

    /** permission **/
    private final CheckPermission checkPermission = new CheckPermission(this);;

    private static final int PERMISSION_REQUEST_CODE = 20;
    private final String[] runtimePermissions = {
            Manifest.permission.READ_CONTACTS
    };
    /** end of permission def.*****/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!checkPermission.runtimeCheckPermission(this, runtimePermissions)) {
            ActivityCompat.requestPermissions(this, runtimePermissions, PERMISSION_REQUEST_CODE);
        } else { //이미 전체 권한이 있는 경우
            initData();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (checkPermission.hasPermission(runtimePermissions, grantResults)) {
                initData();
            } else {
                checkPermission.requestPermission();
            }
        }
    }


    public void initData(){
        TextView textview = findViewById(R.id.textview);
        // 주소록 URI
        //ContactsContract.Contacts.CONTENT_URI
        // --> content://com.android.contacts/contacts
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        if(cursor != null){
            Log.d(TAG, "initData: "+cursor.getCount());
            Log.d(TAG, "initData: "+cursor.getColumnCount());
            while(cursor.moveToNext()){
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    Log.d(TAG, i+" - name: "+cursor.getColumnName(i) + " value: "+cursor.getString(i));
                    
                }
            }
        }
    }
}
