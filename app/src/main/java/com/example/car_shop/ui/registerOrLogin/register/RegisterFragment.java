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

        onClick();

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        // TODO: Use the ViewModel
    }

    private void onClick(){
        binding.register.setOnClickListener(view -> {
            String name = binding.name.getText().toString();
            String phone = binding.phone.getText().toString();
            String password = binding.password.getText().toString();
            if (name.isEmpty() || phone.isEmpty() || password.isEmpty()){
                Toast.makeText(binding.getRoot().getContext(), "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show();
            }
            else {
                if (password.length() < 6){
                    Toast.makeText(binding.getRoot().getContext(), "Пароль должен содержать не менее 6 символов", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(binding.getRoot().getContext(), "Вы зарегестрированы", Toast.LENGTH_SHORT).show();
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