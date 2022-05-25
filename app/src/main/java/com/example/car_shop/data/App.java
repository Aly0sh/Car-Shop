package com.example.car_shop.data;

import android.content.Context;

import androidx.room.Room;

import com.example.car_shop.data.room.AppDatabase;

public class App {
    private static AppDatabase appDatabase;

    public static AppDatabase getAppDatabase(Context context){
        if (appDatabase == null){
            appDatabase = Room
                    .databaseBuilder(context, AppDatabase.class, "database")
                    .allowMainThreadQueries()
                    .build();
        }
        return appDatabase;
    }
}
