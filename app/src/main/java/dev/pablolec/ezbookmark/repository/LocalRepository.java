package dev.pablolec.ezbookmark.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import dev.pablolec.ezbookmark.dao.BookmarkDao;
import dev.pablolec.ezbookmark.model.Bookmark;

public class LocalRepository {
    private BookmarkDao mBookmarkDao;
    private LiveData<List<Bookmark>> mAllBookmarks;

    public LocalRepository(Application application) {
        LocalDatabase db = LocalDatabase.getDatabase(application);
        mBookmarkDao = db.bookmarkDao();
        mAllBookmarks = mBookmarkDao.getAll();
    }
    public LiveData<List<Bookmark>> getAllBookmarks() {
        return mAllBookmarks;
    }

    public void insert(Bookmark word) {
        LocalDatabase.databaseWriteExecutor.execute(() -> {
            mBookmarkDao.insert(word);
        });
    }
}
