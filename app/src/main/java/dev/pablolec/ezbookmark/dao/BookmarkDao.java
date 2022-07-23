package dev.pablolec.ezbookmark.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.google.gson.GsonBuilder;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import dev.pablolec.ezbookmark.App;
import dev.pablolec.ezbookmark.model.Bookmark;

@Dao
public interface BookmarkDao {
    @Query("SELECT * FROM bookmark")
    List<Bookmark> getAll();

    @Insert
    void insertAll(Bookmark... bookmarks);

    @Insert
    void insert(Bookmark bookmark);

    @Delete
    void delete(Bookmark bookmark);

}
