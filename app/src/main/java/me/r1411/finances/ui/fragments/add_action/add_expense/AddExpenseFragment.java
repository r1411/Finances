package me.r1411.finances.ui.fragments.add_action.add_expense;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import me.r1411.finances.R;
import me.r1411.finances.ui.elements.FakeSpinner;
import me.r1411.finances.ui.elements.FakeSpinnerClickListener;

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

        final FakeSpinner dateSpinner = root.findViewById(R.id.add_expense_date_spinner);
        addExpenseViewModel.getDateString().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String str) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(root.getContext(), R.layout.spinner_item, new String[] {str});
                dateSpinner.setAdapter(adapter);
            }
        });

        final FakeSpinner timeSpinner = root.findViewById(R.id.add_expense_time_spinner);
        addExpenseViewModel.getTimeString().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String str) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(root.getContext(), R.layout.spinner_item, new String[] {str});
                timeSpinner.setAdapter(adapter);
            }
        });

        dateSpinner.setFakeSpinnerClickListener(new FakeSpinnerClickListener() {
            @Override
            public void onFakeSpinnerClick() {
                DatePickerDialog datePickerDialog = new DatePickerDialog(root.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        final Calendar c = Calendar.getInstance();
                        final int currentYear = c.get(Calendar.YEAR);
                        final int currentMonth = c.get(Calendar.MONTH);
                        final int currentDay = c.get(Calendar.DAY_OF_MONTH);

                        addExpenseViewModel.setSelectedYear(year);
                        addExpenseViewModel.setSelectedMonth(month);
                        addExpenseViewModel.setSelectedDay(day);

                        if(year == currentYear && month == currentMonth && day == currentDay) {
                            addExpenseViewModel.setDateString(root.getContext().getString(R.string.today));
                        } else {
                            c.set(year, month, day);
                            addExpenseViewModel.setDateString(DateFormat.getDateInstance(DateFormat.MEDIUM).format(c.getTime()));
                        }

                    }
                }, addExpenseViewModel.getSelectedYear().getValue(), addExpenseViewModel.getSelectedMonth().getValue(), addExpenseViewModel.getSelectedDay().getValue());
                datePickerDialog.show();
            }
        });

        timeSpinner.setFakeSpinnerClickListener(new FakeSpinnerClickListener() {
            @Override
            public void onFakeSpinnerClick() {
                TimePickerDialog timePickerDialog = new TimePickerDialog(root.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        addExpenseViewModel.setSelectedHour(hour);
                        addExpenseViewModel.setSelectedMinute(minute);

                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.HOUR_OF_DAY, hour);
                        cal.set(Calendar.MINUTE, minute);

                        String currentTimeString = new SimpleDateFormat("HH:mm").format(cal.getTime());
                        addExpenseViewModel.setTimeString(currentTimeString);
                    }
                }, addExpenseViewModel.getSelectedHour().getValue(), addExpenseViewModel.getSelectedMinute().getValue(), true);
                timePickerDialog.show();
            }
        });


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
