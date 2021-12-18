package com.example.car4u.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Car implements Parcelable
{
    @PrimaryKey
    @NonNull
    public int id_key;
    public String owner;
    public String model;
    public String year;
    public String price;
    public String location;
    public String phone;
    public String description;
    public String car_username;
    public static int counter=0;

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

    public Car(String owner, String model,String year,String price,String location,String phone,String description,String car_username,int id_key)
    {
        this.owner=owner;
        this.model=model;
        this.year=year;
        this.price=price;
        this.location=location;
        this.phone=phone;
        this.description =description;
        this.car_username =car_username;
        this.id_key=id_key;
    }

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
        counter++;
        id_key=counter;
    }

    protected Car(Parcel in)
    {
        id_key = in.readInt();
        owner = in.readString();
        model = in.readString();
        year = in.readString();
        price = in.readString();
        location = in.readString();
        phone = in.readString();
        description = in.readString();
        car_username = in.readString();
    }

    public static final Creator<Car> CREATOR = new Creator<Car>()
    {
        @Override
        public Car createFromParcel(Parcel in) {
            return new Car(in);
        }

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }
    };

    @NonNull
    public String getOwner() {
        return owner;
    }

    public String getModel() {
        return model;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getId_key() {return id_key;}

    public void setModel(String model)
    {
        this.model = model;
    }

    public void setYear(String year)
    {
        this.year = year;
    }

    public void setPrice(String price)
    {
        this.price = price;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setCar_username(String car_username)
    {
        this.car_username = car_username;
    }

    public String getYear()
    {
        return year;
    }

    public String getPrice()
    {
        return price;
    }

    public String getLocation()
    {
        return location;
    }

    public String getPhone()
    {
        return phone;
    }

    public String getDescription()
    {
        return description;
    }

    public String getCar_username()
    {
        return car_username;
    }

    public Map<String,Object> toJson()
    {
        Map<String, Object> json = new HashMap<>();
        json.put("id_key", getId_key());
        json.put("owner", getOwner());
        json.put("model", getModel());
        json.put("year", getYear());
        json.put("price", getPrice());
        json.put("location", getLocation());
        json.put("phone", getPhone());
        json.put("description", getDescription());
        json.put("car_username", getCar_username());
        return json;
    }

    static Car fromJson(Map<String, Object> json)
    {
        int id_key=Integer.parseInt(String.valueOf(json.get("id_key")));
        if(id_key==0)
            return null;
        String owner=(String)json.get("owner");
        String model=(String)json.get("model");
        String year=(String)json.get("year");
        String price=(String)json.get("price");
        String location=(String)json.get("location");
        String phone=(String)json.get("phone");
        String description=(String)json.get("description");
        String car_username=(String)json.get("car_username");
        Car car = new Car(owner,model,year,price,location,phone,description,car_username,id_key);
        return car;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(id_key);
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
