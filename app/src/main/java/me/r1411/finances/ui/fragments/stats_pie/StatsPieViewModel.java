package me.r1411.finances.ui.fragments.stats_pie;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import me.r1411.finances.FinancesApp;
import me.r1411.finances.R;
import me.r1411.finances.objects.Action;
import me.r1411.finances.objects.ActionType;
import me.r1411.finances.objects.Expense;
import me.r1411.finances.objects.Income;

public class StatsPieViewModel extends ViewModel {
    private MutableLiveData<List<PieEntry>> pieEntries;
    private MutableLiveData<Double> pieCenterValue;

    public StatsPieViewModel(ActionType actionType) {
        this.pieEntries = new MutableLiveData<>();
        this.pieCenterValue = new MutableLiveData<>();

        List<PieEntry> entriesList = new ArrayList<>();

        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.clear(Calendar.MINUTE);
            calendar.clear(Calendar.SECOND);
            calendar.clear(Calendar.MILLISECOND);
            calendar.set(Calendar.DAY_OF_MONTH, 1);

            List<Action> actions = new ArrayList<>();
            Map<String, PieEntry> pieEntryMap = new HashMap<>();
            Log.d("SPVM", "Oldest date: " + (calendar.getTime().getTime() / 1000));
            double centerValue = 0;
            if(actionType == ActionType.EXPENSE) {
                List<Expense> expenses = FinancesApp.getInstance().getDatabase().expenseDao().getFromDate(calendar.getTime().getTime() / 1000);
                actions.addAll(expenses);
            } else {
                List<Income> incomes = FinancesApp.getInstance().getDatabase().incomeDao().getFromDate(calendar.getTime().getTime() / 1000);
                actions.addAll(incomes);
            }
            for(Action action : actions) {
                centerValue += action.getSum();
                if(pieEntryMap.containsKey(action.getCategory())) {
                    pieEntryMap.get(action.getCategory()).setY((float) (pieEntryMap.get(action.getCategory()).getY() + action.getSum()));
                } else {
                    pieEntryMap.put(action.getCategory(), new PieEntry((float) action.getSum(), action.getCategory()));
                }
            }
            entriesList.addAll(pieEntryMap.values());
            this.pieEntries.postValue(entriesList);
            this.pieCenterValue.postValue(centerValue);
        });

    }

    public MutableLiveData<List<PieEntry>> getPieEntries() {
        return pieEntries;
    }

    public MutableLiveData<Double> getPieCenterValue() {
        return pieCenterValue;
    }
}