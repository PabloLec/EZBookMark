package dev.pablolec.ezbookmark.ui.menu;

import android.content.DialogInterface;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.MenuProvider;

import dev.pablolec.ezbookmark.R;
import dev.pablolec.ezbookmark.model.Bookmark;
import dev.pablolec.ezbookmark.repository.LocalDatabase;
import dev.pablolec.ezbookmark.ui.popups.BookmarkPopUp;

public class BookmarkAltMenu implements MenuProvider {
    private View view;
    private Bookmark selectedBookmark;
    private AlertDialog deleteDialog;

    public void setView(View view) {
        this.view = view;
    }

    public Bookmark getSelectedBookmark() {
        return selectedBookmark;
    }

    public void setSelectedBookmark(Bookmark selectedBookmark) {
        this.selectedBookmark = selectedBookmark;
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
        switch (menuItem.getItemId()) {
            case R.id.menu_bookmark_alt_edit:
                BookmarkPopUp popup = new BookmarkPopUp();
                popup.showPopupWindow(view, selectedBookmark);
                return true;
            case R.id.menu_bookmark_alt_delete:
                deleteDialog.show();
                return true;
            default:
                return false;
        }
    }
}
