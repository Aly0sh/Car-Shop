package com.example.car_shop;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.car_shop.data.enums.UserRoles;
import com.example.car_shop.userService.UserSingl;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.car_shop.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        Menu navigation = navView.getMenu();
        MenuItem item = navigation.findItem(R.id.navigation_notifications);
        if(UserSingl.getUserSingln().getUserRole() == UserRoles.CLIENT){
            item.setTitle(getString(R.string.client));
        } else if (UserSingl.getUserSingln().getUserRole() == UserRoles.SELLER){
            item.setTitle(getString(R.string.seller));
        }
    }



}