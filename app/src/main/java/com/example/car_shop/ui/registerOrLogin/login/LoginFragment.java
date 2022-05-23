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

import com.example.car_shop.MainActivity;
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

        onClick();

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        // TODO: Use the ViewModel
    }

    private void onClick(){
        binding.login.setOnClickListener(view -> {
            String phone = binding.phone.getText().toString();
            String password = binding.password.getText().toString();
            if (phone.isEmpty() || password.isEmpty()){
                Toast.makeText(binding.getRoot().getContext(), "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show();
            }
            else {
                Intent intent = new Intent(requireActivity(), MainActivity.class);
                Toast.makeText(binding.getRoot().getContext(), "Вы вошли в аккаунт", Toast.LENGTH_SHORT).show();
                startActivity(intent);
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