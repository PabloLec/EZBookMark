package dev.pablolec.ezbookmark.ui.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import dev.pablolec.ezbookmark.model.Bookmark;
import dev.pablolec.ezbookmark.repository.LocalRepository;

public class HomeModel extends AndroidViewModel {

    private LocalRepository mRepository;

    private final LiveData<List<Bookmark>> mAllBookmarks;

    public HomeModel(Application application) {
        super(application);
        mRepository = new LocalRepository(application);
        mAllBookmarks = mRepository.getAllBookmarks();
    }

    LiveData<List<Bookmark>> getAllBookmarks() {
        return mAllBookmarks;
    }

    public void insert(Bookmark Bookmark) {
        mRepository.insert(Bookmark);
    }
}