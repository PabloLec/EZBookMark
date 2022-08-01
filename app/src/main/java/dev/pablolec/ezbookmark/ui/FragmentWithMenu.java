package dev.pablolec.ezbookmark.ui;

import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;

public class FragmentWithMenu extends Fragment {
    protected MenuProvider menu;
    protected MenuProvider menuAlt;
    protected boolean isMenuAltLoaded = false;

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

}
