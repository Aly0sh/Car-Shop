package com.example.car_shop.ui.dashboard;

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
import androidx.room.Room;

import com.example.car_shop.R;
import com.example.car_shop.data.models.Car;
import com.example.car_shop.data.room.AppDatabase;
import com.example.car_shop.databinding.FragmentDashboardBinding;
import com.example.car_shop.ui.add_car.AddCar;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dashboardViewModel.setAppDatabase(Room.databaseBuilder(binding.getRoot().getContext(), AppDatabase.class, "database").allowMainThreadQueries().build());

        RecyclerView recyclerView = binding.carRecycler;
        recyclerView.setLayoutManager(new GridLayoutManager(binding.getRoot().getContext(), 2));
        recyclerView.setHasFixedSize(true);
        CarAdapter carAdapter = new CarAdapter();
        recyclerView.setAdapter(carAdapter);
        dashboardViewModel.getMyCars().observe(getViewLifecycleOwner(), new Observer<ArrayList<Car>>() {
            @Override
            public void onChanged(ArrayList<Car> cars) {
                carAdapter.setList(cars);
            }
        });
        binding.addCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment addCarFragment = new AddCar();
                FragmentTransaction trans=getFragmentManager().beginTransaction();
                trans.add(R.id.addCarFrame, addCarFragment);
                trans.commit();
                binding.addCar.setVisibility(View.GONE);
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