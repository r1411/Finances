package me.r1411.finances.ui.add_expense;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import me.r1411.finances.R;

public class AddExpenseFragment extends Fragment {

    private AddExpenseViewModel addExpenseViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addExpenseViewModel =
                ViewModelProviders.of(this).get(AddExpenseViewModel.class);
        View root = inflater.inflate(R.layout.fragment_add_expense, container, false);
        final TextView textView = root.findViewById(R.id.text_add_expense);
        addExpenseViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}