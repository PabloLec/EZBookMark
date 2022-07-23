package dev.pablolec.ezboookmark.dao;

import com.google.gson.GsonBuilder;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import dev.pablolec.ezboookmark.App;
import dev.pablolec.ezboookmark.model.Bookmark;

public class BookmarkDao {
    private final String testDataBaseFile = "database.json";

    public List<Bookmark> loadAll() throws Exception {
        InputStream is = App.getContext().getAssets().open(testDataBaseFile);
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        String jsonString = new String(buffer, StandardCharsets.UTF_8);

        Bookmark[] bookMarkArray = new GsonBuilder().create().fromJson(jsonString, Bookmark[].class);
        return Arrays.asList(bookMarkArray);
    }
}
