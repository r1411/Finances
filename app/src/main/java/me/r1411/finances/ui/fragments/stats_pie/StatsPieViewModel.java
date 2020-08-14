package me.r1411.finances.ui.fragments.stats_pie;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class StatsPieViewModel extends ViewModel {
    private MutableLiveData<List<PieEntry>> pieEntries;

    public StatsPieViewModel() {
        pieEntries = new MutableLiveData<>();

        List<PieEntry> entriesList = new ArrayList<>();
        entriesList.add(new PieEntry(3.0F, "Категория 1"));
        entriesList.add(new PieEntry(3.0F, "Категория 2"));
        entriesList.add(new PieEntry(3.0F, "Категория 3"));
        entriesList.add(new PieEntry(3.0F, "Категория 4"));
        entriesList.add(new PieEntry(6.0F, "Категория 5"));
        entriesList.add(new PieEntry(3.0F, "Категория 6"));
        entriesList.add(new PieEntry(3.0F, "Категория 7"));
        entriesList.add(new PieEntry(3.0F, "Категория 8"));
        entriesList.add(new PieEntry(3.0F, "Категория 9"));
        entriesList.add(new PieEntry(3.0F, "Категория 10"));
        entriesList.add(new PieEntry(3.0F, "Категория 11"));
        entriesList.add(new PieEntry(3.0F, "Категория 12"));
        entriesList.add(new PieEntry(3.0F, "Категория 13"));
        entriesList.add(new PieEntry(3.0F, "Категория 14"));
        entriesList.add(new PieEntry(3.0F, "Категория 15"));
        entriesList.add(new PieEntry(3.0F, "Категория 16"));

        this.pieEntries.setValue(entriesList);
    }

    public MutableLiveData<List<PieEntry>> getPieEntries() {
        return pieEntries;
    }

    public void setPieEntries(MutableLiveData<List<PieEntry>> pieEntries) {
        this.pieEntries = pieEntries;
    }
}