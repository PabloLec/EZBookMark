package dev.pablolec.ezbookmark.model;

import androidx.room.Entity;

@Entity(primaryKeys = {"playlistId", "songId"})
public class BookmarkListCrossRef {
    public long bookmarkId;
    public long bookmarkListId;
}
