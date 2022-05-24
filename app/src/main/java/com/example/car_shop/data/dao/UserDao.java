package com.example.car_shop.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.car_shop.data.models.Car;
import com.example.car_shop.data.models.User;


@Dao
public interface UserDao {
    @Query("SELECT * FROM users WHERE username = :username;")
    User getUser(String username);

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);
}
