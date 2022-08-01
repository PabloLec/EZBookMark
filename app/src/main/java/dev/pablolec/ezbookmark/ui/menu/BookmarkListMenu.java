package dev.pablolec.ezbookmark.ui.menu;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;

import dev.pablolec.ezbookmark.R;
import dev.pablolec.ezbookmark.ui.popups.BookmarkListPopUp;

public class BookmarkListMenu implements MenuProvider {
    private View view;

    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu_bookmark_list, menu);
    }

    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getTitle() == null) return false;

        String title = menuItem.getTitle().toString();
        if (title.equals(view.getContext().getResources().getString(R.string.title_add_bookmark_list))) {
            BookmarkListPopUp popup = new BookmarkListPopUp();
            popup.showPopupWindow(view);
            return true;
        } else if (title.equals(view.getContext().getResources().getString(R.string.title_sort_bookmark_list))) {
            Toast.makeText(view.getContext(), "BOOKMARK LIST SORT", Toast.LENGTH_LONG).show();
            return true;
        } else if (title.equals(view.getContext().getResources().getString(R.string.title_search_bookmark_list))) {
            Toast.makeText(view.getContext(), "BOOKMARK LIST SEARCH", Toast.LENGTH_LONG).show();
            return true;
        } else {
            return false;
        }
    }
}
