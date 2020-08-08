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

import java.util.List;

import me.r1411.finances.R;
import me.r1411.finances.objects.Expense;
import me.r1411.finances.ui.elements.ExpenseRowView;

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
        return root;
    }
}