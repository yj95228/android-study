package kr.jaen.android.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_1).setOnClickListener(view ->
                startActivity(new Intent(this, ListViewWithBaseAdapterActivity.class)));

        findViewById(R.id.button_2).setOnClickListener(view ->
                startActivity(new Intent(this, ListViewHolderActivity.class)));

        findViewById(R.id.button_3).setOnClickListener(view ->
                startActivity(new Intent(this, ListViewWithViewBindingActivity.class)));
    }
}