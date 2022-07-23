package dev.pablolec.ezbookmark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import dev.pablolec.ezbookmark.adapter.MainListAdapter;
import dev.pablolec.ezbookmark.dao.BookmarkDao;
import dev.pablolec.ezbookmark.listener.RecyclerTouchListener;
import dev.pablolec.ezbookmark.model.Bookmark;
import dev.pablolec.ezbookmark.repository.LocalDatabase;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mMainRecyclerView;
    private MainListAdapter mMainListAdapter;
    private List<Bookmark> bookmarkList;
    private LocalDatabase localDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        localDatabase = LocalDatabase.getDatabase(getApplicationContext());
        testPrePopulateDB(); // DEV
        mMainRecyclerView = findViewById(R.id.main_recycler_view);
        mMainRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        try {
            loadBookmarks();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadBookmarks() throws Exception {
        bookmarkList = localDatabase.bookmarkDao().getAll();
        mMainListAdapter = new MainListAdapter(bookmarkList);
        mMainRecyclerView.setAdapter(mMainListAdapter);
        mMainRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mMainRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getApplication(), bookmarkList.get(position).getUrl(), Toast.LENGTH_SHORT).show();
                String url = bookmarkList.get(position).getUrl();
                if (url != null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }
    
    private void testPrePopulateDB() {
        InputStream is;
        try {
            is = App.getContext().getAssets().open("database.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String jsonString = new String(buffer, StandardCharsets.UTF_8);

            Bookmark[] bookMarkArray = new GsonBuilder().create().fromJson(jsonString, Bookmark[].class);
            localDatabase.bookmarkDao().insertAll(bookMarkArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}