package com.example.car_shop.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.car_shop.data.enums.Status;
import com.example.car_shop.data.models.Car;

import java.util.List;

@Dao
public interface CarDao {


    @Query("SELECT * FROM Car")
    List<Car> getAll();

    @Query("SELECT * FROM Car WHERE userId = :userId")
    List<Car> getMyCars(long userId);

    @Query("SELECT * FROM Car WHERE id = :carId")
    Car getById(long carId);

    @Query("SELECT model FROM Car")
    List<String> getsize();

    @Insert
    void insert(Car car);

    @Query("UPDATE car SET car_status = :status WHERE id = :carId")
    void updateStatus(Status status, long carId);

    @Update
    void update(Car car);

    @Query("DELETE FROM car WHERE model = :model")
    void deleteByModel(String model);

    @Delete
    void delete(Car car);

}
