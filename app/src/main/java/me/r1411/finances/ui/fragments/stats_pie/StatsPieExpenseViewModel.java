package me.r1411.finances.ui.fragments.stats_pie;

import me.r1411.finances.objects.ActionType;

public class StatsPieExpenseViewModel extends StatsPieViewModel {

    @Override
    public ActionType getActionType() {
        return ActionType.EXPENSE;
    }
}