package me.r1411.finances.ui.fragments.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import me.r1411.finances.R;
import me.r1411.finances.objects.ActionType;
import me.r1411.finances.objects.Expense;
import me.r1411.finances.objects.Income;
import me.r1411.finances.ui.elements.ActionRowView;
import me.r1411.finances.ui.fragments.stats_pie.StatsPiePagerAdapter;
import me.relex.circleindicator.CircleIndicator3;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ViewPager2 statsPieViewPager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel.getLatestExpenses().observe(requireActivity(), expenses -> {
            LinearLayout expensesLinearLayout = root.findViewById(R.id.expenses_list_layout);
            expensesLinearLayout.removeAllViews();
            if(expenses.size() > 0) {
                for(Expense expense : expenses) {
                    ActionRowView rowView = new ActionRowView(root.getContext(), expense.getId(), expense.getCategory(), expense.getSum(), expense.getTs(), expense.getComment(), ActionType.EXPENSE);
                    expensesLinearLayout.addView(rowView);
                }
            } else {
                inflater.inflate(R.layout.layout_no_data_row, expensesLinearLayout);
            }
        });
        homeViewModel.getLatestIncomes().observe(requireActivity(), incomes -> {
            LinearLayout incomesLinearLayout = root.findViewById(R.id.incomes_list_layout);
            incomesLinearLayout.removeAllViews();
            if(incomes.size() > 0) {
                for(Income income : incomes) {
                    ActionRowView rowView = new ActionRowView(root.getContext(), income.getId(), income.getCategory(), income.getSum(), income.getTs(), income.getComment(), ActionType.INCOME);
                    incomesLinearLayout.addView(rowView);
                }
            } else {
                inflater.inflate(R.layout.layout_no_data_row, incomesLinearLayout);
            }
        });
        this.statsPieViewPager = root.findViewById(R.id.home_pager);
        StatsPiePagerAdapter adapter = new StatsPiePagerAdapter(this);
        this.statsPieViewPager.setAdapter(adapter);
        CircleIndicator3 indicator = root.findViewById(R.id.home_dots_indicator);
        indicator.setViewPager(this.statsPieViewPager);
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

    public HomeViewModel getHomeViewModel() {
        return this.homeViewModel;
    }

    public ViewPager2 getStatsPieViewPager() {
        return this.statsPieViewPager;
    }
}