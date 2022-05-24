package com.example.car_shop.ui.registerOrLogin.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.example.car_shop.MainActivity;
import com.example.car_shop.data.room.AppDatabase;
import com.example.car_shop.databinding.LoginFragmentBinding;
import com.example.car_shop.ui.registerOrLogin.register.RegisterFragment;

public class LoginFragment extends Fragment {

    private LoginViewModel mViewModel;

    private LoginFragmentBinding binding;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = LoginFragmentBinding.inflate(getLayoutInflater());

        View root = binding.getRoot();

        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        mViewModel.setDatabase(Room.databaseBuilder(binding.getRoot().getContext(), AppDatabase.class, "database").allowMainThreadQueries().build());
        onClick();

        return root;
    }

    private void onClick(){
        binding.login.setOnClickListener(view -> {
            String username = binding.username.getText().toString();
            String password = binding.password.getText().toString();
            if (username.isEmpty() || password.isEmpty()){
                Toast.makeText(binding.getRoot().getContext(), "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show();
            }
            else {
                if(mViewModel.checkUser(username, password))
                {
                    Intent intent = new Intent(requireActivity(), MainActivity.class);
                    Toast.makeText(binding.getRoot().getContext(), "Вы вошли в аккаунт", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                } else {
                    Toast.makeText(binding.getRoot().getContext(), "Неправильный логин или пароль!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.toRegisterPage.setOnClickListener(view -> {
            RegisterFragment registerFragment = new RegisterFragment();
            getFragmentManager()
                    .beginTransaction()
                    .replace(this.getId(), registerFragment, "toLoginPage")
                    .addToBackStack(null)
                    .commit();
        });
    }

}