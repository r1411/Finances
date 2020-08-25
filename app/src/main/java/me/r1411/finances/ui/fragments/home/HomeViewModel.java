package me.r1411.finances.ui.fragments.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.concurrent.Executors;

import me.r1411.finances.FinancesApp;
import me.r1411.finances.objects.Expense;
import me.r1411.finances.objects.Income;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<Expense>> latestExpenses;
    private MutableLiveData<List<Income>> latestIncomes;

    public HomeViewModel() {
        latestExpenses = new MutableLiveData<>();
        latestIncomes = new MutableLiveData<>();

        updateLatestActions();
    }

    public void updateLatestActions() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Expense> expenses = FinancesApp.getInstance().getDatabase().expenseDao().getLatest();
            this.latestExpenses.postValue(expenses);
        });

        Executors.newSingleThreadExecutor().execute(() -> {
            List<Income> incomes = FinancesApp.getInstance().getDatabase().incomeDao().getLatest();
            this.latestIncomes.postValue(incomes);
        });
    }

    public MutableLiveData<List<Expense>> getLatestExpenses() {
        return latestExpenses;
    }

    public MutableLiveData<List<Income>> getLatestIncomes() {
        return latestIncomes;
    }
}