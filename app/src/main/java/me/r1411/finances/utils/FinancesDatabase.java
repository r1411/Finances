package me.r1411.finances.utils;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import me.r1411.finances.objects.Expense;
import me.r1411.finances.objects.ExpenseDao;

@Database(entities = {Expense.class}, version = 1)
public abstract class FinancesDatabase extends RoomDatabase {

    public abstract ExpenseDao expenseDao();
}
