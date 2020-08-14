package me.r1411.finances.ui.fragments.stats_pie;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import me.r1411.finances.objects.ActionType;

public class StatsPieViewModelFactory implements ViewModelProvider.Factory {
    private ActionType actionType;

    public StatsPieViewModelFactory(ActionType actionType) {
        this.actionType = actionType;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new StatsPieViewModel(this.actionType);
    }
}
