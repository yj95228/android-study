package kr.jaen.android.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.List;

public class ListViewWithBaseAdapterActivity extends AppCompatActivity {

    private static final String TAG = "ListViewActivity_SCSA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        ListView listView = findViewById(R.id.listView);
        List<String> countryList = Country.getCountryList();

        MyAdapter adapter = new MyAdapter(countryList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Toast.makeText(
                    this,
                    countryList.get(position)+"가 선택되었음",
                    Toast.LENGTH_SHORT
            ).show();
            countryList.remove(position);
            adapter.notifyDataSetChanged(); // 화면 갱신 요청
        });

    }

    //BaseAdapter를 상속받는 MyAdapter class를 생성해 봅시다.
    //R.layout.item_row가 하나의 데이터를 표현한다.
    class MyAdapter extends BaseAdapter {
        List<String> list;

        MyAdapter(List<String> list) {
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
                // item_row.xml -> inflate : 아래 코드가 context를 받아서 좀 더 많이 씀
                // LayoutInflater inflater = getLayoutInflater();
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                view = inflater.inflate(R.layout.item_row, parent, false); // true로 줄 일 잘 없음
            } else { // 화면에서 사라져서 재사용될 때
                Log.d(TAG, "getView: 재사용 "+position);
                view = convertView;
            }
            TextView textView = view.findViewById(R.id.country_name);
            textView.setText(list.get(position));
            textView.setOnClickListener(v -> {
                Toast.makeText(
                        ListViewWithBaseAdapterActivity.this,
                        "국가 이름 선택",
                        Toast.LENGTH_SHORT
                ).show();
            });

            TextView tv = view.findViewById(R.id.country_name2);
            tv.setText("현재 국가 이름은 : " + list.get(position));

            return view;
        }
    }

}