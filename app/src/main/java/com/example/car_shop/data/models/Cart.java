package com.example.car_shop.data.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(foreignKeys = @ForeignKey(entity = Car.class,
        parentColumns = "id",
        childColumns = "car_id"))
public class Cart implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "user_id")
    private long user;

    @ColumnInfo(name = "car_id")
    private long car;

    public Cart(long userId, long carId){
        this.user = userId;
        this.car = carId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public long getCar() {
        return car;
    }

    public void setCar(long car) {
        this.car = car;
    }
}
