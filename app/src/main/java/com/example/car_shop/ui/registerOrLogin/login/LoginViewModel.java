package com.example.car_shop.ui.registerOrLogin.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.car_shop.R;
import com.example.car_shop.data.dao.UserDao;
import com.example.car_shop.data.models.User;
import com.example.car_shop.data.room.AppDatabase;
import com.example.car_shop.userService.UserSingl;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<User> user;
    private AppDatabase room;
    private UserDao userDao;

    public void setDatabase(AppDatabase database){
        room = database;
        userDao = room.userDao();
    }

    public boolean checkUser(String username, String password) {
        User user = userDao.getUser(username);
        if (user != null){
            UserSingl.setUser(user);
            System.out.println(UserSingl.getUserSingln().getRole());
            return user.getPassword().equals(password);
        }
        else {
            return false;
        }
    }


}