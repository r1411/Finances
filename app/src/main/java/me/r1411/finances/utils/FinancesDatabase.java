package me.r1411.finances.utils;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import me.r1411.finances.objects.CategoryExpense;
import me.r1411.finances.daos.CategoryExpenseDao;
import me.r1411.finances.objects.CategoryIncome;
import me.r1411.finances.daos.CategoryIncomeDao;
import me.r1411.finances.objects.Expense;
import me.r1411.finances.daos.ExpenseDao;
import me.r1411.finances.objects.Income;
import me.r1411.finances.daos.IncomeDao;

@Database(entities = {Expense.class, CategoryExpense.class, Income.class, CategoryIncome.class}, version = 1, exportSchema = false)
public abstract class FinancesDatabase extends RoomDatabase {

    public abstract ExpenseDao expenseDao();
    public abstract IncomeDao incomeDao();
    public abstract CategoryExpenseDao categoryExpenseDao();
    public abstract CategoryIncomeDao categoryIncomeDao();
}
