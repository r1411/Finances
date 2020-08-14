package me.r1411.finances.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import me.r1411.finances.objects.CategoryExpense;

@Dao
public interface CategoryExpenseDao {

    @Query("SELECT * FROM expense_categories")
    List<CategoryExpense> getAll();

    @Query("SELECT * FROM expense_categories WHERE id = :id")
    CategoryExpense getById(long id);

    @Query("SELECT * FROM expense_categories WHERE name = :name")
    CategoryExpense getByName(String name);

    @Insert
    void insert(CategoryExpense category);

    @Update
    void update(CategoryExpense category);

    @Delete
    void delete(CategoryExpense category);

}
