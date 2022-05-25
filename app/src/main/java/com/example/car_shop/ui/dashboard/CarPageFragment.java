package com.example.car_shop.ui.dashboard;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.car_shop.R;
import com.example.car_shop.data.models.Car;
import com.example.car_shop.databinding.FragmentCarPageBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CarPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarPageFragment extends Fragment {
    private FragmentCarPageBinding binding;

    public CarPageFragment() {
        // Required empty public constructor
    }
    public static CarPageFragment newInstance(String param1, String param2) {
        CarPageFragment fragment = new CarPageFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCarPageBinding.inflate(getLayoutInflater());

        Bundle bundle = getArguments();
        Car car = (Car) bundle.get("car");

        binding.imgSelector.setImageBitmap(BitmapFactory.decodeByteArray(car.getPhoto(), 0, car.getPhoto().length));
        binding.brand.setText("Бренд:" + car.getBrand());
        binding.model.setText("Модель:" + car.getModel());
        binding.price.setText("Цена:" + car.getPrice());
        binding.sellerPhone.setText(car.getSellerPhone());
        binding.showPhone.setOnClickListener(v -> {
            binding.sellerPhone.setVisibility(View.VISIBLE);
        });
        binding.sellerPhone.setOnClickListener(v -> {
            if (binding.sellerPhone.getVisibility() == View.VISIBLE){
                getContext().startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + car.getSellerPhone())));
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}