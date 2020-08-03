package me.r1411.finances.ui.fragments.add_action;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import me.r1411.finances.ui.fragments.add_action.add_expense.AddExpenseFragment;
import me.r1411.finances.ui.fragments.add_action.add_income.AddIncomeFragment;

public class CollectionAdapter extends FragmentStateAdapter {

    public CollectionAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 0) {
            return new AddExpenseFragment();
        } else {
            return  new AddIncomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
