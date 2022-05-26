package com.example.car_shop.ui.add_car;


import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.car_shop.data.App;
import com.example.car_shop.data.models.Car;
import com.example.car_shop.data.room.AppDatabase;
import com.example.car_shop.databinding.FragmentAddCarBinding;
import com.example.car_shop.ui.cars.CarsFragment;
import com.example.car_shop.ui.mycars.MyCarsFragment;
import com.example.car_shop.userService.UserSingl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddCar extends Fragment {
    private FragmentAddCarBinding binding;
    private ActivityResultLauncher<String> content;
    private Bitmap bitmap;
    private boolean isImgSelected = false;



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
                    isImgSelected = true;
                } catch (IOException e) {
                    e.printStackTrace();
                    isImgSelected = false;
                }
            }
        });

        binding.insert.setOnClickListener(view -> {
            String brand = binding.brand.getText().toString();
            String model = binding.model.getText().toString();
            String strPrice = binding.price.getText().toString();
            if (brand.isEmpty() || model.isEmpty() || strPrice.isEmpty()){
                Toast.makeText(getContext(), "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show();
            }
            else {
                if (isImgSelected){
                    ByteArrayOutputStream baos=new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
                    byte[] img = baos.toByteArray();

                    int price = Integer.valueOf(strPrice);
                    Car car = new Car(brand, model, price, img, UserSingl.getUserSingln().getUserId(), UserSingl.getUserSingln().getPhone());
                    AppDatabase appDatabase = App.getAppDatabase(getContext());
                    appDatabase.carDao().insert(car);

                    MyCarsFragment dashboardFragment = new MyCarsFragment();
                    getFragmentManager()
                            .beginTransaction()
                            .setReorderingAllowed(true)
                            .disallowAddToBackStack()
                            .replace(getId(), dashboardFragment, "car list")
                            .commit();
                }
                else {
                    Toast.makeText(getContext(), "Выберите фотку", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return binding.getRoot();
    }
}