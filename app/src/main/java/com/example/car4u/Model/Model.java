package com.example.car4u.Model;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.car4u.MyApplication;

import java.util.List;

public class Model
{
    public final static Model instance = new Model();
    ModelFireBase modelFireBase = new ModelFireBase();
    private Model()
    {
        reloadCarList();
        reloadUserList();
    }

    public interface getAllCarsListener
    {
        void onComplete(List<Car> car_data);
    }

    MutableLiveData<List<Car>> carListLd = new MutableLiveData<List<Car>>();
    public void reloadCarList()
    {
        //1.get local last update
        Long localLastUpdate = Car.getLocalLastUpdated();
        //2.get all cars record since local last update from firebase
        modelFireBase.getAllCars(localLastUpdate, new getAllCarsListener()
        {
            @Override
            public void onComplete(List<Car> car_data)
            {
                //3.update local last update date
                //4.add new records to the local db
                MyApplication.executorService.execute(()->
                {
                    Long lLastUpdate = new Long(0);
                    Log.d("TAG", "FB returned " + car_data.size());
                    for(Car c: car_data)
                    {
                        if(c.isDeleted()==false)
                        {
                            AppLocalDB.db.carDao().insertAll(c);                        }
                        else
                        {
                            AppLocalDB.db.carDao().delete(c);
                        }
                        if(c.getLastUpdated() > lLastUpdate)
                        {
                            lLastUpdate=c.getLastUpdated();
                        }
                    }
                    Car.setLocalLastUpdated(lLastUpdate);
                    //5.return all records to the caller
                    List<Car> ctList = AppLocalDB.db.carDao().getAllCars();
                    carListLd.postValue(ctList);
                });
            }
        });
    }

    public MutableLiveData<List<Car>> getAllCarsData()
    {
        return carListLd;
    }


    public LiveData<List<Car>> getCarsByUserName(String username)
    {
        return AppLocalDB.db.carDao().getCarsByOwner(username);
    }

    public interface addCarListener
    {
        void onComplete();
    }

    public void addCar(Car car, addCarListener listener)
    {
        modelFireBase.addCar(car, new addCarListener()
        {
            @Override
            public void onComplete()
            {
                reloadCarList();
                listener.onComplete();
            }
        });
    }

    public interface removeCarListener
    {
        void onComplete();
    }

    public void removeCar(Car car,removeCarListener listener)
    {
        car.setDeleted(true);
        addCar(car, new addCarListener()
        {
            @Override
            public void onComplete()
            {
                listener.onComplete();
            }
        });
    }

    public interface getAllUsersListener
    {
        void onComplete(List<User> user_data);
    }

    public void reloadUserList()
    {
        //1.get local last update
        Long localLastUpdate = User.getLocalLastUpdated();
        //2.get all cars record since local last update from firebase
        modelFireBase.getAllUsers(localLastUpdate, new getAllUsersListener()
        {
            @Override
            public void onComplete(List<User> user_data)
            {
                //3.update local last update date
                //4.add new records to the local db
                MyApplication.executorService.execute(()->
                {
                    Long lLastUpdate = new Long(0);
                    Log.d("TAG", "FB returned " + user_data.size());
                    for(User u: user_data)
                    {
                        AppLocalDB.db.userDao().insertAll(u);
                        if(u.getLastUpdated() > lLastUpdate)
                        {
                            lLastUpdate=u.getLastUpdated();
                        }
                    }
                    User.setLocalLastUpdated(lLastUpdate);
                });
            }
        });
    }

    public interface getUserByEmailListener
    {
        void onComplete(User user);
    }

    public void getUserByEmail(String email, getUserByEmailListener listener)
    {
        modelFireBase.getUserByEmail(email,listener);
    }

    public interface addUserListener
    {
        void onComplete();
    }

    public void addUser(User user, addUserListener listener)
    {
        modelFireBase.addUser(user, new addUserListener()
        {
            @Override
            public void onComplete()
            {
                reloadUserList();
                listener.onComplete();
            }
        });
    }

    public interface SaveImageListener
    {
        void onComplete(String url);
    }

    public void saveImage(Bitmap bitmap, String Car_username, SaveImageListener listener)
    {
        modelFireBase.saveImage(bitmap,Car_username, listener);
    }
}

