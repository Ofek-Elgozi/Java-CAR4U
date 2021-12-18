package com.example.car4u.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.Map;

@Entity
public class User implements Parcelable
{
    @PrimaryKey
    @NonNull
    public String username;
    public String password;
    public String email;
    public String phone;

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

    public User(String username, String password, String email,String phone)
    {
        this.username=username;
        this.password=password;
        this.email=email;
        this.phone=phone;
    }

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

    public Map<String,Object> toJson()
    {
        Map<String, Object> json = new HashMap<>();
        json.put("username", getUsername());
        json.put("password", getPassword());
        json.put("email", getEmail());
        json.put("phone", getPhone());
        return json;
    }

    static User fromJson(Map<String, Object> json)
    {
        String username=(String)json.get("username");
        if(username==null)
            return null;
        String password=(String)json.get("password");
        String email=(String)json.get("address");
        String phone=(String)json.get("phone");
        User user = new User(username,password,email,phone);
        return user;
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
    }
}
