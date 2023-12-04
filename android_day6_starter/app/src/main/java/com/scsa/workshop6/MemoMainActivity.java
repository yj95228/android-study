package com.scsa.workshop6;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.scsa.workshop6.databinding.ActivityMemoMainBinding;
import com.scsa.workshop6.sqlite.DBHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MemoMainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity_SCSA";
    MyAdapter adapter;

    private static final int REQUEST_PERMISSIONS = 100;

    private SMSReceiver receiver;

    private final String [] REQUIRED_PERMISSIONS = new String []{
            "android.permission.RECEIVE_SMS"
    };

    boolean hasPermission = false;

    ActivityMemoMainBinding binding;
    private DBHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMemoMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbHelper = new DBHelper(this, "mydb.db", null, 1);
        dbHelper.open();

        refresh();

        registerForContextMenu(binding.listView);

        adapter = new MyAdapter();
        binding.listView.setAdapter(adapter);
        initEvent();

        // 1. 권한이 있는지 확인.
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                REQUIRED_PERMISSIONS[0]);

        // 2. 권한이 없으면 런타임 퍼미션 창 띄우기. 있으면 정상진행.
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_PERMISSIONS);
        }else{
            hasPermission = true;
        }
    }

    // requestPermissions의 call back method
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean allPermissionGranted = true;

        if (requestCode == REQUEST_PERMISSIONS) {
            for (int grantResult : grantResults) {
                if (grantResult == PackageManager.PERMISSION_DENIED) {
                    allPermissionGranted = false;
                    break;
                }
            }
            if ( allPermissionGranted) {
                Toast.makeText(this,
                        "권한 획득 성공!", Toast.LENGTH_SHORT).show();
                hasPermission = true;
                regist();

            }
            else {
                Toast.makeText(this,
                        "권한 획득 실패!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void initEvent() {
        binding.floatingActionButton.setOnClickListener(v -> {
            startActivityForResult(new Intent(this, MemoEditActivity.class), Util.INSERT);
        });

        binding.listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(MemoMainActivity.this, MemoEditActivity.class);
            intent.putExtra("position", position);
            intent.putExtra("memo", dbHelper.select(String.valueOf(id)));
            startActivityForResult(intent, Util.EDIT);
        });
    }

    //start of options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivityForResult(new Intent(this, MemoEditActivity.class), 1);

        return super.onOptionsItemSelected(item);
    }
    //end of options menu


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(requestCode == Util.INSERT){ // 입력
                if(data != null){
                    String action = data.getStringExtra("action");
                    if("save".equals(action)){
                        MemoDto memo = (MemoDto) data.getSerializableExtra("result");
                        Log.d(TAG, "onActivityResult: "+memo);
                        dbHelper.insert(memo.getTitle(), memo.getBody(), memo.getRegDate());
                        adapter.notifyDataSetChanged();
                    }else if("cancel".equals(action)){

                    }
                }
            }else if(requestCode == Util.EDIT){ // 수정 삭제 취소
                if(data != null){
                    String action = data.getStringExtra("action");
                    int position = data.getIntExtra("position", -1);

                    if("save".equals(action)){
                        MemoDto dto = (MemoDto)data.getSerializableExtra("result");
                        dbHelper.update(String.valueOf(position), dto.getTitle(), dto.getBody(), dto.getRegDate());
                        adapter.notifyDataSetChanged();
                    }else if("delete".equals(action)){

                        Log.d(TAG, "onActivityResult: delete : "+position);
                        dbHelper.delete(String.valueOf(position));
                        adapter.notifyDataSetChanged();
                    }else if("cancel".equals(action)){

                    }
                }
            }
        }
    }

    //start of context menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0,0,0,"삭제");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        ContextMenu.ContextMenuInfo menuInfo = item.getMenuInfo();
        AdapterView.AdapterContextMenuInfo aptInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;

        AlertDialog.Builder d = new AlertDialog.Builder(this)
            .setIcon(R.drawable.baseline_warning_24)
            .setTitle("삭제 확인")
            .setMessage("정말로 삭제하시겠습니까?")
            .setPositiveButton("예", (dialog, which) -> {
                Toast.makeText(this, "position:"+aptInfo.position, Toast.LENGTH_SHORT).show();
                dbHelper.delete(String.valueOf(aptInfo.position));
                adapter.notifyDataSetChanged();
            })
            .setNegativeButton("아니오", (dialog, which) -> {
            });
            d.create().show();

        return super.onContextItemSelected(item);
    }
    //end of context menu

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return dbHelper.getDataCount();
        }

        @Override
        public Object getItem(int position) {
            return dbHelper.select(String.valueOf(position));
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if(view == null){
                LayoutInflater inflater = LayoutInflater.from(MemoMainActivity.this);
                view = inflater.inflate(R.layout.item_row, parent, false);
            }

            String result = dbHelper.select(String.valueOf(position));
            Log.d(TAG, "getView: "+result+position);
            MemoDto dto = new MemoDto("asdf", "1234", System.currentTimeMillis());

            TextView textView = view.findViewById(R.id.title);
            TextView date = view.findViewById(R.id.date);
            textView.setText(dto.getTitle());
            date.setText(Util.getFormattedDate(dto.getRegDate()));

            return view;
        }
    }

    // 2. BroadcastReceiver를 registerReceiver()를 이용하여 등록 후 사용하기
    private void regist() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");

        receiver = new SMSReceiver();
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(hasPermission){
            regist();
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(messageReceiver, new IntentFilter("my_unique_name"), RECEIVER_EXPORTED);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(receiver != null){
            unregisterReceiver(receiver);
        }

        unregisterReceiver(messageReceiver);

    }

    private BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = intent.getStringExtra("message");
            Toast.makeText(MemoMainActivity.this, "BroadcastActivity : "+msg, Toast.LENGTH_SHORT).show();

            Log.d(TAG, "onReceive: " + msg);
            dbHelper.insert(msg, msg, System.currentTimeMillis());
            adapter.notifyDataSetChanged();
        }
    };

    private void refresh() {
        String result = dbHelper.selectAll();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}