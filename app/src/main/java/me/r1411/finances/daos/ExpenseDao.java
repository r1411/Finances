package me.r1411.finances.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import me.r1411.finances.objects.Expense;

@Dao
public interface ExpenseDao {

    @Query("SELECT * FROM expense ORDER BY ts DESC, id DESC")
    List<Expense> getAll();

    @Query("SELECT * FROM expense ORDER BY ts DESC, id DESC LIMIT 3")
    List<Expense> getLatest();

    @Query("SELECT * FROM expense WHERE ts >= :oldestDate")
    List<Expense> getFromDate(long oldestDate);

    @Query("SELECT * FROM expense WHERE id = :id")
    Expense getById(long id);

    @Insert
    void insert(Expense expense);

    @Update
    void update(Expense expense);

    @Delete
    void delete(Expense expense);
}
