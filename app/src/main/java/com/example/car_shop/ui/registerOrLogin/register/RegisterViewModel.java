package com.example.car_shop.ui.registerOrLogin.register;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.car_shop.data.dao.UserDao;
import com.example.car_shop.data.models.User;
import com.example.car_shop.data.room.AppDatabase;

public class RegisterViewModel extends ViewModel {
    private MutableLiveData<User> user;
    private AppDatabase room;
    private UserDao userDao;

    public void setDatabase(AppDatabase database){
        room = database;
        userDao = room.userDao();
    }

    public void register(User user) {
        if (user != null){
            userDao.insert(user);
        }
    }
}