package com.scsa.andr.project;

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

import com.scsa.andr.project.databinding.ActivityMemoMainBinding;
import com.scsa.andr.project.http.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemoMainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity_SCSA";
    List<MemoDto> list = new ArrayList<>();
    MyAdapter adapter;

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


}