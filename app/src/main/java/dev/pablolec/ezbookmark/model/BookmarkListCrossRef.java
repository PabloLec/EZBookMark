package dev.pablolec.ezbookmark.model;

import androidx.room.Entity;

@Entity(primaryKeys = {"bookmarkId", "bookmarkListId"})
public class BookmarkListCrossRef {
    public long bookmarkId;
    public long bookmarkListId;

    public BookmarkListCrossRef(long bookmarkId, long bookmarkListId) {
        this.bookmarkId = bookmarkId;
        this.bookmarkListId = bookmarkListId;
    }
}
