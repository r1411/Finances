package me.r1411.finances.objects;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CategoryIncomeDao {

    @Query("SELECT * FROM income_categories")
    List<CategoryIncome> getAll();

    @Query("SELECT * FROM income_categories WHERE id = :id")
    CategoryIncome getById(long id);

    @Query("SELECT * FROM income_categories WHERE name = :name")
    CategoryIncome getByName(String name);

    @Insert
    void insert(CategoryIncome category);

    @Update
    void update(CategoryIncome category);

    @Delete
    void delete(CategoryIncome category);

}
