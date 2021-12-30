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
public class User implements Parcelable
{
    public final static String LAST_UPDATED="LAST_UPDATED";
    @PrimaryKey
    @NonNull
    public String username;
    public String password;
    public String email;
    public String phone;
    Long lastUpdated= new Long(0);

    protected User(Parcel in) {
        username = in.readString();
        password = in.readString();
        email = in.readString();
        phone = in.readString();
        if (in.readByte() == 0) {
            lastUpdated = null;
        } else {
            lastUpdated = in.readLong();
        }
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

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
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
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());
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
        Timestamp ts = (Timestamp)json.get(LAST_UPDATED);
        user.setLastUpdated(new Long(ts.getSeconds()));
        return user;
    }

    static long getLocalLastUpdated()
    {
        Long localLastUpdate = MyApplication.getContext().getSharedPreferences("TAG", Context.MODE_PRIVATE).getLong("USERS_LAST_UPDATE", 0 );
        Log.d("TAG","localLastUpdate: " + localLastUpdate);
        return localLastUpdate;
    }

    static void setLocalLastUpdated(Long date)
    {
        SharedPreferences.Editor editor = MyApplication.getContext().getSharedPreferences("TAG", Context.MODE_PRIVATE).edit();
        editor.putLong("USERS_LAST_UPDATE",date);
        editor.commit();
        Log.d("TAG", "new lud" + date);
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
        if (lastUpdated == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(lastUpdated);
        }
    }
}
