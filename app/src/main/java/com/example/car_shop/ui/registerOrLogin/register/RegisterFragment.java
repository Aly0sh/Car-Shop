package com.example.car_shop.ui.registerOrLogin.register;

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

import com.example.car_shop.data.App;
import com.example.car_shop.data.models.User;
import com.example.car_shop.data.room.AppDatabase;
import com.example.car_shop.databinding.RegisterFragmentBinding;
import com.example.car_shop.ui.registerOrLogin.login.LoginFragment;

public class RegisterFragment extends Fragment {

    private RegisterViewModel mViewModel;

    private RegisterFragmentBinding binding;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = RegisterFragmentBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        mViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        mViewModel.setDatabase(App.getAppDatabase(getContext()));

        onClick();

        return root;
    }


    private void onClick(){
        binding.register.setOnClickListener(view -> {
            String username = binding.username.getText().toString();
            String password = binding.password.getText().toString();
            String phone = binding.phone.getText().toString();
            if (username.isEmpty() || phone.isEmpty() || password.isEmpty()){
                Toast.makeText(binding.getRoot().getContext(), "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show();
            }
            else {
                if (password.length() < 6){
                    Toast.makeText(binding.getRoot().getContext(), "Пароль должен содержать не менее 6 символов", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(binding.getRoot().getContext(), "Вы зарегистрированы", Toast.LENGTH_LONG).show();
                    mViewModel.register(new User(username, phone, password, binding.role.getSelectedItem().toString()));
                    toLoginPage();
                }
            }
        });

        binding.toLoginPage.setOnClickListener(view -> {
            toLoginPage();
        });
    }
    
    private void toLoginPage(){
        LoginFragment loginFragment = new LoginFragment();
        getFragmentManager()
                .beginTransaction()
                .replace(this.getId(), loginFragment, "toLoginPage")
                .addToBackStack(null)
                .commit();
    }

}