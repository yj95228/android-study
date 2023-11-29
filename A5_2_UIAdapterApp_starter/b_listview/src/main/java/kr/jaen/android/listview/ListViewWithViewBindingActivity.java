package kr.jaen.android.listview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import kr.jaen.android.listview.databinding.ActivityListViewBinding;

public class ListViewWithViewBindingActivity extends AppCompatActivity {

    ActivityListViewBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        List<String> countryList = Country.getCountryList();

        // 1. 리스트 뷰 생성
//        ListView listView = findViewById(R.id.listView);

        // 2. 어댑터 생성
        EfficientAdapter adapter = new EfficientAdapter(countryList);

        // 3. 리스트 뷰와 어댑터 결합
        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemClickListener((parent, view, position, id) -> {
            Toast.makeText(this,
                    countryList.get(position) + " 선택됨...",
                    Toast.LENGTH_SHORT).show();

        });
    }



    private static class EfficientAdapter extends BaseAdapter {

        private List<String> countries;
        private int icon1;
        private int icon2;

        public EfficientAdapter(List<String> countries) {
            this.countries = countries;
            this.icon1 = R.drawable.icon48x48_1;
            this.icon2 = R.drawable.icon48x48_2;
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

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            ViewHolder holder;
            //R.layout.row_of_base_adapter_list_view
            if (view == null) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_viewbinding, parent, false);

                holder = new ViewHolder();
                holder.tvText = view.findViewById(R.id.tv_text);
                holder.ivIcon = view.findViewById(R.id.iv_icon);

                view.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            // holder를 이용해서 효율적인 데이터 바인딩을 한다.
            holder.tvText.setText(countries.get(position));
            holder.ivIcon.setImageResource((position & 1) == 1 ? icon1 : icon2);

            return view;
        }

        private class ViewHolder {
            public TextView tvText;
            public ImageView ivIcon;
        }

    }

}