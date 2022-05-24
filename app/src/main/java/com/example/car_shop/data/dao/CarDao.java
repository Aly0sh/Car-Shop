package com.example.car_shop.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.car_shop.data.models.Car;

import java.util.List;

@Dao
public interface CarDao {
    @Query("SELECT * FROM Car")
    List<Car> getAll();

    @Query("SELECT * FROM Car WHERE userId = :userId")
    List<Car> getMyCars(long userId);

    @Insert
    void insert(Car car);

    @Delete
    void delete(Car car);
}
