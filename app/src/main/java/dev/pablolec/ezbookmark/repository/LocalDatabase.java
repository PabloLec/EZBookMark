package dev.pablolec.ezbookmark.repository;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import dev.pablolec.ezbookmark.App;
import dev.pablolec.ezbookmark.dao.BookmarkDao;
import dev.pablolec.ezbookmark.dao.BookmarkListCrossRefDao;
import dev.pablolec.ezbookmark.dao.BookmarkListDao;
import dev.pablolec.ezbookmark.model.Bookmark;
import dev.pablolec.ezbookmark.model.BookmarkList;
import dev.pablolec.ezbookmark.model.BookmarkListCrossRef;

@Database(entities = {Bookmark.class, BookmarkList.class, BookmarkListCrossRef.class}, version = 1)
public abstract class LocalDatabase extends RoomDatabase {
    private static volatile LocalDatabase instance;

    public static synchronized LocalDatabase getDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            LocalDatabase.class, App.LOCAL_DATABASE)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract BookmarkDao bookmarkDao();
    public abstract BookmarkListDao bookmarkListDao();
    public abstract BookmarkListCrossRefDao bookmarkListCrossRefDao();
}
