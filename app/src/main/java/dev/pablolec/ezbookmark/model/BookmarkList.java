package dev.pablolec.ezbookmark.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

import dev.pablolec.ezbookmark.repository.LocalDatabase;

@Entity
public class BookmarkList {
    @PrimaryKey(autoGenerate = true)
    public int bookmarkListId;

    @ColumnInfo(name = "name")
    private String name;

    public BookmarkList(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Bookmark> getBookmarks(LocalDatabase database) {
        List<Integer> bookmarkIds = database.bookmarkListCrossRefDao().getBookmarksByList(bookmarkListId);
        return database.bookmarkDao().getById(bookmarkIds);
    }


}
