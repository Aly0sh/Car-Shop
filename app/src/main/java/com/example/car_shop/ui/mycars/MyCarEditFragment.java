package com.example.car_shop.ui.mycars;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.example.car_shop.data.App;
import com.example.car_shop.data.models.Car;
import com.example.car_shop.data.room.AppDatabase;
import com.example.car_shop.databinding.FragmentAddCarBinding;
import com.example.car_shop.databinding.FragmentMyCarEditBinding;
import com.example.car_shop.ui.add_car.AddCar;
import com.example.car_shop.ui.cars.CarsFragment;
import com.example.car_shop.userService.UserSingl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MyCarEditFragment extends Fragment {
    private FragmentMyCarEditBinding binding;
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
        binding = FragmentMyCarEditBinding.inflate(getLayoutInflater());

        Bundle bundle = getArguments();
        Car car = (Car) bundle.get("carEdit");

        binding.brand.setText(car.getBrand());
        binding.model.setText(car.getModel());
        binding.price.setText(car.getPrice() + "");
        binding.imgSelector.setImageBitmap(BitmapFactory.decodeByteArray(car.getPhoto(), 0, car.getPhoto().length));

        bundle.clear();

        binding.imgSelector.setOnClickListener(view -> {
            MyCarEditFragment.this.content.launch("image/*");
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
                    ByteArrayOutputStream baos =new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
                    byte[] img = baos.toByteArray();
                    car.setPhoto(img);
                }
                car.setBrand(brand);
                car.setModel(model);
                car.setPrice(Integer.valueOf(strPrice));
                AppDatabase appDatabase = App.getAppDatabase(getContext());
                appDatabase.carDao().update(car);

                MyCarsFragment carsFragment = new MyCarsFragment();
                getFragmentManager()
                        .beginTransaction()
                        .disallowAddToBackStack()
                        .replace(getId(), carsFragment, "car list")
                        .commit();
            }
        });

        return binding.getRoot();
    }
}
