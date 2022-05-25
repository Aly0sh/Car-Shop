package com.example.car_shop;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.car_shop.databinding.ActivityRegisterLoginBinding;
import com.example.car_shop.ui.registerOrLogin.login.LoginFragment;
import com.example.car_shop.userService.UserSingl;


public class RegisterLoginActivity extends AppCompatActivity {
    private ActivityRegisterLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterLoginBinding.inflate(getLayoutInflater());

        setContentView(R.layout.activity_register_login);

        if (UserSingl.getUserSingln() == null){
            LoginFragment loginFragment = new LoginFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.registerAndLogin, loginFragment, "register fragment")
                    .addToBackStack(null)
                    .commit();
        }
    }
}