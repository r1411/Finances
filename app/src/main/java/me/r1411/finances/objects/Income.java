package me.r1411.finances.objects;

import androidx.room.Entity;

@Entity(tableName = "income")
public class Income extends Action {
    public Income(String category, double sum, long ts, String comment) {
        super(category, sum, ts, comment);
    }
}
