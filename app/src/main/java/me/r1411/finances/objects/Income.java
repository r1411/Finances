package me.r1411.finances.objects;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "income")
public class Income extends Action {

    public Income(String category, double sum, long ts) {
        super(category, sum, ts);
    }
}
