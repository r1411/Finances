package me.r1411.finances.ui.fragments.stats_pie;

import me.r1411.finances.objects.ActionType;

public class StatsPieIncomeViewModel extends StatsPieViewModel {

    @Override
    public ActionType getActionType() {
        return ActionType.INCOME;
    }
}