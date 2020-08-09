package me.r1411.finances.ui.fragments.add_action.add_income;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import me.r1411.finances.FinancesApp;
import me.r1411.finances.R;
import me.r1411.finances.objects.Income;
import me.r1411.finances.objects.IncomeDao;
import me.r1411.finances.ui.elements.FakeSpinner;
import me.r1411.finances.ui.elements.FakeSpinnerClickListener;
import me.r1411.finances.utils.DecimalDigitsInputFilter;
import me.r1411.finances.utils.FinancesDatabase;

public class AddIncomeFragment extends Fragment {
    private AddIncomeViewModel addIncomeViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        addIncomeViewModel = ViewModelProviders.of(this).get(AddIncomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_add_income, container, false);
        final Spinner categorySpinner = root.findViewById(R.id.add_income_category_spinner);
        addIncomeViewModel.getSpinnerElements().observe(getViewLifecycleOwner(), strings -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(root.getContext(), R.layout.spinner_item, strings);
            categorySpinner.setAdapter(adapter);
        });

        final FakeSpinner dateSpinner = root.findViewById(R.id.add_income_date_spinner);
        addIncomeViewModel.getDateString().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String str) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(root.getContext(), R.layout.spinner_item, new String[] {str});
                dateSpinner.setAdapter(adapter);
            }
        });

        final FakeSpinner timeSpinner = root.findViewById(R.id.add_income_time_spinner);
        addIncomeViewModel.getTimeString().observe(getViewLifecycleOwner(), new Observer<String>() {
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

                        addIncomeViewModel.setSelectedYear(year);
                        addIncomeViewModel.setSelectedMonth(month);
                        addIncomeViewModel.setSelectedDay(day);

                        if(year == currentYear && month == currentMonth && day == currentDay) {
                            addIncomeViewModel.setDateString(root.getContext().getString(R.string.today));
                        } else {
                            c.set(year, month, day);
                            addIncomeViewModel.setDateString(DateFormat.getDateInstance(DateFormat.MEDIUM).format(c.getTime()));
                        }

                    }
                }, addIncomeViewModel.getSelectedYear().getValue(), addIncomeViewModel.getSelectedMonth().getValue(), addIncomeViewModel.getSelectedDay().getValue());
                datePickerDialog.show();
            }
        });

        timeSpinner.setFakeSpinnerClickListener(new FakeSpinnerClickListener() {
            @Override
            public void onFakeSpinnerClick() {
                TimePickerDialog timePickerDialog = new TimePickerDialog(root.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {

                        addIncomeViewModel.setSelectedHour(hour);
                        addIncomeViewModel.setSelectedMinute(minute);

                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.HOUR_OF_DAY, hour);
                        cal.set(Calendar.MINUTE, minute);

                        String currentTimeString = new SimpleDateFormat("HH:mm").format(cal.getTime());
                        addIncomeViewModel.setTimeString(currentTimeString);
                    }
                }, addIncomeViewModel.getSelectedHour().getValue(), addIncomeViewModel.getSelectedMinute().getValue(), android.text.format.DateFormat.is24HourFormat(root.getContext()));
                timePickerDialog.show();
            }
        });

        final Button addIncomeButton = root.findViewById(R.id.add_income_done_btn);

        final EditText sumEditText = root.findViewById(R.id.add_income_sum_input);
        sumEditText.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(10,2)});
        sumEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                addIncomeButton.setEnabled(charSequence.toString().trim().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        addIncomeButton.setEnabled(sumEditText.getText().toString().trim().length() > 0);

        addIncomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, addIncomeViewModel.getSelectedYear().getValue());
                cal.set(Calendar.MONTH, addIncomeViewModel.getSelectedMonth().getValue());
                cal.set(Calendar.DAY_OF_MONTH, addIncomeViewModel.getSelectedDay().getValue());
                cal.set(Calendar.HOUR_OF_DAY, addIncomeViewModel.getSelectedHour().getValue());
                cal.set(Calendar.MINUTE, addIncomeViewModel.getSelectedMinute().getValue());

                long ts = cal.getTimeInMillis() / 1000;
                final Income income = new Income(categorySpinner.getSelectedItem().toString(), Double.parseDouble(sumEditText.getText().toString()), ts);

                Executor executor = Executors.newSingleThreadExecutor();
                executor.execute(() -> {
                    FinancesDatabase db = FinancesApp.getInstance().getDatabase();
                    IncomeDao incomeDao = db.incomeDao();
                    incomeDao.insert(income);
                });
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
