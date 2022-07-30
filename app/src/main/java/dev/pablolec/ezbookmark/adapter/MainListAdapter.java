package dev.pablolec.ezbookmark.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.pablolec.ezbookmark.R;
import dev.pablolec.ezbookmark.model.Bookmark;

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ViewHolder> {

    private List<Bookmark> bookmarkList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordItemView;

        private ViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.textView);
        }

        public void bind(String text) {
            wordItemView.setText(text);
        }

        static ViewHolder create(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.content_main, parent, false);
            return new ViewHolder(view);
        }

    }

    public MainListAdapter() {}
    public MainListAdapter(List<Bookmark> bookmarkList) {
        this.bookmarkList = bookmarkList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.bookmark_cell, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getTextView().setText(bookmarkList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return bookmarkList.size();
    }

    public void setData(List<Bookmark> bookmarkList) {
        this.bookmarkList = bookmarkList;
        notifyDataSetChanged();
    }

}
