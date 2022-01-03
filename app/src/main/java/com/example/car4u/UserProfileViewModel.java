package com.example.car4u;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.car4u.Model.Car;
import com.example.car4u.Model.Model;
import com.example.car4u.Model.User;

import java.util.List;

public class UserProfileViewModel extends ViewModel
{
    LiveData<List<Car>> data;
    User user;
    public LiveData<List<Car>> getData()
    {
        return data;
    }

    public void setUser(User user)
    {
        this.user = user;
        data = Model.instance.getCarsByUserName(this.user.getUsername());
    }
}
