package me.r1411.finances.objects;

import androidx.room.Entity;

@Entity(tableName = "expense")
public class Expense extends Action {
    public Expense(String category, double sum, long ts, String comment) {
        super(category, sum, ts, comment);
    }
}
