package com.example.car4u.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable
{
    public String username;
    public String password;
    public String email;
    public String phone;

    public User()
    {
        username=" ";
        password=" ";
        email=" ";
        phone=" ";
    }
    public User(User u)
    {
        this.username=u.username;
        this.password=u.password;
        this.email=u.email;
        this.phone=u.phone;
    }


    protected User(Parcel in) {
        username = in.readString();
        password = in.readString();
        email = in.readString();
        phone = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

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
    }
}
