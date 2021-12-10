package com.example.car4u.Model;

import com.example.car4u.MyApplication;

import java.util.LinkedList;
import java.util.List;

public class Model
{
    public final static Model instance = new Model();
    private Model(){}

    public interface getAllCarsListener
    {
        void onComplete(List<Car> car_data);
    }

    public void getAllCars(getAllCarsListener listener)
    {
        MyApplication.executorService.execute(()->
        {
            List<Car> car_data = AppLocalDB.db.carDao().getAllCars();
            MyApplication.mainHandler.post(()->
            {
                listener.onComplete(car_data);
            });
        });
    }

    public interface getCarByOwnerListener
    {
        void onComplete(Car car);
    }

    public void getCarByOwner(String owner,getCarByOwnerListener listener)
    {
        MyApplication.executorService.execute(()->
        {
            Car car = AppLocalDB.db.carDao().getCarByOwner(owner);
            MyApplication.mainHandler.post(()->
            {
                listener.onComplete(car);
            });
        });
    }

    public interface addCarListener
    {
        void onComplete();
    }

    public void addCar(Car car, addCarListener listener)
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
            AppLocalDB.db.carDao().insertAllCars(car);
            MyApplication.mainHandler.post(()->
            {
                listener.onComplete();
            });
        });
    }

    public interface removeCarListener
    {
        void onComplete();
    }

    public void removeCar(Car car,removeCarListener listener)
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
            AppLocalDB.db.carDao().delete(car);
            MyApplication.mainHandler.post(()->
            {
                listener.onComplete();
            });
        });
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

