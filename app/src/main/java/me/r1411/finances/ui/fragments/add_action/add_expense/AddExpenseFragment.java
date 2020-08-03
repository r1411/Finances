package me.r1411.finances.ui.fragments.add_action.add_expense;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;

import me.r1411.finances.R;

public class AddExpenseFragment extends Fragment {

    private AddExpenseViewModel addExpenseViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        addExpenseViewModel = ViewModelProviders.of(this).get(AddExpenseViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_add_expense, container, false);
        final Spinner categorySpinner = root.findViewById(R.id.add_expense_category_spinner);
        addExpenseViewModel.getSpinnerElements().observe(getViewLifecycleOwner(), new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(root.getContext(), R.layout.spinner_item, strings);
                categorySpinner.setAdapter(adapter);
            }
        });

        final Spinner dateSpinner = root.findViewById(R.id.add_expense_date_spinner);
        addExpenseViewModel.getDateString().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String str) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(root.getContext(), R.layout.spinner_item, new String[] {str});
                dateSpinner.setAdapter(adapter);
            }
        });

        final Spinner timeSpinner = root.findViewById(R.id.add_expense_time_spinner);
        addExpenseViewModel.getTimeString().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String str) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(root.getContext(), R.layout.spinner_item, new String[] {str});
                timeSpinner.setAdapter(adapter);
            }
        });


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
