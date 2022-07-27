package dev.pablolec.ezbookmark.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import dev.pablolec.ezbookmark.model.Bookmark;

@Dao
public interface BookmarkDao {
    @Query("SELECT * FROM bookmark")
    List<Bookmark> getAll();

    @Query("SELECT * FROM bookmark WHERE bookmarkId IN (:ids)")
    List<Bookmark> getById(List<Integer> ids);

    @Insert
    void insertAll(Bookmark... bookmarks);

    @Insert
    void insert(Bookmark bookmark);

    @Delete
    void delete(Bookmark bookmark);
}
