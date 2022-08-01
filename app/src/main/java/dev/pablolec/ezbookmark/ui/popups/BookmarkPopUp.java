package dev.pablolec.ezbookmark.ui.popups;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import dev.pablolec.ezbookmark.R;
import dev.pablolec.ezbookmark.model.Bookmark;
import dev.pablolec.ezbookmark.repository.LocalDatabase;

public class BookmarkPopUp {
    public static void dimBehind(PopupWindow popupWindow) {
        View container = popupWindow.getContentView().getRootView();
        Context context = popupWindow.getContentView().getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.6f;
        wm.updateViewLayout(container, p);
    }

    public void showPopupWindow(final View view) {
        showPopupWindow(view, new Bookmark("", ""));
    }

    public void showPopupWindow(final View view, Bookmark bookmark) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.bookmark_popup, null);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        dimBehind(popupWindow);

        EditText name = popupView.findViewById(R.id.bookmark_name_edit_text);
        name.setText(bookmark.getName());
        EditText url = popupView.findViewById(R.id.bookmark_url_edit_text);
        url.setText(bookmark.getUrl());
        Button confirmButton = popupView.findViewById(R.id.bookmark_confirm_button);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookmark.setName(name.getText().toString());
                bookmark.setUrl(url.getText().toString());
                LocalDatabase localDatabase = LocalDatabase.getDatabase();
                localDatabase.bookmarkDao().insert(bookmark);
                Toast.makeText(view.getContext(), "Bookmark added", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        });

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //Close the window when clicked
                popupWindow.dismiss();
                return true;
            }
        });
    }

}