package dev.pablolec.ezbookmark.ui.menu;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.MenuProvider;

import dev.pablolec.ezbookmark.R;
import dev.pablolec.ezbookmark.model.BookmarkList;
import dev.pablolec.ezbookmark.ui.popups.BookmarkListPopUp;

public class BookmarkListAltMenu implements MenuProvider {
    private View view;
    private BookmarkList selected;
    private AlertDialog deleteDialog;

    public void setView(View view) {
        this.view = view;
    }

    public BookmarkList getSelected() {
        return selected;
    }

    public void setSelected(BookmarkList selected) {
        this.selected = selected;
    }

    public void setDeleteDialog(AlertDialog deleteDialog) {
        this.deleteDialog = deleteDialog;
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu_bookmark_list_alt, menu);
    }

    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getTitle() == null) return false;

        String title = menuItem.getTitle().toString();
        if (title.equals(view.getContext().getResources().getString(R.string.title_edit_bookmark_list))) {
            BookmarkListPopUp popup = new BookmarkListPopUp();
            popup.showPopupWindow(view, selected);
            return true;
        } else if (title.equals(view.getContext().getResources().getString(R.string.title_delete_bookmark_list))) {
            deleteDialog.show();
            return true;
        } else {
            return false;
        }
    }
}
