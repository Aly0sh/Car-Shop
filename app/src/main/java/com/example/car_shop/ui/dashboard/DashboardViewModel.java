package com.example.car_shop.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.example.car_shop.data.dao.CarDao;
import com.example.car_shop.data.models.Car;
import com.example.car_shop.data.room.AppDatabase;
import com.example.car_shop.databinding.FragmentDashboardBinding;

import java.util.ArrayList;

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
        populateList();
        cars.setValue(carList);
    }

    private void populateList(){
        carDao = appDatabase.carDao();
        carDao.insert(new Car("Toyota" , "Corolla", 4000));
        carDao.insert(new Car("Toyota" , "Corolla", 4000));
        carDao.insert(new Car("Toyota" , "Corolla", 4000));
        carDao.insert(new Car("Toyota" , "Corolla", 4000));
        carDao.insert(new Car("Toyota" , "Corolla", 4000));
        carDao.insert(new Car("Toyota" , "Corolla", 4000));
        carDao.insert(new Car("Toyota" , "Corolla", 4000));
        carList = new ArrayList<>();
        carList.addAll(carDao.getAll());
    }

    public LiveData<ArrayList<Car>> getMutableLiveData() {
        init();
        return cars;
    }
}