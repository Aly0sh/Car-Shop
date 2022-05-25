package com.example.car_shop.ui.cars;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.car_shop.R;
import com.example.car_shop.data.App;
import com.example.car_shop.data.dao.CartDao;
import com.example.car_shop.data.enums.UserRoles;
import com.example.car_shop.data.models.Car;
import com.example.car_shop.data.models.Cart;
import com.example.car_shop.databinding.CarItemLayoutBinding;
import com.example.car_shop.userService.UserSingl;

import java.util.ArrayList;
import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarHolder> {
    private List<Car> cars = new ArrayList<>();
    private CarsFragment carsFragment;
    private CartDao cartDao;

    public void setList(List<Car> cars){
        this.cars = cars;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CarAdapter.CarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CarItemLayoutBinding carItemLayoutBinding = CarItemLayoutBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        CarHolder carHolder = new CarHolder(carItemLayoutBinding);
        return carHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CarAdapter.CarHolder holder, int position) {
        Car car = cars.get(position);
        holder.binding.brand.setText("Марка: " + car.getBrand());
        holder.binding.model.setText("Модель: " + car.getModel());
        holder.binding.price.setText("Цена: " + car.getPrice() + " сом");
        holder.binding.carImg.setImageBitmap(BitmapFactory.decodeByteArray(car.getPhoto(), 0, car.getPhoto().length));
        holder.itemView.setOnClickListener(v -> {
            CarPageFragment carPageFragment = new CarPageFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("car", car);
            carPageFragment.setArguments(bundle);

            carsFragment.getFragmentManager()
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(carsFragment.getId(), carPageFragment, "carPageFragment")
                    .addToBackStack(null)
                    .commit();

        });
        cartDao = App.getAppDatabase(holder.binding.getRoot().getContext()).cartDao();
        if (UserSingl.getUserSingln().getUserRole() == UserRoles.CLIENT){
            if (check(car)){
                holder.binding.like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holder.binding.like.setImageDrawable(holder.binding.getRoot().getResources().getDrawable(R.drawable.ic_baseline_favorite_24));
                        cartDao.insert(new Cart(UserSingl.getUserSingln().getUserId(), car.getId()));
                    }
                });
            } else {
                holder.binding.like.setImageDrawable(holder.binding.getRoot().getResources().getDrawable(R.drawable.ic_baseline_favorite_24));
            }
        } else {
            holder.binding.like.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    public class CarHolder extends RecyclerView.ViewHolder {
        private CarItemLayoutBinding binding;

        public CarHolder(@NonNull CarItemLayoutBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }

    public boolean check(Car car){
        for (Cart i:cartDao.getByCarId(car.getId(), UserSingl.getUserSingln().getUserId())){
            if (i.getCarId() == car.getId()){
                return false;
            }
        }
        return true;
    }

    public void setCarsFragment(CarsFragment carsFragment){
        this.carsFragment = carsFragment;
    }
}
