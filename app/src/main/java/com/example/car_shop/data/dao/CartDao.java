package com.example.car_shop.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.car_shop.data.models.Car;
import com.example.car_shop.data.models.Cart;

import java.util.List;

@Dao
public interface CartDao {
//    @Query("SELECT * FROM Carts")
//    List<Car> getAll();
//
//    @Query("SELECT * FROM Carts WHERE user_id = :userId")
//    List<Car> getMyCars(long userId);

    @Query("SELECT * FROM carts WHERE user_id = :userId")
    List<Cart> getMyCarts(long userId);

    @Insert
    void insert(Cart cart);

    @Delete
    void delete(Cart cart);
}
