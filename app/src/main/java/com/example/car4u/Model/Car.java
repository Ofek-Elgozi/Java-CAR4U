package com.example.car4u.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.car4u.MyApplication;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Car implements Parcelable
{
    public final static String LAST_UPDATED="LAST_UPDATED";
    @PrimaryKey
    @NonNull
    public String id_key=null;
    public String owner;
    public String model;
    public String year;
    public String price;
    public String location;
    public String car_username;
    public String phone;
    public String description;
    public String avatarUrl;
    boolean deleted;
    Long lastUpdated= new Long(0);

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
        this.deleted=c.deleted;
    }

    public Car(String owner, String model,String year,String price,String location,String phone,String description,String car_username)
    {
        this.owner=owner;
        this.model=model;
        this.year=year;
        this.price=price;
        this.location=location;
        this.phone=phone;
        this.description =description;
        this.car_username =car_username;
        this.deleted=false;
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
        deleted=false;
    }

    @NonNull
    public String getOwner() {
        return owner;
    }

    public String getModel() {
        return model;
    }

    public String getAvatarUrl()
    {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl)
    {
        this.avatarUrl = avatarUrl;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getId_key() {return id_key;}

    public void setId_key(@NonNull String id_key) {
        this.id_key = id_key;
    }

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

    public Long getLastUpdated()
    {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public Map<String,Object> toJson()
    {
        Map<String, Object> json = new HashMap<>();
        json.put("owner", getOwner());
        json.put("model", getModel());
        json.put("year", getYear());
        json.put("price", getPrice());
        json.put("location", getLocation());
        json.put("phone", getPhone());
        json.put("description", getDescription());
        json.put("car_username", getCar_username());
        json.put("deleted", isDeleted());
        json.put("avatarUrl", getAvatarUrl());
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());
        return json;
    }

    static Car fromJson(Map<String, Object> json)
    {
        String owner=(String)json.get("owner");
        String model=(String)json.get("model");
        String year=(String)json.get("year");
        String price=(String)json.get("price");
        String location=(String)json.get("location");
        String avatarUrl=(String)json.get("avatarUrl");
        String phone=(String)json.get("phone");
        String description=(String)json.get("description");
        String car_username=(String)json.get("car_username");
        boolean deleted=(boolean)json.get("deleted");
        Car car = new Car(owner,model,year,price,location,phone,description,car_username);
        car.setDeleted(deleted);
        car.setAvatarUrl(avatarUrl);
        Timestamp ts = (Timestamp)json.get(LAST_UPDATED);
        car.setLastUpdated(new Long(ts.getSeconds()));
        return car;
    }

    static long getLocalLastUpdated()
    {
        Long localLastUpdate = MyApplication.getContext().getSharedPreferences("TAG", Context.MODE_PRIVATE).getLong("CARS_LAST_UPDATE", 0 );
        Log.d("TAG","localLastUpdate: " + localLastUpdate);
        return localLastUpdate;
    }

    static void setLocalLastUpdated(Long date)
    {
        SharedPreferences.Editor editor = MyApplication.getContext().getSharedPreferences("TAG", Context.MODE_PRIVATE).edit();
        editor.putLong("CARS_LAST_UPDATE",date);
        editor.commit();
        Log.d("TAG", "new lud" + date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id_key);
        dest.writeString(owner);
        dest.writeString(model);
        dest.writeString(year);
        dest.writeString(price);
        dest.writeString(location);
        dest.writeString(car_username);
        dest.writeString(phone);
        dest.writeString(description);
        dest.writeString(avatarUrl);
        dest.writeByte((byte) (deleted ? 1 : 0));
        if (lastUpdated == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(lastUpdated);
        }
    }

    protected Car(Parcel in)
    {
        id_key = in.readString();
        owner = in.readString();
        model = in.readString();
        year = in.readString();
        price = in.readString();
        location = in.readString();
        car_username = in.readString();
        phone = in.readString();
        description = in.readString();
        avatarUrl = in.readString();
        deleted = in.readByte() != 0;
        if (in.readByte() == 0) {
            lastUpdated = null;
        } else {
            lastUpdated = in.readLong();
        }
    }

    public static final Creator<Car> CREATOR = new Creator<Car>()
    {
        @Override
        public Car createFromParcel(Parcel in)
        {
            return new Car(in);
        }

        @Override
        public Car[] newArray(int size)
        {
            return new Car[size];
        }
    };
}
