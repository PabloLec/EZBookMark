package dev.pablolec.ezbookmark;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.GsonBuilder;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import dev.pablolec.ezbookmark.adapter.MainListAdapter;
import dev.pablolec.ezbookmark.databinding.ActivityMainBinding;
import dev.pablolec.ezbookmark.databinding.ActivityMainBinding;
import dev.pablolec.ezbookmark.listener.RecyclerTouchListener;
import dev.pablolec.ezbookmark.model.Bookmark;
import dev.pablolec.ezbookmark.repository.LocalDatabase;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private RecyclerView mMainRecyclerView;
    private MainListAdapter mMainListAdapter;
    private List<Bookmark> bookmarkList;
    private LocalDatabase localDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setUpUI();
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

    private void setUpUI() {
        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drawer, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
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