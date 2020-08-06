package me.r1411.finances.utils;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import me.r1411.finances.objects.CategoryExpense;
import me.r1411.finances.objects.CategoryExpenseDao;
import me.r1411.finances.objects.Expense;
import me.r1411.finances.objects.ExpenseDao;

@Database(entities = {Expense.class, CategoryExpense.class}, version = 1)
public abstract class FinancesDatabase extends RoomDatabase {

    public abstract ExpenseDao expenseDao();
    public abstract CategoryExpenseDao categoryExpenseDao();
}
