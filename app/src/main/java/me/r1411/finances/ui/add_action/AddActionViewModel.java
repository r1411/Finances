package me.r1411.finances.ui.add_action;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddActionViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AddActionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is addAction fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}