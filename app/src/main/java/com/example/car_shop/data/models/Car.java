package com.example.car_shop.data.models;

import androidx.annotation.ColorInt;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.car_shop.data.enums.Status;

import java.io.Serializable;

@Entity(tableName = "car")
public class Car implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "brand")
    private String brand;

    @ColumnInfo(name = "model")
    private String model;

    @ColumnInfo(name = "price")
    private int price;

    @ColumnInfo(name = "userId")
    private long userId;

    @ColumnInfo(name = "seller_phone")
    private String sellerPhone;

    @ColumnInfo(name = "car_status")
    private Status status;

    @ColumnInfo(name = "photo", typeAffinity = ColumnInfo.BLOB)
    private byte[] photo;



    public Car(){}

    public Car(String brand, String model, int price, byte[] photo, long userId, String sellerPhone){
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.photo = photo;
        this.userId = userId;
        this.sellerPhone = sellerPhone;
        this.status = Status.SALE;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getSellerPhone() {
        return sellerPhone;
    }

    public void setSellerPhone(String sellerPhone) {
        this.sellerPhone = sellerPhone;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
