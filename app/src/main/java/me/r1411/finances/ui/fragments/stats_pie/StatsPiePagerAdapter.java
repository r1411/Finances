package me.r1411.finances.ui.fragments.stats_pie;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import me.r1411.finances.R;
import me.r1411.finances.objects.ActionType;

public class StatsPiePagerAdapter extends FragmentStateAdapter {

    private StatsPieFragment expensesPieFragment;
    private StatsPieFragment incomesPieFragment;

    public StatsPiePagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
        this.expensesPieFragment = StatsPieFragment.newInstance(ActionType.EXPENSE);
        this.incomesPieFragment = StatsPieFragment.newInstance(ActionType.INCOME);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return this.expensesPieFragment;
        } else {
            return this.incomesPieFragment;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
