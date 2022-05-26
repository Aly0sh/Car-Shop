package com.example.car_shop.ui.cars;

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
import androidx.recyclerview.widget.RecyclerView;

import com.example.car_shop.data.App;
import com.example.car_shop.data.enums.UserRoles;
import com.example.car_shop.data.models.Car;
import com.example.car_shop.databinding.FragmentCarsBinding;
import com.example.car_shop.ui.add_car.AddCar;
import com.example.car_shop.userService.UserSingl;

import java.util.ArrayList;

public class CarsFragment extends Fragment {

    private FragmentCarsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CarsViewModel dashboardViewModel =
                new ViewModelProvider(this).get(CarsViewModel.class);

        binding = FragmentCarsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dashboardViewModel.setAppDatabase(App.getAppDatabase(getContext()));

        RecyclerView recyclerView = binding.carRecycler;
        recyclerView.setLayoutManager(new GridLayoutManager(binding.getRoot().getContext(), 2));
        recyclerView.setHasFixedSize(true);
        CarAdapter carAdapter = new CarAdapter();
        recyclerView.setAdapter(carAdapter);
        dashboardViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<Car>>() {
            @Override
            public void onChanged(ArrayList<Car> cars) {
                carAdapter.setList(cars);
                carAdapter.setCarsFragment(CarsFragment.this);
            }
        });
//        if(UserSingl.getUserSingln().getUserRole() == UserRoles.CLIENT){
//            binding.addCar.setVisibility(View.GONE);
//        }
//        binding.addCar.setOnClickListener(v ->  {
//            Fragment addCarFragment = new AddCar();
//            FragmentTransaction trans=getFragmentManager().beginTransaction();
//            trans.add(getId(), addCarFragment);
//            trans.commit();
//            binding.addCar.setVisibility(View.GONE);
//        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}