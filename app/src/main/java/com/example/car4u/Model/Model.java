package com.example.car4u.Model;

import com.example.car4u.MyApplication;

import java.util.LinkedList;
import java.util.List;

public class Model
{
    public final static Model instance = new Model();
    ModelFireBase modelFireBase = new ModelFireBase();
    private Model(){}

    public interface getAllCarsListener
    {
        void onComplete(List<Car> car_data);
    }

    public void getAllCars(getAllCarsListener listener)
    {
        modelFireBase.getAllCars(listener);
    }

    public interface getCarByOwnerListener
    {
        void onComplete(Car car);
    }

    public void getCarByOwner(String owner,getCarByOwnerListener listener)
    {
        modelFireBase.getCarByOwner(owner,listener);
    }

    public interface addCarListener
    {
        void onComplete();
    }

    public void addCar(Car car, addCarListener listener)
    {
        modelFireBase.addCar(car,listener);
    }

    public interface removeCarListener
    {
        void onComplete();
    }

    public void removeCar(Car car,removeCarListener listener)
    {
        modelFireBase.removeCar(car,listener);
    }

    public interface getAllUsersListener
    {
        void onComplete(List<User> user_data);
    }

    public void getAllUsers(getAllUsersListener listener)
    {
        modelFireBase.getAllUsers(listener);
    }

    public interface getUserByUsernameListener
    {
        void onComplete(User user);
    }

    public void getUserByUsername(String username,getUserByUsernameListener listener)
    {
        modelFireBase.getUserByUsername(username,listener);
    }

    public interface addUserListener
    {
        void onComplete();
    }

    public void addUser(User user, addUserListener listener)
    {
        modelFireBase.addUser(user,listener);
    }
}

