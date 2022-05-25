package com.example.car_shop.data.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Cart implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "userId")
    private long userId;

    @ColumnInfo(name = "carId")
    private long carId;

    public Cart(long userId, long carId){
        this.userId = userId;
        this.carId = carId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long user) {
        this.userId = user;
    }

    public long getCarId() {
        return carId;
    }

    public void setCarId(long car) {
        this.carId = car;
    }
}
