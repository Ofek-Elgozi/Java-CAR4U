package com.example.car4u.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Car implements Parcelable
{
    public String owner;
    public String model;
    public String year;
    public String price;
    public String location;
    public String phone;
    public String description;
    public String car_username;


    public Car()
    {
        owner=" ";
        model=" ";
        year=" ";
        price=" ";
        location=" ";
        phone=" ";
        description =" ";
        car_username =" ";
    }

    public Car(Car c)
    {
        this.owner=c.owner;
        this.model=c.model;
        this.year=c.year;
        this.price=c.price;
        this.location=c.location;
        this.phone=c.phone;
        this.description =c.description;
        this.car_username =c.car_username;
    }


    protected Car(Parcel in) {
        owner = in.readString();
        model = in.readString();
        year = in.readString();
        price = in.readString();
        location = in.readString();
        phone = in.readString();
        description = in.readString();
        car_username = in.readString();
    }

    public static final Creator<Car> CREATOR = new Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel in) {
            return new Car(in);
        }

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(owner);
        dest.writeString(model);
        dest.writeString(year);
        dest.writeString(price);
        dest.writeString(location);
        dest.writeString(phone);
        dest.writeString(description);
        dest.writeString(car_username);
    }
}
