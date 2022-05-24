package com.example.car_shop.ui.add_car;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.car_shop.data.models.Car;
import com.example.car_shop.data.room.AppDatabase;
import com.example.car_shop.databinding.FragmentAddCarBinding;
import com.example.car_shop.ui.dashboard.DashboardFragment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddCar extends Fragment {
    private FragmentAddCarBinding binding;
    private ActivityResultLauncher<String> content;
    private Bitmap bitmap;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddCarBinding.inflate(inflater, container, false);

        binding.imgSelector.setOnClickListener(view -> {
            AddCar.this.content.launch("image/*");
        });

        content = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), result);
                    binding.imgSelector.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        binding.insert.setOnClickListener(view -> {
            String brand = binding.brand.getText().toString();
            String model = binding.model.getText().toString();
            int price = Integer.valueOf(binding.price.getText().toString());
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
            byte[] img = baos.toByteArray();
            Car car = new Car(brand, model, price, img);

            AppDatabase appDatabase = Room.databaseBuilder(binding.getRoot().getContext(), AppDatabase.class, "database").allowMainThreadQueries().build();
            appDatabase.carDao().insert(car);

            DashboardFragment dashboardFragment = new DashboardFragment();
            getFragmentManager()
                    .beginTransaction()
                    .replace(this.getId(), dashboardFragment, "car list")
                    .addToBackStack(null)
                    .commit();
        });

        return binding.getRoot();
    }
}