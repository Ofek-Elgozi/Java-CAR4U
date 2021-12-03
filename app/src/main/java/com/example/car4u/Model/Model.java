package com.example.car4u.Model;

import java.util.LinkedList;
import java.util.List;

public class Model
{
    public final static Model instance = new Model();
    private Model(){}
    List<Car> data = new LinkedList<Car>();

    public List<Car> getAllCars()
    {
        return data;
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

    public void addCar(Car car)
    {
        data.add(car);
    }

    public void removeCar(Car car)
    {
        data.remove(car);
    }
}

