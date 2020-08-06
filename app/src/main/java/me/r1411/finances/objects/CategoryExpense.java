package me.r1411.finances.objects;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "expense_categories")
public class CategoryExpense {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;

    public CategoryExpense(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
