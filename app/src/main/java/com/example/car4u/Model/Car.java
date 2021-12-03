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
    public String Description;


    public Car()
    {
        owner=" ";
        model=" ";
        year=" ";
        price=" ";
        location=" ";
        phone=" ";
        Description=" ";
    }

    public Car(Car s)
    {
        this.owner=s.owner;
        this.model=s.model;
        this.year=s.year;
        this.price=s.price;
        this.location=s.location;
        this.phone=s.phone;
        this.Description=s.Description;
    }

    protected Car(Parcel in) {
        owner = in.readString();
        model = in.readString();
        year = in.readString();
        price = in.readString();
        location = in.readString();
        phone = in.readString();
        Description = in.readString();
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
        dest.writeString(Description);
    }
}
