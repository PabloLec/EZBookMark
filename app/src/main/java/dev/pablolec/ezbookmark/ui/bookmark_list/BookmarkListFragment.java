package dev.pablolec.ezbookmark.ui.bookmark_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.pablolec.ezbookmark.R;
import dev.pablolec.ezbookmark.adapter.BookmarkListAdapter;
import dev.pablolec.ezbookmark.databinding.FragmentBookmarkListBinding;
import dev.pablolec.ezbookmark.listener.RecyclerTouchListener;
import dev.pablolec.ezbookmark.model.BookmarkList;
import dev.pablolec.ezbookmark.repository.LocalDatabase;
import dev.pablolec.ezbookmark.ui.bookmark.BookmarkViewModel;
import dev.pablolec.ezbookmark.ui.menu.BookmarkListMenu;

public class BookmarkListFragment extends Fragment {
    private FragmentBookmarkListBinding binding;

    private RecyclerView mBookmarkListRecyclerView;
    private BookmarkListAdapter mBookmarkListAdapter;
    Observer<List<BookmarkList>> bookmarkListUpdateObserver = new Observer<List<BookmarkList>>() {
        @Override
        public void onChanged(List<BookmarkList> bookmarkLists) {
            mBookmarkListAdapter.updateBookmarkList(bookmarkLists);
        }
    };
    private BookmarkListMenu menu;
    private LocalDatabase localDatabase;
    private BookmarkViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBookmarkListBinding.inflate(inflater, container, false);
        localDatabase = LocalDatabase.getDatabase(getActivity().getApplicationContext());
        mBookmarkListRecyclerView = binding.getRoot().findViewById(R.id.bookmark_list_recycler_view);
        mBookmarkListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        try {
            loadBookmarks();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        menu = new BookmarkListMenu();
        menu.setView(binding.getRoot());
        requireActivity().addMenuProvider(menu);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        requireActivity().removeMenuProvider(menu);
        binding = null;
    }

    private void loadBookmarks() {
        viewModel = new ViewModelProvider(this).get(BookmarkViewModel.class);
        localDatabase.bookmarkListDao().getAllLive().observe(getViewLifecycleOwner(), bookmarkListUpdateObserver);
        mBookmarkListAdapter = new BookmarkListAdapter();
        mBookmarkListRecyclerView.setAdapter(mBookmarkListAdapter);
        mBookmarkListRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), mBookmarkListRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }
}