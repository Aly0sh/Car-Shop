package com.example.car_shop.ui.add_car;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.car_shop.R;
import com.example.car_shop.databinding.FragmentAddCarBinding;
import com.example.car_shop.databinding.FragmentDashboardBinding;

public class AddCar extends Fragment {
    private FragmentAddCarBinding binding;
    private int SELECT_PICTURE = 200;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddCarBinding.inflate(inflater, container, false);

        binding.imgSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });

        return binding.getRoot();
    }

    private void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                final Bundle extras = data.getExtras();
                System.out.println(extras);
                if (extras != null) {
                    //Get image
                    Bitmap carImage = extras.getParcelable("data");
                    binding.imgSelector.setImageBitmap(carImage);
                }
            }
        }
    }

}