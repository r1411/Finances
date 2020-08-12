package me.r1411.finances.objects;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "expense")
public class Expense extends Action {

    public Expense(String category, double sum, long ts) {
        super(category, sum, ts);
    }
}
