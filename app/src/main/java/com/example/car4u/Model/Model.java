package com.example.car4u.Model;

import com.example.car4u.MyApplication;

import java.util.LinkedList;
import java.util.List;

public class Model
{
    public final static Model instance = new Model();
    private Model(){}

    List<Car> car_data = new LinkedList<Car>();

    public List<Car> getAllCars()
    {
        return car_data;
    }

    public Car getCarByOwner(String owner)
    {
        for(Car c:car_data)
        {
            if(c.owner.equals(owner))
            {
                return c;
            }
        }
        return null;
    }

    public void addCar(Car car)
    {
        car_data.add(car);
    }

    public void removeCar(Car car)
    {
        car_data.remove(car);
    }

    public interface getAllUsersListener
    {
        void onComplete(List<User> user_data);
    }

    public void getAllUsers(getAllUsersListener listener)
    {
        MyApplication.executorService.execute(()->
        {
            List<User> user_data = AppLocalDB.db.userDao().getAllUsers();
            MyApplication.mainHandler.post(()->
            {
                listener.onComplete(user_data);
            });
        });
    }

    public interface getUserByUsernameListener
    {
        void onComplete(User user);
    }

    public void getUserByUsername(String username,getUserByUsernameListener listener)
    {
        MyApplication.executorService.execute(()->
        {
            User user = AppLocalDB.db.userDao().getUserByUsername(username);
            MyApplication.mainHandler.post(()->
            {
                listener.onComplete(user);
            });
        });
    }

    public interface addUserListener
    {
        void onComplete();
    }

    public void addUser(User user, addUserListener listener)
    {
        MyApplication.executorService.execute(()->
        {
            try
            {
                Thread.sleep(3000);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            AppLocalDB.db.userDao().insertAll(user);
            MyApplication.mainHandler.post(()->
            {
                listener.onComplete();
            });
        });
    }
}

