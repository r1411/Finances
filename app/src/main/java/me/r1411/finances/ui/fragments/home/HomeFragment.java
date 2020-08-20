package me.r1411.finances.ui.fragments.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import me.r1411.finances.R;
import me.r1411.finances.objects.Expense;
import me.r1411.finances.objects.Income;
import me.r1411.finances.ui.elements.ExpenseRowView;
import me.r1411.finances.ui.elements.IncomeRowView;
import me.r1411.finances.ui.fragments.stats_pie.StatsPiePagerAdapter;
import me.relex.circleindicator.CircleIndicator3;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel.getLatestExpenses().observe(getViewLifecycleOwner(), expenses -> {
            LinearLayout expensesLinearLayout = root.findViewById(R.id.expenses_list_layout);
            expensesLinearLayout.removeAllViews();
            if(expenses.size() > 0) {
                for(Expense expense : expenses) {
                    ExpenseRowView rowView = new ExpenseRowView(root.getContext(), expense.getId(), expense.getCategory(), expense.getSum(), expense.getTs());
                    expensesLinearLayout.addView(rowView);
                }
            } else {
                inflater.inflate(R.layout.layout_no_data_row, expensesLinearLayout);
            }
        });
        homeViewModel.getLatestIncomes().observe(getViewLifecycleOwner(), incomes -> {
            LinearLayout incomesLinearLayout = root.findViewById(R.id.incomes_list_layout);
            incomesLinearLayout.removeAllViews();
            if(incomes.size() > 0) {
                for(Income income : incomes) {
                    IncomeRowView rowView = new IncomeRowView(root.getContext(), income.getId(), income.getCategory(), income.getSum(), income.getTs());
                    incomesLinearLayout.addView(rowView);
                }
            } else {
                inflater.inflate(R.layout.layout_no_data_row, incomesLinearLayout);
            }
        });
        ViewPager2 viewPager = root.findViewById(R.id.home_pager);
        StatsPiePagerAdapter adapter = new StatsPiePagerAdapter(this);
        viewPager.setAdapter(adapter);
        CircleIndicator3 indicator = root.findViewById(R.id.home_dots_indicator);
        indicator.setViewPager(viewPager);
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