package me.r1411.finances.ui.fragments.add_action.add_expense;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class AddExpenseViewModel extends ViewModel {
    private MutableLiveData<ArrayList<String>> spinnerElements;

    public AddExpenseViewModel() {
        spinnerElements = new MutableLiveData<>();
        ArrayList<String> debugElements = new ArrayList<>();
        debugElements.add("element 1");
        debugElements.add("element 2");
        debugElements.add("element 3");
        debugElements.add("element 4");
        spinnerElements.setValue(debugElements);
        Log.d("ADDEXP", "AddExpenseViewModel init done");
    }

    public LiveData<ArrayList<String>> getSpinnerElements() {
        return spinnerElements;
    }

}
