package me.r1411.finances.ui.add_expense;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddExpenseViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AddExpenseViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is addExpense fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}