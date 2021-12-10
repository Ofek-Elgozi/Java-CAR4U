package com.example.car4u.Model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CarDao
{
    @Query("select * from Car")
    List<Car> getAllCars();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Car... cars);

    @Delete
    void delete(Car car);

    @Query("SELECT * FROM Car WHERE owner=:owner ")
    Car getCarByOwner(String owner);
}
