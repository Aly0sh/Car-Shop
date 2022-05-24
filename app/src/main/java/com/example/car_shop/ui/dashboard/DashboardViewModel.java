package com.example.car_shop.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.example.car_shop.data.dao.CarDao;
import com.example.car_shop.data.models.Car;
import com.example.car_shop.data.room.AppDatabase;
import com.example.car_shop.databinding.FragmentDashboardBinding;
import com.example.car_shop.userService.UserSingl;

import java.util.ArrayList;
import java.util.List;

public class DashboardViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Car>> cars;
    private ArrayList<Car> carList;
    private AppDatabase appDatabase;
    private CarDao carDao;


    public void setAppDatabase(AppDatabase appDatabase){
        this.appDatabase = appDatabase;
    }

    public DashboardViewModel() {
        cars = new MutableLiveData<>();
    }

    private void init(){
        carDao = appDatabase.carDao();
    }

    private void populateList(List<Car> cars1){
        carList = new ArrayList<>();
//        carDao.insert(new Car("Lamborghini", "Aventador", 4000000));
        carList.addAll(cars1);
        cars.setValue(carList);
    }

    public LiveData<ArrayList<Car>> getMutableLiveData() {
        init();
        populateList(carDao.getAll());
        return cars;
    }

    public LiveData<ArrayList<Car>> getMyCars(){
        init();
        populateList(carDao.getMyCars(UserSingl.getUserSingln().getUserId()));
        return cars;
    }
}