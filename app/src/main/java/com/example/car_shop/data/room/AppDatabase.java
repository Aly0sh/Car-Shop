package com.example.car_shop.data.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.car_shop.data.dao.CarDao;
import com.example.car_shop.data.models.Car;

@Database(entities = {Car.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CarDao carDao();

}