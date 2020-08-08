package me.r1411.finances.ui.fragments.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import me.r1411.finances.FinancesApp;
import me.r1411.finances.objects.Expense;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<Expense>> latestExpenses;

    public HomeViewModel() {
        latestExpenses = new MutableLiveData<>();

        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            List<Expense> testExpenses = FinancesApp.getInstance().getDatabase().expenseDao().getLatest();
            latestExpenses.postValue(testExpenses);
        });

    }


    public MutableLiveData<List<Expense>> getLatestExpenses() {
        return latestExpenses;
    }
}