package com.scsa.andr.memo4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MemoMainActivity extends AppCompatActivity {

    private static final String TAG = "ListViewActivity_SCSA";
    private static final ArrayList<MemoDto> MEMO = new ArrayList<>();

    private SMSReceiver receiver;
    private static final int REQUEST_PERMISSIONS = 100;
    private final String [] REQUIRED_PERMISSIONS = new String []{
            "android.permission.RECEIVE_SMS"
    };
    boolean hasPermission = false;

    MemoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);

        adapter = new MemoAdapter(MEMO);
        listView.setAdapter(adapter);

        Intent editIntent = getIntent();
        int type = editIntent.getIntExtra("type", 0);
        MemoDto dto = (MemoDto) editIntent.getSerializableExtra("dto");
        int pos = editIntent.getIntExtra("position", -1);

        if (type == 0) {
            // 초기 값 세팅
            MEMO.add(new MemoDto("메모 앱 만들기1", "내용1", System.currentTimeMillis()));
            MEMO.add(new MemoDto("메모 앱 만들기2", "내용2", System.currentTimeMillis()));
            MEMO.add(new MemoDto("메모 앱 만들기3", "내용3", System.currentTimeMillis()));
        } else if (type == 1) { // 등록 버튼 클릭 시
            if (pos != -1) { // 수정
                MEMO.set(pos, dto);
            } else { // 등록
                MEMO.add(dto);
            }
        } else if (type == 2) {
            // 삭제
            MEMO.remove(pos);
        }
        adapter.notifyDataSetChanged();

        findViewById(R.id.floatingActionButton).setOnClickListener(v -> {
            Intent intent = new Intent(this, MemoEditActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.register).setOnClickListener(v -> {
            Intent intent = new Intent(this, MemoEditActivity.class);
            startActivity(intent);
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, MemoEditActivity.class);
            intent.putExtra("dto", MEMO.get(position));
            intent.putExtra("position", position);
            startActivity(intent);
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            Dialog alertDialog = new AlertDialog.Builder(MemoMainActivity.this)
                    .setIcon(R.drawable.baseline_warning_24)
                    .setTitle("해당 메모를 삭제하시겠습니까?")
                    .setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MEMO.remove(position);
                            adapter.notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton("아니오",(dialog, which) -> {})
                    .create();
            alertDialog.show();
            return true;
        });

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*
        Menu버튼이 눌러지면 XML에서 정의한 Menu 리소스에 대한 포인터 값을
        menu변수로 받아 화면에 보여주는 역할을 수행한다.
         */
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();
        if (itemId == R.id.plus) {
            Intent intent = new Intent(this, MemoEditActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    class MemoAdapter extends BaseAdapter {
        List<MemoDto> list;

        MemoAdapter(List<MemoDto> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        // view를 만들어서 return
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            if (convertView == null) { // 최초 호출, 재사용되지 않을 때
                // LayoutInflater inflater = getLayoutInflater();
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                view = inflater.inflate(R.layout.item_row, parent, false); // true로 줄 일 잘 없음
            } else { // 화면에서 사라져서 재사용될 때
                Log.d(TAG, "getView: 재사용 "+position);
                view = convertView;
            }
            TextView titleView = view.findViewById(R.id.title_content);
            titleView.setText(list.get(position).title);
            TextView bodyView = view.findViewById(R.id.body_content);
            bodyView.setText(list.get(position).body);
            TextView dateView = view.findViewById(R.id.date_content);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            dateView.setText(df.format(list.get(position).reg_date));

            return view;
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
                Toast.makeText(MemoMainActivity.this,
                        "권한 획득 성공!", Toast.LENGTH_SHORT).show();
                hasPermission = true;
                regist();

            }
            else {
                Toast.makeText(MemoMainActivity.this,
                        "권한 획득 실패!", Toast.LENGTH_SHORT).show();

                showDialog();

            }
        }
    }

    private void showDialog(){
        AlertDialog dialog = new AlertDialog.Builder(MemoMainActivity.this)
                .setTitle("권한확인")
                .setMessage("서비스를 정상적으로 이용하려면, 권한이 필요합니다. 설정화면으로 이동합니다.")
                .setPositiveButton("예", (dialogInterface, i) -> {
                    //권한설정화면으로 이동.
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            .setData(Uri.parse("package:" + getPackageName()));
                    startActivity(intent);
                })
                .setNegativeButton("아니오", (dialogInterface, which) -> {
                    Toast.makeText(MemoMainActivity.this, "권한이 필요합니다.", Toast.LENGTH_SHORT).show();
                })
                .create();
        dialog.show();
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
            // RECEIVER_EXPORTED : 방송 중에서 외부 방송 수신할 것이다
            // RECEIVER_NOT_EXPORTED : 내부 방송만 수신
            registerReceiver(myReceiver, new IntentFilter("my_message"), RECEIVER_EXPORTED);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(receiver != null){
            unregisterReceiver(receiver);
        }
    }

    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            MEMO.add(new MemoDto(message, "문자메시지", System.currentTimeMillis()));
            adapter.notifyDataSetChanged();
        }
    };
}