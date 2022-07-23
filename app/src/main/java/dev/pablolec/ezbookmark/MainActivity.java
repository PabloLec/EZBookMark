package dev.pablolec.ezbookmark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import dev.pablolec.ezbookmark.adapter.MainListAdapter;
import dev.pablolec.ezbookmark.dao.BookmarkDao;
import dev.pablolec.ezbookmark.listener.RecyclerTouchListener;
import dev.pablolec.ezbookmark.model.Bookmark;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mMainRecyclerView;
    private MainListAdapter mMainListAdapter;
    private List<Bookmark> bookmarkList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMainRecyclerView = findViewById(R.id.main_recycler_view);
        mMainRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        try {
            loadBookmarks();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadBookmarks() throws Exception {
        bookmarkList = new BookmarkDao().loadAll();
        mMainListAdapter = new MainListAdapter(bookmarkList);
        mMainRecyclerView.setAdapter(mMainListAdapter);
        mMainRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mMainRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getApplication(), bookmarkList.get(position).getUrl(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }
}