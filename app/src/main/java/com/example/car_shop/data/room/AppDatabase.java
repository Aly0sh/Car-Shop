package com.example.car_shop.data.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.car_shop.data.dao.CarDao;
import com.example.car_shop.data.dao.UserDao;
import com.example.car_shop.data.models.Car;
import com.example.car_shop.data.models.User;

@Database(entities = {Car.class, User.class}, version = 1, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase room;

    public abstract CarDao carDao();

    public abstract UserDao userDao();

}