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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.car_shop.data.App;
import com.example.car_shop.data.dao.CarDao;
import com.example.car_shop.data.dao.CartDao;
import com.example.car_shop.data.enums.UserRoles;
import com.example.car_shop.data.models.Car;
import com.example.car_shop.databinding.FragmentMyCarsBinding;
import com.example.car_shop.ui.add_car.AddCar;
import com.example.car_shop.ui.cars.CarAdapter;
import com.example.car_shop.ui.cars.CarsViewModel;
import com.example.car_shop.userService.UserSingl;

import java.util.ArrayList;
import java.util.List;

public class MyCarsFragment extends Fragment {
    private FragmentMyCarsBinding binding;
    private CarsViewModel carsViewModel;
    private MyCarsViewModel myCarsViewModel;
    private static MyCarsAdapter carAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        carsViewModel = new ViewModelProvider(this).get(CarsViewModel.class);
        myCarsViewModel = new ViewModelProvider(this).get(MyCarsViewModel.class);

        binding = FragmentMyCarsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        myCarsViewModel.setAppDatabase(App.getAppDatabase(getContext()));
        carsViewModel.setAppDatabase(App.getAppDatabase(getContext()));

        RecyclerView recyclerView = binding.carRecycler;
        recyclerView.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        recyclerView.setHasFixedSize(true);
        carAdapter = new MyCarsAdapter();
        recyclerView.setAdapter(carAdapter);
        if (UserSingl.getUserSingln().getUserRole() == UserRoles.CLIENT){
            binding.addCar.setVisibility(View.GONE);
            myCarsViewModel.getMyCars().observe(getViewLifecycleOwner(), new Observer<ArrayList<Car>>() {
                @Override
                public void onChanged(ArrayList<Car> cars) {
                    carAdapter.setList(cars);
                    carAdapter.setMyCarsFragment(MyCarsFragment.this);
                }
            });
        }
        else if (UserSingl.getUserSingln().getUserRole() == UserRoles.SELLER) {
            carsViewModel.getMyCars().observe(getViewLifecycleOwner(), new Observer<ArrayList<Car>>() {
                @Override
                public void onChanged(ArrayList<Car> cars) {
                    carAdapter.setList(cars);
                    carAdapter.setMyCarsFragment(MyCarsFragment.this);
                }
            });
        }
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.addCar.setOnClickListener(v ->  {
            Fragment addCarFragment = new AddCar();
            getFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(getId(), addCarFragment)
                    .addToBackStack(null)
                    .commit();

            binding.addCar.setVisibility(View.GONE);
        });
    }

    public static void updateRecycler(){
        carAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}