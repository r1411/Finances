package me.r1411.finances.ui.fragments.actions_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import me.r1411.finances.FinancesApp;
import me.r1411.finances.R;
import me.r1411.finances.objects.Action;
import me.r1411.finances.objects.ActionType;

public class ActionsListFragment extends Fragment {

    private ActionType actionType;
    private RecyclerView recyclerView;
    private ActionAdapter actionAdapter;

    public ActionsListFragment() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            actionType = (ActionType) getArguments().getSerializable("actionType");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_action_list, container, false);
        this.recyclerView = root.findViewById(R.id.actions_list_recycler_view);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        this.actionAdapter = new ActionAdapter();
        this.recyclerView.setAdapter(actionAdapter);
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            Collection<Action> actions = new ArrayList<>();
            if(actionType == ActionType.EXPENSE) {
                actions.addAll(FinancesApp.getInstance().getDatabase().expenseDao().getAll());
            } else if(actionType == ActionType.INCOME) {
                actions.addAll(FinancesApp.getInstance().getDatabase().incomeDao().getAll());
            }
            actionAdapter.setItems(actions);
        });
        return root;
    }
}