package dev.pablolec.ezbookmark.ui.bookmark;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import dev.pablolec.ezbookmark.model.Bookmark;
import dev.pablolec.ezbookmark.repository.LocalDatabase;

public class BookmarkViewModel extends ViewModel {
    private final LocalDatabase localDatabase;
    List<Bookmark> bookmarkArrayList;

    public BookmarkViewModel() {
        localDatabase = LocalDatabase.getDatabase();
        init();
    }

    public void init() {
        populateList();
    }

    public void populateList() {
        bookmarkArrayList = new ArrayList<>();
        bookmarkArrayList.addAll(localDatabase.bookmarkDao().getAll());
    }
}