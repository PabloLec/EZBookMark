package dev.pablolec.ezbookmark.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import dev.pablolec.ezbookmark.model.Bookmark;
import dev.pablolec.ezbookmark.model.BookmarkListCrossRef;

@Dao
public interface BookmarkListCrossRefDao {
    @Query("SELECT bookmarkId FROM BookmarkListCrossRef WHERE bookmarkListId = :bookmarkListId")
    List<Integer> getBookmarksByList(int bookmarkListId);

    @Query("SELECT * FROM BookmarkListCrossRef")
    List<Bookmark> getAll();

    @Insert
    void insertAll(BookmarkListCrossRef... bookmarkListCrossRefs);

    @Insert
    void insert(BookmarkListCrossRef bookmarkListCrossRef);

    @Delete
    void delete(BookmarkListCrossRef bookmarkListCrossRef);
}
