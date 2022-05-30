package com.example.car_shop.ui.cars;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

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
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarHolder> implements Filterable {
    private List<Car> cars = new ArrayList<>();
    private List<Car> allCars;
    private CarsFragment carsFragment;
    private CartDao cartDao;

    public void setList(List<Car> cars){
        this.cars = cars;
        this.allCars = new ArrayList<>(cars);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CarAdapter.CarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CarItemLayoutBinding carItemLayoutBinding = CarItemLayoutBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        CarHolder carHolder = new CarHolder(carItemLayoutBinding);
        cartDao = App.getAppDatabase(parent.getContext()).cartDao();
        return carHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CarAdapter.CarHolder holder, int position) {
        Car car = cars.get(position);
        holder.binding.brand.setText("Марка: " + car.getBrand());
        holder.binding.model.setText("Модель: " + car.getModel());
        holder.binding.price.setText("Цена: " + car.getPrice() + "$");
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
        if (UserSingl.getUserSingln().getUserRole() == UserRoles.CLIENT){
            if (check(car)){
                holder.binding.like.setImageDrawable(holder.binding.getRoot().getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24));
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

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Car> filteredCars = new ArrayList<>();

            if (constraint.toString().isEmpty()){
                filteredCars.addAll(allCars);
            }
            else {
                String text = constraint.toString().toLowerCase();
                for (Car car : allCars){
                    String carBrandAndModel = car.getBrand() + " " + car.getModel();
                    if (carBrandAndModel.toLowerCase().contains(text)) {
                        filteredCars.add(car);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredCars;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            cars.clear();
            cars.addAll((Collection<? extends Car>) results.values);
            notifyDataSetChanged();
        }
    };

    @Override
    public Filter getFilter() {
        return filter;
    }

    public boolean check(Car car) {
        if (cartDao.getByCarId(car.getId(), UserSingl.getUserSingln().getUserId()) != null){
            if (cartDao.getByCarId(car.getId(), UserSingl.getUserSingln().getUserId()).getCarId() == car.getId()) {
                return false;
            }
        }
        return true;
    }

    public void setCarsFragment(CarsFragment carsFragment){
        this.carsFragment = carsFragment;
    }
}
