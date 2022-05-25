package com.example.car_shop.ui.mycars;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.car_shop.R;
import com.example.car_shop.data.App;
import com.example.car_shop.data.enums.UserRoles;
import com.example.car_shop.data.models.Car;
import com.example.car_shop.databinding.FragmentMyCarsBinding;
import com.example.car_shop.ui.add_car.AddCar;
import com.example.car_shop.ui.cars.CarAdapter;
import com.example.car_shop.ui.cars.CarsViewModel;

import java.util.ArrayList;

public class MyCarsFragment extends Fragment {
    private FragmentMyCarsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CarsViewModel dashboardViewModel =
                new ViewModelProvider(this).get(CarsViewModel.class);

        binding = FragmentMyCarsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dashboardViewModel.setAppDatabase(App.getAppDatabase(getContext()));

        RecyclerView recyclerView = binding.carRecycler;
        recyclerView.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        recyclerView.setHasFixedSize(true);
        CarAdapter carAdapter = new CarAdapter();
        recyclerView.setAdapter(carAdapter);
        dashboardViewModel.getMyCars().observe(getViewLifecycleOwner(), new Observer<ArrayList<Car>>() {
            @Override
            public void onChanged(ArrayList<Car> cars) {
                carAdapter.setList(cars);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}