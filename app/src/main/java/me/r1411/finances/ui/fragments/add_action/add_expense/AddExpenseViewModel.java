package me.r1411.finances.ui.fragments.add_action.add_expense;

import android.content.res.Resources;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Calendar;

import me.r1411.finances.FinancesApp;
import me.r1411.finances.R;

public class AddExpenseViewModel extends ViewModel {
    private MutableLiveData<ArrayList<String>> spinnerElements;
    private MutableLiveData<String> dateString;
    private MutableLiveData<String> timeString;

    public AddExpenseViewModel() {
        spinnerElements = new MutableLiveData<>();
        dateString = new MutableLiveData<>();
        timeString = new MutableLiveData<>();

        ArrayList<String> debugElements = new ArrayList<>();
        debugElements.add("element 1");
        debugElements.add("element 2");
        debugElements.add("element 3");
        debugElements.add("element 4");
        spinnerElements.setValue(debugElements);
        dateString.setValue(FinancesApp.getContext().getString(R.string.today));
        Calendar currentTime = Calendar.getInstance();

        int currentHour = currentTime.get(Calendar.HOUR_OF_DAY);
        int currentMinute = currentTime.get(Calendar.MINUTE);

        timeString.setValue(currentHour + ":" + currentMinute);
        Log.d("ADDEXP", "AddExpenseViewModel init done");
    }

    public LiveData<ArrayList<String>> getSpinnerElements() {
        return spinnerElements;
    }

    public MutableLiveData<String> getDateString() {
        return dateString;
    }

    public MutableLiveData<String> getTimeString() {
        return timeString;
    }
}
