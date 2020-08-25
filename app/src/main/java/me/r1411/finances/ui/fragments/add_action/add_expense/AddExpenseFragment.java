package me.r1411.finances.ui.fragments.add_action.add_expense;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import me.r1411.finances.FinancesApp;
import me.r1411.finances.MainActivity;
import me.r1411.finances.R;
import me.r1411.finances.daos.ExpenseDao;
import me.r1411.finances.objects.Expense;
import me.r1411.finances.ui.elements.FakeSpinner;
import me.r1411.finances.ui.elements.FakeSpinnerClickListener;
import me.r1411.finances.ui.fragments.home.HomeViewModel;
import me.r1411.finances.ui.fragments.stats_pie.StatsPieExpenseViewModel;
import me.r1411.finances.utils.DecimalDigitsInputFilter;
import me.r1411.finances.utils.FinancesDatabase;

public class AddExpenseFragment extends Fragment {

    private AddExpenseViewModel addExpenseViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        addExpenseViewModel = new ViewModelProvider(this).get(AddExpenseViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_add_expense, container, false);
        final Spinner categorySpinner = root.findViewById(R.id.add_expense_category_spinner);
        addExpenseViewModel.getSpinnerElements().observe(getViewLifecycleOwner(), strings -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(root.getContext(), R.layout.spinner_item, strings);
            categorySpinner.setAdapter(adapter);
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
                }, addExpenseViewModel.getSelectedHour().getValue(), addExpenseViewModel.getSelectedMinute().getValue(), android.text.format.DateFormat.is24HourFormat(root.getContext()));
                timePickerDialog.show();
            }
        });

        final Button addExpenseButton = root.findViewById(R.id.add_expense_done_btn);

        final EditText sumEditText = root.findViewById(R.id.add_expense_sum_input);
        sumEditText.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(10,2)});
        sumEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                addExpenseButton.setEnabled(false);
                try {
                    Double.parseDouble(sumEditText.getText().toString());
                    addExpenseButton.setEnabled(true);
                } catch (Exception ignored) {}
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        addExpenseButton.setEnabled(sumEditText.getText().toString().trim().length() > 0);

        EditText commentEditText = root.findViewById(R.id.add_expense_comment_input);

        addExpenseButton.setOnClickListener(view -> {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, addExpenseViewModel.getSelectedYear().getValue());
            cal.set(Calendar.MONTH, addExpenseViewModel.getSelectedMonth().getValue());
            cal.set(Calendar.DAY_OF_MONTH, addExpenseViewModel.getSelectedDay().getValue());
            cal.set(Calendar.HOUR_OF_DAY, addExpenseViewModel.getSelectedHour().getValue());
            cal.set(Calendar.MINUTE, addExpenseViewModel.getSelectedMinute().getValue());

            long ts = cal.getTimeInMillis() / 1000;
            final Expense expense = new Expense(categorySpinner.getSelectedItem().toString(), Double.parseDouble(sumEditText.getText().toString()), ts, commentEditText.getText().toString());

            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                FinancesDatabase db = FinancesApp.getInstance().getDatabase();
                ExpenseDao expenseDao = db.expenseDao();
                expenseDao.insert(expense);

                HomeViewModel homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
                homeViewModel.updateLatestActions();

                StatsPieExpenseViewModel statsPieExpenseViewModel = new ViewModelProvider(requireActivity()).get(StatsPieExpenseViewModel.class);
                statsPieExpenseViewModel.updatePieData();
                
                MainActivity mainActivity = (MainActivity) FragmentManager.findFragment(view).getActivity();
                mainActivity.runOnUiThread(() -> mainActivity.getNavController().navigate(R.id.action_global_navigation_home));
            });


        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
