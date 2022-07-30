package dev.pablolec.ezbookmark.repository;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dev.pablolec.ezbookmark.App;
import dev.pablolec.ezbookmark.dao.BookmarkDao;
import dev.pablolec.ezbookmark.dao.BookmarkListCrossRefDao;
import dev.pablolec.ezbookmark.dao.BookmarkListDao;
import dev.pablolec.ezbookmark.model.Bookmark;
import dev.pablolec.ezbookmark.model.BookmarkList;
import dev.pablolec.ezbookmark.model.BookmarkListCrossRef;

@Database(entities = {Bookmark.class, BookmarkList.class, BookmarkListCrossRef.class}, version = 1)
public abstract class LocalDatabase extends RoomDatabase {
        private static volatile LocalDatabase INSTANCE;
        private static final int NUMBER_OF_THREADS = 4;
        static final ExecutorService databaseWriteExecutor =
                Executors.newFixedThreadPool(NUMBER_OF_THREADS);

        static LocalDatabase getDatabase(final Context context) {
            if (INSTANCE == null) {
                synchronized (LocalDatabase.class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                        LocalDatabase.class, App.LOCAL_DATABASE)
                                .build();
                    }
                }
            }
            return INSTANCE;
        }

    public abstract BookmarkDao bookmarkDao();
    public abstract BookmarkListDao bookmarkListDao();
    public abstract BookmarkListCrossRefDao bookmarkListCrossRefDao();
}
