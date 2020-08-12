package me.r1411.finances.objects;

import androidx.room.PrimaryKey;

public class Action {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String category;
    private double sum;
    private long ts;

    public Action(String category, double sum, long ts) {
        this.category = category;
        this.sum = sum;
        this.ts = ts;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
