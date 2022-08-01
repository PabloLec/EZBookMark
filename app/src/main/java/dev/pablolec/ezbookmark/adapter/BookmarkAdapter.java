package dev.pablolec.ezbookmark.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dev.pablolec.ezbookmark.R;
import dev.pablolec.ezbookmark.model.Bookmark;

public class BookmarkAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Bookmark> bookmarks;

    public BookmarkAdapter() {
        this.bookmarks = new ArrayList<>();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark_cell, parent, false);
        return new RecyclerViewViewHolder(rootView);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Bookmark bookmark = bookmarks.get(position);
        RecyclerViewViewHolder viewHolder = (RecyclerViewViewHolder) holder;
        viewHolder.title.setText(bookmark.getName());
    }

    @Override
    public int getItemCount() {
        return bookmarks.size();
    }

    public Bookmark getBookmark(int index) {
        return bookmarks.get(index);
    }

    public void updateBookmarkList(final List<Bookmark> bookmarks) {
        this.bookmarks.clear();
        this.bookmarks = (ArrayList<Bookmark>) bookmarks;
        notifyDataSetChanged();
    }

    static class RecyclerViewViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public RecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.bookmark_name_text_view);
        }
    }
}
