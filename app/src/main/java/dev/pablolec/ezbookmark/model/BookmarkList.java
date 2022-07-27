package dev.pablolec.ezbookmark.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class BookmarkList {
    @PrimaryKey(autoGenerate = true)
    public int bookmarkListId;

    @ColumnInfo(name = "name")
    private String name;

    public BookmarkList(int uid, String name, String url) {
        this.bookmarkListId = uid;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
