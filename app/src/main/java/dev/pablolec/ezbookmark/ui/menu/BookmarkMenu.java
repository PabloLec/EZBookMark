package dev.pablolec.ezbookmark.ui.menu;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;

import dev.pablolec.ezbookmark.R;
import dev.pablolec.ezbookmark.ui.popups.BookmarkPopUp;

public class BookmarkMenu implements MenuProvider {
    private View view;

    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu_bookmark, menu);
    }

    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_bookmark_add:
                BookmarkPopUp popup = new BookmarkPopUp();
                popup.showPopupWindow(view);
                return true;
            case R.id.menu_bookmark_sort:
                Toast.makeText(view.getContext(), "BOOKMARK SORT", Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_bookmark_search:
                Toast.makeText(view.getContext(), "BOOKMARK SEARCH", Toast.LENGTH_LONG).show();
                return true;
            default:
                return false;
        }
    }
}
