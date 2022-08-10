package dev.pablolec.ezbookmark.ui.menu;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.MenuProvider;

import dev.pablolec.ezbookmark.R;
import dev.pablolec.ezbookmark.model.Bookmark;
import dev.pablolec.ezbookmark.model.BookmarkList;
import dev.pablolec.ezbookmark.ui.popups.BookmarkPopUp;

public class BookmarkAltMenu implements MenuProvider {
    private View view;
    private Bookmark selected;
    private AlertDialog deleteDialog;
    private BookmarkList list;

    public BookmarkAltMenu(BookmarkList list) {
        this.list = list;
    }

    public void setView(View view) {
        this.view = view;
    }

    public Bookmark getSelected() {
        return selected;
    }

    public void setSelected(Bookmark selected) {
        this.selected = selected;
    }

    public void setDeleteDialog(AlertDialog deleteDialog) {
        this.deleteDialog = deleteDialog;
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu_bookmark_alt, menu);
    }

    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getTitle() == null) return false;

        String title = menuItem.getTitle().toString();
        if (title.equals(view.getContext().getResources().getString(R.string.title_edit_bookmark))) {
            BookmarkPopUp popup = new BookmarkPopUp(list);
            popup.showPopupWindow(view, selected);
            return true;
        } else if (title.equals(view.getContext().getResources().getString(R.string.title_delete_bookmark))) {
            deleteDialog.show();
            return true;
        } else {
            return false;
        }
    }
}
