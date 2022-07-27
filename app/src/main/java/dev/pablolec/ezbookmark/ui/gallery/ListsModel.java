package dev.pablolec.ezbookmark.ui.lists;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ListsModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ListsModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is lists fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}