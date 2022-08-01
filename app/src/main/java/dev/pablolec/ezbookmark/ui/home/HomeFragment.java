package dev.pablolec.ezbookmark.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import dev.pablolec.ezbookmark.App;
import dev.pablolec.ezbookmark.R;
import dev.pablolec.ezbookmark.adapter.MainListAdapter;
import dev.pablolec.ezbookmark.databinding.FragmentHomeBinding;
import dev.pablolec.ezbookmark.listener.RecyclerTouchListener;
import dev.pablolec.ezbookmark.model.Bookmark;
import dev.pablolec.ezbookmark.repository.LocalDatabase;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;

    private RecyclerView mMainRecyclerView;
    private MainListAdapter mMainListAdapter;
    Observer<List<Bookmark>> bookmarkListUpdateObserver = new Observer<List<Bookmark>>() {
        @Override
        public void onChanged(List<Bookmark> userArrayList) {
            mMainListAdapter.updateBookmarkList(userArrayList);
        }
    };
    private List<Bookmark> bookmarkList;
    private LocalDatabase localDatabase;
    private HomeViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        localDatabase = LocalDatabase.getDatabase(getActivity().getApplicationContext());
        // testPrePopulateDB(); // DEV
        mMainRecyclerView = binding.getRoot().findViewById(R.id.main_recycler_view);
        mMainRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        try {
            loadBookmarks();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void loadBookmarks() throws Exception {
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        localDatabase.bookmarkDao().getAllLive().observe(getViewLifecycleOwner(), bookmarkListUpdateObserver);
        mMainListAdapter = new MainListAdapter();
        mMainRecyclerView.setAdapter(mMainListAdapter);
        mMainRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), mMainRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getActivity().getApplication(), bookmarkList.get(position).getUrl(), Toast.LENGTH_SHORT).show();
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