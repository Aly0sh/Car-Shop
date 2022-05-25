package com.example.car_shop.ui.mycars;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.car_shop.data.enums.Status;
import com.example.car_shop.data.models.Car;
import com.example.car_shop.databinding.CarItemLayoutBinding;
import com.example.car_shop.databinding.MyCarItemLayoutBinding;
import com.example.car_shop.ui.cars.CarPageFragment;
import com.example.car_shop.ui.cars.CarsFragment;

import java.util.ArrayList;
import java.util.List;

public class MyCarsAdapter extends RecyclerView.Adapter<MyCarsAdapter.CarHolder>{
    private List<Car> cars = new ArrayList<>();
    private MyCarsFragment myCarsFragment;

    public void setList(List<Car> cars){
        this.cars = cars;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyCarsAdapter.CarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyCarItemLayoutBinding carItemLayoutBinding = MyCarItemLayoutBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        MyCarsAdapter.CarHolder carHolder = new MyCarsAdapter.CarHolder(carItemLayoutBinding);
        return carHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyCarsAdapter.CarHolder holder, int position) {
        Car car = cars.get(position);
        holder.binding.brand.setText("Марка: " + car.getBrand());
        holder.binding.model.setText("Модель: " + car.getModel());
        holder.binding.price.setText("Цена: " + car.getPrice() + " сом");
        holder.binding.status.setText("Статус: " + ((car.getStatus() == Status.SALE)?"В продаже":"Продано"));

        holder.binding.carImg.setImageBitmap(BitmapFactory.decodeByteArray(car.getPhoto(), 0, car.getPhoto().length));
        holder.itemView.setOnClickListener(v -> {
            CarPageFragment carPageFragment = new CarPageFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("car", car);
            carPageFragment.setArguments(bundle);

            myCarsFragment.getFragmentManager()
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(myCarsFragment.getId(), carPageFragment, "carPageFragment")
                    .addToBackStack(null)
                    .commit();

        });
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    public class CarHolder extends RecyclerView.ViewHolder {
        private MyCarItemLayoutBinding binding;

        public CarHolder(@NonNull MyCarItemLayoutBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }

    public void setMyCarsFragment(MyCarsFragment myCarsFragment){
        this.myCarsFragment = myCarsFragment;
    }
}
