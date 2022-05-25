package com.example.car_shop.ui.mycars;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.car_shop.R;
import com.example.car_shop.data.App;
import com.example.car_shop.data.dao.CarDao;
import com.example.car_shop.data.dao.CartDao;
import com.example.car_shop.data.enums.Status;
import com.example.car_shop.data.enums.UserRoles;
import com.example.car_shop.data.models.Car;
import com.example.car_shop.databinding.MyCarItemLayoutBinding;
import com.example.car_shop.ui.cars.CarPageFragment;
import com.example.car_shop.userService.UserSingl;

import java.util.ArrayList;
import java.util.List;

public class MyCarsAdapter extends RecyclerView.Adapter<MyCarsAdapter.CarHolder>{
    private List<Car> cars = new ArrayList<>();
    private MyCarsFragment myCarsFragment;
    private CartDao cartDao;
    private CarDao carDao;

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
        cartDao = App.getAppDatabase(parent.getContext()).cartDao();
        carDao = App.getAppDatabase(parent.getContext()).carDao();
        return carHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyCarsAdapter.CarHolder holder, int position) {
        Car car = cars.get(position);
        holder.binding.brand.setText(car.getBrand());
        holder.binding.model.setText("Модель: " + car.getModel());
        holder.binding.price.setText("Цена: " + car.getPrice() + " сом");
        holder.binding.status.setText("Статус: " + ((car.getStatus() == Status.SALE)?"В продаже":"Продано"));

        holder.binding.carImg.setImageBitmap(BitmapFactory.decodeByteArray(car.getPhoto(), 0, car.getPhoto().length));
        holder.binding.dropdownMenu.setOnClickListener(v -> {
            if (UserSingl.getUserSingln().getUserRole() == UserRoles.SELLER){
                PopupMenu popupMenu = new PopupMenu(holder.binding.getRoot().getContext(), holder.binding.dropdownMenu);
                popupMenu.getMenuInflater().inflate(R.menu.seller_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getTitle().toString()){
                            case "Редактировать":
                                return true;
                            case "Продано":
                                carDao.updateStatus(Status.SOLD, car.getId());
                                holder.binding.status.setText("Продано");
                                return true;
                            case "На продажу":
                                carDao.updateStatus(Status.SALE, car.getId());
                                holder.binding.status.setText("В продаже");
                                return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
            else if (UserSingl.getUserSingln().getUserRole() == UserRoles.CLIENT){
                PopupMenu popupMenu = new PopupMenu(holder.binding.getRoot().getContext(), holder.binding.dropdownMenu);
                popupMenu.getMenuInflater().inflate(R.menu.client_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getTitle().toString()){
                            case "Позвонить владельцу":
                                holder.binding.getRoot().getContext().startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + car.getSellerPhone())));
                                return true;
                            case "Удалить понравившееся":
                                cartDao.delete(cartDao.getByCarId(car.getId(), UserSingl.getUserSingln().getUserId()));
                                cars.remove(holder.getPosition());
                                MyCarsFragment.updateRecycler();
                                return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }

        });
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
