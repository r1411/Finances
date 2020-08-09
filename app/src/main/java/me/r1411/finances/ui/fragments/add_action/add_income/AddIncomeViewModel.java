package me.r1411.finances.ui.fragments.add_action.add_income;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import me.r1411.finances.FinancesApp;
import me.r1411.finances.R;
import me.r1411.finances.objects.CategoryIncome;

public class AddIncomeViewModel extends ViewModel {
    private MutableLiveData<List<String>> spinnerElements;
    private MutableLiveData<String> dateString;
    private MutableLiveData<String> timeString;
    private MutableLiveData<Integer> selectedYear;
    private MutableLiveData<Integer> selectedMonth;
    private MutableLiveData<Integer> selectedDay;
    private MutableLiveData<Integer> selectedHour;
    private MutableLiveData<Integer> selectedMinute;

    public AddIncomeViewModel() {
        spinnerElements = new MutableLiveData<>();
        dateString = new MutableLiveData<>();
        timeString = new MutableLiveData<>();
        selectedYear = new MutableLiveData<>();
        selectedMonth = new MutableLiveData<>();
        selectedDay = new MutableLiveData<>();
        selectedHour = new MutableLiveData<>();
        selectedMinute = new MutableLiveData<>();

        List<String> categories = new ArrayList<>();
        spinnerElements.setValue(categories);

        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            List<CategoryIncome> categoryIncomeList = FinancesApp.getInstance().getDatabase().categoryIncomeDao().getAll();
            for(CategoryIncome category : categoryIncomeList) {
                categories.add(category.getName());
            }
            spinnerElements.postValue(categories);
        });

        dateString.setValue(FinancesApp.getContext().getString(R.string.today));

        final Calendar c = Calendar.getInstance();
        int currentYear = c.get(Calendar.YEAR);
        int currentMonth = c.get(Calendar.MONTH);
        int currentDay = c.get(Calendar.DAY_OF_MONTH);
        int currentHour = c.get(Calendar.HOUR_OF_DAY);
        int currentMinute = c.get(Calendar.MINUTE);

        selectedYear.setValue(currentYear);
        selectedMonth.setValue(currentMonth);
        selectedDay.setValue(currentDay);
        selectedHour.setValue(currentHour);
        selectedMinute.setValue(currentMinute);

        String currentTimeString = new SimpleDateFormat("HH:mm").format(new Date());
        timeString.setValue(currentTimeString);
        Log.d("ADDINC", "AddIncomeViewModel init done");
    }

    public LiveData<List<String>> getSpinnerElements() {
        return spinnerElements;
    }

    public MutableLiveData<String> getDateString() {
        return dateString;
    }

    public void setDateString(String str) {
        this.dateString.setValue(str);
    }

    public MutableLiveData<String> getTimeString() {
        return timeString;
    }

    public void setTimeString(String str) {
        this.timeString.setValue(str);
    }

    public MutableLiveData<Integer> getSelectedYear() {
        return selectedYear;
    }

    public void setSelectedYear(int year) {
        this.selectedYear.setValue(year);
    }

    public MutableLiveData<Integer> getSelectedMonth() {
        return selectedMonth;
    }

    public void setSelectedMonth(int month) {
        this.selectedMonth.setValue(month);
    }

    public MutableLiveData<Integer> getSelectedDay() {
        return selectedDay;
    }

    public void setSelectedDay(int day) {
        this.selectedDay.setValue(day);
    }

    public void setSelectedHour(int hour) {
        this.selectedHour.setValue(hour);
    }

    public void setSelectedMinute(int minute) {
        this.selectedMinute.setValue(minute);
    }

    public MutableLiveData<Integer> getSelectedHour() {
        return this.selectedHour;
    }

    public MutableLiveData<Integer> getSelectedMinute() {
        return this.selectedMinute;
    }
}
