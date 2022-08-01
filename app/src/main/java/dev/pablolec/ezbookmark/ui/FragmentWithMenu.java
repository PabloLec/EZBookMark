package dev.pablolec.ezbookmark.ui;

import android.content.DialogInterface;
import android.graphics.Color;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class FragmentWithMenu extends Fragment {
    protected MenuProvider menu;
    protected MenuProvider menuAlt;
    protected boolean isMenuAltLoaded = false;
    protected List<View> selectedViews = new ArrayList<>();
    private int selectedColor = Color.parseColor("#567845");
    private int defaultColor = Color.parseColor("#ffffff");

    @Override
    public void onResume() {
        super.onResume();
        loadMenu();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unloadAllMenus();
    }

    protected void loadMenu() {
        requireActivity().removeMenuProvider(menuAlt);
        requireActivity().addMenuProvider(menu);
    }

    protected void loadAltMenu() {
        isMenuAltLoaded = true;
        unloadAllMenus();
        requireActivity().addMenuProvider(menuAlt);
    }

    protected void unloadAltMenu() {
        isMenuAltLoaded = false;
        loadMenu();
    }

    protected void unloadAllMenus() {
        requireActivity().removeMenuProvider(menu);
        requireActivity().removeMenuProvider(menuAlt);
    }

    protected void delete() {
    }

    protected AlertDialog getDeleteDialog() {
        return new AlertDialog.Builder(getContext())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("")
                .setMessage("Are you sure you want to delete this bookmark?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete();
                        unloadAltMenu();
                    }
                })
                .setNegativeButton("No", null).create();
    }

    protected void addSelected(View view) {
        view.setBackgroundColor(selectedColor);
        selectedViews.add(view);
    }

    protected void removeSelected(View view) {
        view.setBackgroundColor(defaultColor);
        selectedViews.remove(view);
    }

    protected void unselectAll() {
        for (View view : selectedViews) {
            view.setBackgroundColor(defaultColor);
        }
        selectedViews.clear();
    }

}
