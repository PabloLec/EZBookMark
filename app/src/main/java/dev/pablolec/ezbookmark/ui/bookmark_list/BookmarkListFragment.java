package dev.pablolec.ezbookmark.ui.bookmark_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.pablolec.ezbookmark.MainActivity;
import dev.pablolec.ezbookmark.R;
import dev.pablolec.ezbookmark.adapter.BookmarkListAdapter;
import dev.pablolec.ezbookmark.databinding.FragmentBookmarkListBinding;
import dev.pablolec.ezbookmark.listener.RecyclerTouchListener;
import dev.pablolec.ezbookmark.model.BookmarkList;
import dev.pablolec.ezbookmark.repository.LocalDatabase;
import dev.pablolec.ezbookmark.ui.FragmentWithMenu;
import dev.pablolec.ezbookmark.ui.bookmark.BookmarkFragment;
import dev.pablolec.ezbookmark.ui.menu.BookmarkListAltMenu;
import dev.pablolec.ezbookmark.ui.menu.BookmarkListMenu;

public class BookmarkListFragment extends FragmentWithMenu {
    private FragmentBookmarkListBinding binding;

    private RecyclerView mBookmarkListRecyclerView;
    private BookmarkListAdapter mBookmarkListAdapter;
    Observer<List<BookmarkList>> bookmarkListUpdateObserver = new Observer<List<BookmarkList>>() {
        @Override
        public void onChanged(List<BookmarkList> bookmarkLists) {
            mBookmarkListAdapter.updateBookmarkList(bookmarkLists);
        }
    };
    private LocalDatabase localDatabase;
    private BookmarkListViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBookmarkListBinding.inflate(inflater, container, false);
        localDatabase = LocalDatabase.getDatabase(getActivity().getApplicationContext());
        mBookmarkListRecyclerView = binding.getRoot().findViewById(R.id.bookmark_list_recycler_view);
        mBookmarkListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        menu = new BookmarkListMenu();
        menuAlt = new BookmarkListAltMenu();
        try {
            load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((BookmarkListMenu) menu).setView(binding.getRoot());
        ((BookmarkListAltMenu) menuAlt).setView(binding.getRoot());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    protected void delete() {
        LocalDatabase.getDatabase().bookmarkListDao().delete(((BookmarkListAltMenu) menuAlt).getSelected());
    }

    private void load() {
        viewModel = new ViewModelProvider(this).get(BookmarkListViewModel.class);
        localDatabase.bookmarkListDao().getAllLive().observe(getViewLifecycleOwner(), bookmarkListUpdateObserver);
        mBookmarkListAdapter = new BookmarkListAdapter();
        mBookmarkListRecyclerView.setAdapter(mBookmarkListAdapter);
        mBookmarkListRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), mBookmarkListRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (isMenuAltLoaded) {
                    unloadAltMenu();
                    unselectAll();
                    return;
                }
                ((MainActivity) getActivity()).setCurrentList(mBookmarkListAdapter.getBookmarkList(position));
                Navigation.findNavController(view).navigate(R.id.open_sub_list);
            }

            @Override
            public void onLongClick(View view, int position) {
                loadAltMenu();
                addSelected(view);
                ((BookmarkListAltMenu) menuAlt).setSelected(mBookmarkListAdapter.getBookmarkList(position));
                ((BookmarkListAltMenu) menuAlt).setDeleteDialog(getDeleteDialog());
            }
        }));
    }
}