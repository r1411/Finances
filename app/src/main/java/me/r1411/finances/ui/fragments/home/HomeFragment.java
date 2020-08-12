package me.r1411.finances.ui.fragments.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import java.util.List;

import me.r1411.finances.R;
import me.r1411.finances.objects.ActionType;
import me.r1411.finances.objects.Expense;
import me.r1411.finances.objects.Income;
import me.r1411.finances.ui.elements.ExpenseRowView;
import me.r1411.finances.ui.elements.IncomeRowView;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel.getLatestExpenses().observe(getViewLifecycleOwner(), expenses -> {
            LinearLayout expensesLinearLayout = root.findViewById(R.id.expenses_list_layout);
            expensesLinearLayout.removeAllViews();
            for(Expense expense : expenses) {
                ExpenseRowView rowView = new ExpenseRowView(root.getContext(), expense.getId(), expense.getCategory(), expense.getSum(), expense.getTs());
                expensesLinearLayout.addView(rowView);
            }
        });
        homeViewModel.getLatestIncomes().observe(getViewLifecycleOwner(), incomes -> {
            LinearLayout incomesLinearLayout = root.findViewById(R.id.incomes_list_layout);
            incomesLinearLayout.removeAllViews();
            for(Income income : incomes) {
                IncomeRowView rowView = new IncomeRowView(root.getContext(), income.getId(), income.getCategory(), income.getSum(), income.getTs());
                incomesLinearLayout.addView(rowView);
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);
        root.findViewById(R.id.show_recent_expenses_btn).setOnClickListener(view -> {
            Navigation.findNavController(root).navigate(R.id.action_navigation_home_to_expenseListFragment);
        });
        root.findViewById(R.id.show_recent_incomes_btn).setOnClickListener(view -> {
            Navigation.findNavController(root).navigate(R.id.action_navigation_home_to_incomeListFragment);
        });
    }
}