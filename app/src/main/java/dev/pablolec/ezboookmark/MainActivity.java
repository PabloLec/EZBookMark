package dev.pablolec.ezboookmark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

import dev.pablolec.ezboookmark.adapter.MainListAdapter;
import dev.pablolec.ezboookmark.dao.BookmarkDao;
import dev.pablolec.ezboookmark.model.Bookmark;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mMainListView;
    private MainListAdapter mMainListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMainListView = findViewById(R.id.main_recycler_view);
        mMainListView.setLayoutManager(new LinearLayoutManager(this));
        try {
            loadBookmarks();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void loadBookmarks() throws Exception {
        List<Bookmark> bookmarkList = new BookmarkDao().loadAll();
        mMainListAdapter = new MainListAdapter(bookmarkList);
        mMainListView.setAdapter(mMainListAdapter);
    }
}