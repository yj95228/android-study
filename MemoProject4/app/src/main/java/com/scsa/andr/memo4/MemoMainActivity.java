package com.scsa.andr.memo4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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

    MemoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);

        adapter = new MemoAdapter(MEMO);
        listView.setAdapter(adapter);

        Intent editIntent = getIntent();
        MemoDto dto = (MemoDto) editIntent.getSerializableExtra("dto");
        Log.d(TAG, "onCreate: dto"+dto);
        if (dto == null) {
            MEMO.add(new MemoDto("메모 앱 만들기1", "내용1", System.currentTimeMillis()));
            MEMO.add(new MemoDto("메모 앱 만들기2", "내용2", System.currentTimeMillis()));
            MEMO.add(new MemoDto("메모 앱 만들기3", "내용3", System.currentTimeMillis()));
        } else {
            MEMO.add(dto);
            adapter.notifyDataSetChanged();
        }

        findViewById(R.id.register).setOnClickListener(v -> {
            Intent intent = new Intent(this, MemoEditActivity.class);
            intent.putExtra("visibility", View.GONE);
//            startActivityForResult(intent, 100);
            startActivity(intent);
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, MemoEditActivity.class);
            intent.putExtra("dto", MEMO.get(position));
            intent.putExtra("position", position);
            startActivity(intent);
        });

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
//        super.onActivityResult(requestCode, resultCode, intent);
//        MemoDto dto = (MemoDto) intent.getSerializableExtra("dto");
//        intent.putExtra("visibility", View.GONE);
//        MEMO.add(dto);
//        adapter.notifyDataSetChanged();
//    }

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
                Log.d(TAG, "getView: 재사용 없음 "+position);
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


//    public void addMemoList(MemoDto memoDto){
//        MEMO.add(memoDto);
//    }
}