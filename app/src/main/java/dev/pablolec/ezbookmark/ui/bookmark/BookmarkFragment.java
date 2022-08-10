package dev.pablolec.ezbookmark.ui.bookmark;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
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
import dev.pablolec.ezbookmark.MainActivity;
import dev.pablolec.ezbookmark.R;
import dev.pablolec.ezbookmark.adapter.BookmarkAdapter;
import dev.pablolec.ezbookmark.databinding.FragmentBookmarkBinding;
import dev.pablolec.ezbookmark.listener.RecyclerTouchListener;
import dev.pablolec.ezbookmark.model.Bookmark;
import dev.pablolec.ezbookmark.model.BookmarkList;
import dev.pablolec.ezbookmark.repository.LocalDatabase;
import dev.pablolec.ezbookmark.ui.FragmentWithMenu;
import dev.pablolec.ezbookmark.ui.menu.BookmarkAltMenu;
import dev.pablolec.ezbookmark.ui.menu.BookmarkMenu;

public class BookmarkFragment extends FragmentWithMenu {
    private FragmentBookmarkBinding binding;

    private RecyclerView mMainRecyclerView;
    private BookmarkAdapter mBookmarkAdapter;
    Observer<List<Bookmark>> bookmarkListUpdateObserver = new Observer<List<Bookmark>>() {
        @Override
        public void onChanged(List<Bookmark> bookmarks) {
            mBookmarkAdapter.updateBookmarkList(bookmarks);
        }
    };
    private LocalDatabase localDatabase;
    private BookmarkViewModel viewModel;
    private BookmarkList list;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBookmarkBinding.inflate(inflater, container, false);
        localDatabase = LocalDatabase.getDatabase(getActivity().getApplicationContext());
        // testPrePopulateDB(); // DEV
        mMainRecyclerView = binding.getRoot().findViewById(R.id.bookmark_recycler_view);
        mMainRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = ((MainActivity) getActivity()).getCurrentList();
        menu = new BookmarkMenu(list);
        menuAlt = new BookmarkAltMenu(list);
        try {
            load(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((BookmarkMenu) menu).setView(binding.getRoot());
        ((BookmarkAltMenu) menuAlt).setView(binding.getRoot());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    protected void delete() {
        Bookmark selected = ((BookmarkAltMenu) menuAlt).getSelected();
        LocalDatabase.getDatabase()
                .bookmarkDao()
                .delete(selected);
        if (list != null) {
            LocalDatabase.getDatabase()
                    .bookmarkListCrossRefDao()
                    .deleteByBookmarkAndList(selected.bookmarkId, list.bookmarkListId);
        }
    }

    private void load(BookmarkList list) {
        viewModel = new ViewModelProvider(this).get(BookmarkViewModel.class);
        if (list == null) {
            localDatabase.bookmarkDao().getAllLive().observe(getViewLifecycleOwner(), bookmarkListUpdateObserver);
        } else {
            localDatabase.bookmarkDao().getAllFromList(list.bookmarkListId).observe(getViewLifecycleOwner(), bookmarkListUpdateObserver);
        }
        mBookmarkAdapter = new BookmarkAdapter();
        mMainRecyclerView.setAdapter(mBookmarkAdapter);
        mMainRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), mMainRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (isMenuAltLoaded) {
                    unloadAltMenu();
                    unselectAll();
                    return;
                }
                String url = mBookmarkAdapter.getBookmark(position).getUrl();
                if (url != null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }

            @Override
            public void onLongClick(View view, int position) {
                loadAltMenu();
                addSelected(view);
                ((BookmarkAltMenu) menuAlt).setSelected(mBookmarkAdapter.getBookmark(position));
                ((BookmarkAltMenu) menuAlt).setDeleteDialog(getDeleteDialog());
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