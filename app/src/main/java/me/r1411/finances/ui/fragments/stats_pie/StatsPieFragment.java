package me.r1411.finances.ui.fragments.stats_pie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

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
        statsPieViewModel = ViewModelProviders.of(this).get(StatsPieViewModel.class);
        View root = inflater.inflate(R.layout.fragment_stats_pie, container, false);
        ActionType actionType = (ActionType) getArguments().getSerializable("actionType");
        PieChart pieChart = root.findViewById(R.id.stats_pie_chart);
        pieChart.getLegend().setEnabled(true);
        pieChart.getLegend().setTextSize(11);
        pieChart.getLegend().setWordWrapEnabled(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setTouchEnabled(false);
        pieChart.setDrawEntryLabels(false);
        pieChart.setUsePercentValues(true);
        pieChart.setTransparentCircleRadius(0);
        pieChart.setExtraOffsets(0, 10.0f, 0, 10.0f);

        statsPieViewModel.getPieEntries().observe(getViewLifecycleOwner(), pieEntries -> {
            PieDataSet pieDataSet = new PieDataSet(pieEntries, null);
            pieDataSet.setColors(getResources().getIntArray(R.array.pieChartColors));
            //
            pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
            pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
            pieDataSet.setValueLinePart1OffsetPercentage(100f);
            pieDataSet.setValueLinePart1Length(0.6f);
            pieDataSet.setValueLinePart2Length(0.6f);
            //
            PieData pieData = new PieData(pieDataSet);

            pieData.setValueFormatter(new PercentValuesFormatter(pieChart));
            pieData.setValueTextSize(12);
            pieData.setValueTextColor(getResources().getColor(R.color.colorPrimaryText));
            pieChart.setData(pieData);
        });
        ((TextView) root.findViewById(R.id.stats_pie_title)).setText(actionType == ActionType.EXPENSE ? R.string.expenses_in_month  : R.string.incomes_in_month);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}