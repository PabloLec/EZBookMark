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
import dev.pablolec.ezbookmark.model.BookmarkList;

public class BookmarkListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<BookmarkList> bookmarkLists;

    public BookmarkListAdapter() {
        this.bookmarkLists = new ArrayList<>();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark_list_cell, parent, false);
        return new RecyclerViewViewHolder(rootView);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BookmarkList bookmarkList = bookmarkLists.get(position);
        RecyclerViewViewHolder viewHolder = (RecyclerViewViewHolder) holder;
        viewHolder.title.setText(bookmarkList.getName());
    }

    @Override
    public int getItemCount() {
        return bookmarkLists.size();
    }

    public void updateBookmarkList(final List<BookmarkList> bookmarkLists) {
        this.bookmarkLists.clear();
        this.bookmarkLists = (ArrayList<BookmarkList>) bookmarkLists;
        notifyDataSetChanged();
    }

    static class RecyclerViewViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public RecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.bookmark_list_name_text_view);
        }
    }
}
