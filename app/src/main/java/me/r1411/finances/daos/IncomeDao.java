package me.r1411.finances.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import me.r1411.finances.objects.Income;

@Dao
public interface IncomeDao {

    @Query("SELECT * FROM income ORDER BY ts DESC, id DESC")
    List<Income> getAll();

    @Query("SELECT * FROM income ORDER BY ts DESC, id DESC LIMIT 3")
    List<Income> getLatest();

    @Query("SELECT * FROM income WHERE ts >= :oldestDate")
    List<Income> getFromDate(long oldestDate);

    @Query("SELECT * FROM income WHERE id = :id")
    Income getById(long id);

    @Insert
    void insert(Income income);

    @Update
    void update(Income income);

    @Delete
    void delete(Income income);
}
