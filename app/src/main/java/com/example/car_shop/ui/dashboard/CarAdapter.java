package com.example.car_shop.ui.dashboard;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.car_shop.data.models.Car;
import com.example.car_shop.databinding.CarItemLayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarHolder> {
    private List<Car> cars = new ArrayList<>();

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
}
