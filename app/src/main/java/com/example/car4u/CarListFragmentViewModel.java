package com.example.car4u;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.car4u.Model.Car;
import com.example.car4u.Model.Model;

import java.util.LinkedList;
import java.util.List;

public class CarListFragmentViewModel extends ViewModel
{
    LiveData<List<Car>> data = Model.instance.getAllCarsData();

    public LiveData<List<Car>> getData()
    {
        return data;
    }
}
