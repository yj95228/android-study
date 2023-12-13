package com.scsa.andr.project;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.scsa.andr.project.databinding.LayoutBinding;
import com.scsa.andr.project.databinding.RowBinding;

import retrofit2.http.Url;

public class NewsActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity_SCSA";
    View selectedTab;
    ListView listView;
    MyAdapter adapter;
    List<HaniItem> list = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        setTitle("뉴스 읽기");
        listView = findViewById(R.id.result);
        adapter = new MyAdapter();
        listView.setAdapter(adapter);

        new MyAsyncTask().execute("https://www.hani.co.kr/rss/");
        selectedTab = findViewById(R.id.hani);
        selectedTab.setBackgroundColor(Color.parseColor("#79B8F4"));

        findViewById(R.id.hani).setOnClickListener(v -> {
            changeTab(v, "https://www.hani.co.kr/rss/");
        });

        findViewById(R.id.mk).setOnClickListener(v -> {
            changeTab(v, "https://www.mk.co.kr/rss/30000001/");
        });

        findViewById(R.id.etnews).setOnClickListener(v -> {
            changeTab(v, "https://rss.etnews.com/Section901.xml");
        });

        findViewById(R.id.chosun).setOnClickListener(v -> {
            changeTab(v, "https://www.chosun.com/arc/outboundfeeds/rss/?outputType=xml");
        });

        findViewById(R.id.hk).setOnClickListener(v -> {
            changeTab(v, "https://www.hankyung.com/feed/all-news");
        });

        findViewById(R.id.cnn).setOnClickListener(v -> {
            changeTab(v, "http://rss.cnn.com/rss/edition.rss");
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(position).link));
            startActivity(intent);
        });
    }

    private void changeTab(View tab, String url) {
        list.clear();
        // 이전에 선택된 탭의 배경 색상 초기화
        selectedTab.setBackgroundColor(Color.TRANSPARENT);

        // 선택된 탭을 현재 탭으로 업데이트
        selectedTab = tab;
        selectedTab.setBackgroundColor(Color.parseColor("#79B8F4"));

        // 데이터 로딩
        new MyAsyncTask().execute(url);
        adapter.notifyDataSetChanged();
    }

    class MyAsyncTask extends AsyncTask<String, String, List<HaniItem>> {

        @Override
        protected List<HaniItem> doInBackground(String... arg) {
            try {
                Log.d(TAG, "connection start....");
                InputStream input = new URL(arg[0]).openConnection().getInputStream();
                Log.d(TAG, "connection ok....");

                parsing(new BufferedReader(new InputStreamReader(input)));
            } catch (Exception e) {
                e.printStackTrace();
            }

            return list;
        }

        //data 조회 완료.
        protected void onPostExecute(List<HaniItem> result) {
            Log.d(TAG, "onPostExecute: "+list.size());
            adapter.notifyDataSetChanged();
        }

        XmlPullParser parser = Xml.newPullParser();
        private void parsing(Reader reader) throws Exception {
            parser.setInput(reader);
            Log.d(TAG, "parsing: " + parser.getNamespace());
            int eventType = parser.getEventType();
            HaniItem item = null;
            long id = 0;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String name = null;
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        name = parser.getName();
                        if (name.equalsIgnoreCase("item")) {
                            item = new HaniItem();
                            item.id = ++id;
                        } else if (item != null) {
                            if (name.equalsIgnoreCase("title")) {
                                item.title = parser.nextText();
                            } else if (name.equalsIgnoreCase("link")) {
                                item.link = parser.nextText();
                            } else if (name.equalsIgnoreCase("description")) {
                                item.description = parser.nextText();
                            } else if (name.equalsIgnoreCase("pubDate")) {
                                item.pubDate = new Date(parser.nextText());
                            } else if("dc".equalsIgnoreCase(parser.getPrefix())){   // namespace
                                if (name.equalsIgnoreCase("subject")) {
                                    item.subject = parser.nextText();
                                }else if (name.equalsIgnoreCase("category")) {
                                    item.category = parser.nextText();
                                }
                            }
//                            else if (name.equalsIgnoreCase("subject")) { // namespace를 무시해도 이슈없음.
//                                item.subject = parser.nextText();
//                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        name = parser.getName();
                        if (name.equalsIgnoreCase("item") && item != null) {
                            list.add(item);
                        }
                        break;
                }
                eventType = parser.next();
            }
        }
    }


    class MyAdapter extends BaseAdapter {

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            ViewHolder holder;

            if (convertView == null) {
                RowBinding binding = RowBinding.inflate(LayoutInflater.from(NewsActivity.this), viewGroup, false);
//                RowBinding binding = RowBinding.inflate(LayoutInflater.from(MainActivity.this), viewGroup, false);
                convertView = binding.getRoot();

                holder = new ViewHolder();
                holder.title = binding.title;
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            HaniItem item = list.get(position);

            holder.title.setText(item.title);

            return convertView;
        }

        class ViewHolder {
            TextView title;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return list.get(i).id;
        }
    }
}
