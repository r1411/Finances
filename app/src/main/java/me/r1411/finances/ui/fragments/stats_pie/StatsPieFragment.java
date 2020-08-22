package me.r1411.finances.ui.fragments.stats_pie;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import me.r1411.finances.FinancesApp;
import me.r1411.finances.R;
import me.r1411.finances.objects.ActionType;

public class StatsPieFragment extends Fragment {

    private StatsPieViewModel statsPieViewModel;

    public StatsPieFragment() {

    }

    public static StatsPieFragment newInstance(ActionType actionType) {
        Bundle args = new Bundle();
        args.putSerializable("actionType", actionType);
        StatsPieFragment f = new StatsPieFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ActionType actionType = (ActionType) getArguments().getSerializable("actionType");
        Log.d("SPF", "ActionType: " + actionType);
        statsPieViewModel = new ViewModelProvider(this, new StatsPieViewModelFactory(actionType)).get(StatsPieViewModel.class);
        View root = inflater.inflate(R.layout.fragment_stats_pie, container, false);

        PieChart pieChart = root.findViewById(R.id.stats_pie_chart);
        pieChart.setRenderer(new StatsPieRenderer(pieChart, pieChart.getAnimator(), pieChart.getViewPortHandler()));
        pieChart.getLegend().setEnabled(true);
        pieChart.getLegend().setTextSize(11);
        pieChart.getLegend().setWordWrapEnabled(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setTouchEnabled(false);
        pieChart.setDrawEntryLabels(false);
        pieChart.setUsePercentValues(true);
        pieChart.setTransparentCircleRadius(0);
        pieChart.setExtraOffsets(0, 10.0f, 0, 10.0f);
        pieChart.getPaint(Chart.PAINT_INFO).setTextSize(Utils.convertDpToPixel(18f));
        pieChart.setNoDataText(getContext().getString(R.string.loading_dots));
        pieChart.setNoDataTextColor(ContextCompat.getColor(getContext(), R.color.lightGray));
        pieChart.setNoDataTextTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
        pieChart.setCenterTextColor(ContextCompat.getColor(getContext(), actionType == ActionType.EXPENSE ? R.color.expenseColor : R.color.incomeColor));

        statsPieViewModel.getPieEntries().observe(getViewLifecycleOwner(), pieEntries -> {
            if(pieEntries.size() > 0) {
                PieDataSet pieDataSet = new PieDataSet(pieEntries, null);
                pieDataSet.setColors(getResources().getIntArray(R.array.pieChartColors));
                pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                pieDataSet.setValueLinePart1OffsetPercentage(100f);
                pieDataSet.setValueLinePart1Length(0.6f);
                pieDataSet.setValueLinePart2Length(0.6f);

                PieData pieData = new PieData(pieDataSet);
                pieData.setValueFormatter(new PercentValuesFormatter(pieChart));
                pieData.setValueTextSize(12);
                pieData.setValueTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryText));
                pieChart.setData(pieData);
            } else {
                pieChart.setNoDataText(getContext().getString(R.string.no_pie_data));
            }
            pieChart.invalidate();
        });

        statsPieViewModel.getPieCenterValue().observe(getViewLifecycleOwner(), pieCenterValue -> {
            if(pieCenterValue > 0) {
                String pieCenterString = Math.round(pieCenterValue) + FinancesApp.getContext().getString(R.string.currency_postfix);
                pieChart.setCenterText(pieCenterString);
                int textSize = 24;
                if (pieCenterValue >= 1000000)
                    textSize = 22;
                if (pieCenterValue >= 10000000)
                    textSize = 20;
                pieChart.setCenterTextSize(textSize);
            }
        });

        SimpleDateFormat dateFormat = new SimpleDateFormat( "LLLL", Locale.getDefault());
        String monthText = String.format(getResources().getString(actionType == ActionType.EXPENSE ? R.string.expenses_in_month  : R.string.incomes_in_month), dateFormat.format(new Date()));
        ((TextView) root.findViewById(R.id.stats_pie_title)).setText(monthText);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public StatsPieViewModel getStatsPieViewModel() {
        return this.statsPieViewModel;
    }

}