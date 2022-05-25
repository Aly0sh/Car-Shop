package com.example.car_shop.ui.mycars;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.car_shop.data.dao.CarDao;
import com.example.car_shop.data.dao.CartDao;
import com.example.car_shop.data.models.Car;
import com.example.car_shop.data.models.Cart;
import com.example.car_shop.data.room.AppDatabase;
import com.example.car_shop.userService.UserSingl;

import java.util.ArrayList;
import java.util.List;

public class MyCarsViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<Cart>> carts;
    private final MutableLiveData<ArrayList<Car>> cars;
    private ArrayList<Cart> cartArrayList;
    private AppDatabase appDatabase;
    private CartDao cartDao;
    private CarDao carDao;

    public MyCarsViewModel() {
        carts = new MutableLiveData<>();
        cars = new MutableLiveData<>();
    }

    public void setAppDatabase(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;

    }

    private void init(){
        cartDao = appDatabase.cartDao();
        carDao = appDatabase.carDao();
    }

    public LiveData<ArrayList<Car>> getMyCars(){
        init();
        ArrayList<Car> myCars = new ArrayList<>();
        List<Cart> myCarts = cartDao.getMyCarts(UserSingl.getUserSingln().getUserId());

        for (Cart cart : myCarts){
            myCars.add(carDao.getById(cart.getCarId()));
        }

        cars.setValue(myCars);

        return cars;
    }
}