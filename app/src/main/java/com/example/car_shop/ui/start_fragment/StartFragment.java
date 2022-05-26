package com.example.car_shop.ui.start_fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.example.car_shop.R;
import com.example.car_shop.databinding.FragmentMyCarsBinding;
import com.example.car_shop.databinding.FragmentStartBinding;
import com.example.car_shop.ui.registerOrLogin.login.LoginFragment;
import com.example.car_shop.ui.registerOrLogin.register.RegisterFragment;

public class StartFragment extends Fragment {

    private LottieAnimationView lotty;
    private FragmentStartBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStartBinding.inflate(inflater, container, false);
        lotty = binding.lotty;
        lotty.setAnimation(R.raw.porshe);

        binding.signup.setOnClickListener(view -> {
            RegisterFragment registerFragment = new RegisterFragment();
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .replace(this.getId(), registerFragment, "toLoginPage")
                    .addToBackStack(null)
                    .commit();
        });

        binding.signin.setOnClickListener(view -> {
            LoginFragment loginFragment = new LoginFragment();
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .replace(this.getId(), loginFragment, "toLoginPage")
                    .addToBackStack(null)
                    .commit();
        });

        return binding.getRoot();
    }

}