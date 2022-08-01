package dev.pablolec.ezbookmark.ui.bookmark_list;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import dev.pablolec.ezbookmark.model.BookmarkList;
import dev.pablolec.ezbookmark.repository.LocalDatabase;

public class BookmarkListViewModel extends ViewModel {
    private final LocalDatabase localDatabase;
    List<BookmarkList> bookmarkLists;

    public BookmarkListViewModel() {
        localDatabase = LocalDatabase.getDatabase();
        init();
    }

    public void init() {
        populateList();
    }

    public void populateList() {
        bookmarkLists = new ArrayList<>();
        bookmarkLists.addAll(localDatabase.bookmarkListDao().getAll());
    }
}