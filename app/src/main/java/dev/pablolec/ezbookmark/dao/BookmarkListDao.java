package dev.pablolec.ezbookmark.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import dev.pablolec.ezbookmark.model.BookmarkList;

@Dao
public interface BookmarkListDao {
    @Query("SELECT * FROM bookmarklist")
    List<BookmarkList> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(BookmarkList... bookmarkLists);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(BookmarkList bookmarkList);

    @Delete
    void delete(BookmarkList bookmarkList);
}
