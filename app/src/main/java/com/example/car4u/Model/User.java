package com.example.car4u.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User implements Parcelable
{
    @PrimaryKey
    @NonNull
    public String username;
    public String password;
    public String email;
    public String phone;
    public int car_amount;

    protected User(Parcel in)
    {
        username = in.readString();
        password = in.readString();
        email = in.readString();
        phone = in.readString();
        car_amount = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>()
    {
        @Override
        public User createFromParcel(Parcel in)
        {
            return new User(in);
        }

        @Override
        public User[] newArray(int size)
        {
            return new User[size];
        }
    };

    @NonNull
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public int getCar_amount() {
        return car_amount;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCar_amount(int car_amount) {
        this.car_amount = car_amount;
    }

    public static Creator<User> getCREATOR() {
        return CREATOR;
    }

    public User()
    {
        username=" ";
        password=" ";
        email=" ";
        phone=" ";
        car_amount=0;
    }
    public User(User u)
    {
        this.username=u.username;
        this.password=u.password;
        this.email=u.email;
        this.phone=u.phone;
        this.car_amount=u.car_amount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(email);
        dest.writeString(phone);
        dest.writeInt(car_amount);
    }
}
