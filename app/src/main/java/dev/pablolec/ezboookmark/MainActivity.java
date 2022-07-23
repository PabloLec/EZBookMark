package dev.pablolec.ezboookmark;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import dev.pablolec.ezboookmark.adapter.MainListAdapter;

public class MainActivity extends AppCompatActivity {
    private ListView mMainListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMainListView = findViewById(R.id.main_list_view);
        mMainListView.setAdapter(new MainListAdapter());
    }
}