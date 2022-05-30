package com.example.car_shop.ui.mycars;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

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
        if (car.getStatus() == Status.SOLD) {
            holder.binding.mycart.setForeground(new ColorDrawable(Color.parseColor("#4D000000")));
        }
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
                                MyCarEditFragment myCarEditFragment = new MyCarEditFragment();
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("carEdit", car);
                                myCarEditFragment.setArguments(bundle);

                                myCarsFragment.getFragmentManager()
                                        .beginTransaction()
                                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                        .replace(myCarsFragment.getId(),myCarEditFragment)
                                        .addToBackStack(null)
                                        .commit();
                                return true;
                            case "Продано":
                                carDao.updateStatus(Status.SOLD, car.getId());
                                holder.binding.status.setText("Продано");
                                holder.binding.mycart.setForeground(new ColorDrawable(Color.parseColor("#4D000000")));
                                return true;
                            case "На продажу":
                                carDao.updateStatus(Status.SALE, car.getId());
                                holder.binding.status.setText("В продаже");
                                holder.binding.mycart.setForeground(null);
                                return true;
                            case "Удалить":
                                AlertDialog alertDialog = new AlertDialog.Builder(holder.binding.getRoot().getContext()).create();
                                alertDialog.setTitle("Удаление машины из списка");
                                alertDialog.setMessage("Вы действительно хотите удалить машину из списка?");
                                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Удалить",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                cartDao.deleteByCarId(car.getId());
                                                carDao.delete(car);
                                                notifyDataSetChanged();
                                                cars.remove(holder.getPosition());
                                                MyCarsFragment.updateRecycler();
                                                dialog.dismiss();
                                            }
                                        });

                                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Отмена",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.show();
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
                                if (car.getStatus() == Status.SOLD){
                                    Toast.makeText(holder.binding.getRoot().getContext(), "Машина уже продана вы не можете позвонить владельцу!", Toast.LENGTH_SHORT).show();
                                } else {
                                    holder.binding.getRoot().getContext().startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + car.getSellerPhone())));
                                }
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
                    .replace(myCarsFragment.getId(), carPageFragment)
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
