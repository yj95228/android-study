package com.scsa.workshop7;

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

import com.scsa.workshop7.databinding.ActivityMemoMainBinding;
import com.scsa.workshop7.http.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemoMainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity_SCSA";
    List<MemoDto> list = new ArrayList<>();
    MyAdapter adapter;

    private static final int REQUEST_PERMISSIONS = 100;

    private SMSReceiver receiver;

    private final String [] REQUIRED_PERMISSIONS = new String []{
            "android.permission.RECEIVE_SMS"
    };

    boolean hasPermission = false;

//    DBHelper dbHelper;

    ApiService apiService;

    ActivityMemoMainBinding binding;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMemoMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        apiService = ApplicationClass.retrofit.create(ApiService.class);

//        dbHelper = new DBHelper(this, "mydb.db", null, 1);
//        dbHelper.open();

//        list.add(new MemoDto("부서회의", "전체미팅 건입니다.", new Date((2023-1900),11,12).getTime()));
//        list.add(new MemoDto("개발미팅", "과정 개발 미팅입니다.", new Date((2023-1900),10,11).getTime()));
//        list.add(new MemoDto("소개팅", "미팅.", new Date((2023-1900),11,10).getTime()));

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
        refresh();
    }

    private void refresh() {
//        list = dbHelper.selectAll();

        //멀티건 조회
        apiService.getAllData().enqueue(new Callback<List<MemoDto>>() {
            @Override
            public void onResponse(Call<List<MemoDto>> call, Response<List<MemoDto>> response) {
                if(response.isSuccessful()) { //http 200번대
                    list = response.body();
                    adapter.notifyDataSetChanged();
                    Log.d(TAG, "onResponse: "+ list);
                }
            }

            // http 응답이 없는 경우 호출.
            @Override
            public void onFailure(Call<List<MemoDto>> call, Throwable t) {
                Log.d(TAG, "onFailure: t" + t);
            }
        });

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
//            intent.putExtra("position", position);
            intent.putExtra("id", list.get(position).getId());
            intent.putExtra("memo", list.get(position));
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

//                        dbHelper.insert(memo);
                        apiService.insertPost(memo).enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if (response.isSuccessful()){    // 200번대
                                    String result = response.body();
                                    Log.d(TAG, "onResponse: "+result);
                                    if ("success".equals(result)) {
                                        refresh();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Log.d(TAG, "onFailure: ");
                            }
                        });
//                        refresh();
//                        list.add(memo);
//                        adapter.notifyDataSetChanged();
                    }else if("cancel".equals(action)){

                    }
                }
            }else if(requestCode == Util.EDIT){ // 수정 삭제 취소
                if(data != null){
                    String action = data.getStringExtra("action");
                    int id = data.getIntExtra("id", -1);

                    if("save".equals(action)){
                        MemoDto dto = (MemoDto)data.getSerializableExtra("result");

//                        dbHelper.update(dto);
                        apiService.updatePost(dto).enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if (response.isSuccessful()){    // 200번대
                                    String result = response.body();
                                    Log.d(TAG, "onResponse: "+result);
                                    if ("success".equals(result)) {
                                        refresh();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                            }
                        });
//                        refresh();
//                        list.set(position, dto);
//                        adapter.notifyDataSetChanged();
                    }else if("delete".equals(action)){

                        Log.d(TAG, "onActivityResult: delete : "+id);

//                        dbHelper.delete(id + "");
                        apiService.deletePost(id+"").enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if (response.isSuccessful()){    // 200번대
                                    String result = response.body();
                                    Log.d(TAG, "onResponse: "+result);
                                    if ("success".equals(result)) {
                                        refresh();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                            }
                        });
//                        refresh();
//                        list.remove(position);
//                        adapter.notifyDataSetChanged();
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

//                dbHelper.delete(list.get(aptInfo.position).getId()+"");
                apiService.deletePost(list.get(aptInfo.position).getId()+"").enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()){    // 200번대
                            String result = response.body();
                            Log.d(TAG, "onResponse: "+result);
                            if ("success".equals(result)) {
                                refresh();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
                refresh();
//                list.remove(aptInfo.position);
//                adapter.notifyDataSetChanged();
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

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if(view == null){
                LayoutInflater inflater = LayoutInflater.from(MemoMainActivity.this);
                view = inflater.inflate(R.layout.item_row, parent, false);
            }

            MemoDto dto = list.get(position);

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
//            MemoDto dto = new MemoDto(msg, msg, System.currentTimeMillis());
//            list.add(dto);
//            adapter.notifyDataSetChanged();
        }
    };

}