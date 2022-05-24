package com.example.car_shop.userService;

import com.example.car_shop.data.enums.UserRoles;
import com.example.car_shop.data.models.User;

public class UserSingl {
    private long userId;
    private String username;
    private String phone;
    private UserRoles role;

    private static UserSingl userSingln;

    public static void setUser(User user) {
        userSingln = new UserSingl();
        userSingln.setUserId(user.getId());
        userSingln.setUsername(user.getUsername());
        userSingln.setPhone(user.getPhone());
        userSingln.setRole(user.getRole());
    }

    public static UserSingl getUserSingln(){
        return userSingln;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role.name()  ;
    }

    public void setRole(UserRoles role) {
        this.role = role;
    }
}
