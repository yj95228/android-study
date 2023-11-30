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

import java.util.List;

public class ListViewHolderActivity extends AppCompatActivity {

    private static final String TAG = "ListViewActivity_SCSA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        // 1. 리스트 뷰 생성
        ListView listView = findViewById(R.id.listView);
        List<String> countryList = Country.getCountryList();

        // 2. 어댑터 생성
        MyAdapter adapter = new MyAdapter(countryList);

        // 3. 리스트 뷰와 어댑터 결합
        listView.setAdapter(adapter);

    }

    class MyAdapter extends BaseAdapter {
        int resource;
        List<String> countries;
        MyAdapter(List<String> countries){
            this.countries = countries;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            ViewHolder holder;
            if(view == null){
                LayoutInflater inf = getLayoutInflater();
                view = inf.inflate(R.layout.item_row,  parent, false);
                Log.d(TAG, "getView: Inflated..");
                TextView tv = (TextView) view.findViewById(R.id.country_name);
                holder = new ViewHolder();
                holder.textView = tv;
                view.setTag(holder);
            }else{
                // 재사용된다면
                holder = (ViewHolder) view.getTag();
            }

            holder.textView.setText(countries.get(position));


            return view;
        }

        @Override
        public int getCount() {
            return countries.size();
        }

        @Override
        public Object getItem(int position) {
            return countries.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }


    class ViewHolder {
        TextView textView;
    }

}