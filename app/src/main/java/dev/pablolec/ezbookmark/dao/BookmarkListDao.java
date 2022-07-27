package dev.pablolec.ezbookmark.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import dev.pablolec.ezbookmark.model.Bookmark;
import dev.pablolec.ezbookmark.model.BookmarkList;

@Dao
public interface BookmarkListDao {
    @Query("SELECT * FROM bookmarkList")
    List<Bookmark> getAll();

    @Insert
    void insertAll(BookmarkList... bookmarkLists);

    @Insert
    void insert(BookmarkList bookmarkList);

    @Delete
    void delete(BookmarkList bookmarkList);
}
