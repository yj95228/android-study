package kr.jaen.storage.http2;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import kr.jaen.storage.ApplicationClass;
import kr.jaen.storage.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitActivity extends AppCompatActivity {
    private static final String TAG="HttpActivity_SCSA";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http2);

        TextView tv = findViewById(R.id.textview2);

        ApiService apiService =  ApplicationClass.retrofit.create(ApiService.class);

        //단건조회
        apiService.getData("1").enqueue(new Callback<Post>() {

            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.isSuccessful()) { //http 200번대
                    Post post = response.body();
                    Log.d(TAG, "onResponse: "+ post);
                    tv.setText(post.toString());
                }
            }

            // http 응답이 없는 경우 호출.
            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.d(TAG, "onFailure: t" + t);
            }
        });

        //멀티건 조회
        apiService.getAllData().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(response.isSuccessful()) { //http 200번대
                    List<Post> posts = response.body();
                    Log.d(TAG, "onResponse: "+ posts);
                }
            }

            // http 응답이 없는 경우 호출.
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.d(TAG, "onFailure: t" + t);
            }
        });



        //한건 입력
        Post post = new Post();
        post.setUserId(11);
        post.setId(1);
        post.setTitle("hello");
        post.setBody("hello. this is a body.");
        apiService.insertPost(post).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.isSuccessful()) { //http 200번대
                    Post post = response.body();
                    Log.d(TAG, "단건 입력 - onResponse: "+ post);
                }
            }

            // http 응답이 없는 경우 호출.
            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.d(TAG, "onFailure: t" + t);
            }
        });
    }
}
