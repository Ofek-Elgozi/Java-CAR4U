package com.example.car4u.Model;

import java.util.LinkedList;
import java.util.List;

public class Model
{
    public final static Model instance = new Model();
    private Model(){}
    List<Car> data = new LinkedList<Car>();
    List<User> user_data = new LinkedList<User>();

    public List<Car> getAllCars()
    {
        return data;
    }
    public List<User> getAllUsers()
    {
        return user_data;
    }

    public Car getCarByOwner(String owner)
    {
        for(Car c:data)
        {
            if(c.owner.equals(owner))
            {
                return c;
            }
        }
        return null;
    }

    public boolean UserIsExist(String username, String password)
    {
        for(User u:user_data)
        {
            if(u.username.equals(username) && u.password.equals(password))
            {
                return true;
            }
        }
        return false;
    }

    public User getUserByUsername(String username)
    {
        for(User u:user_data)
        {
            if(u.username.equals(username))
            {
                return u;
            }
        }
        return null;
    }

    public void addCar(Car car)
    {
        data.add(car);
    }

    public void removeCar(Car car)
    {
        data.remove(car);
    }

    public void addUser(User user)
    {
        user_data.add(user);
    }

}

