package com.example.car4u.Model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao
{
    @Query("SELECT * FROM User WHERE username NOT NULL")
    List<User> getAllUsers();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(User... users);

    @Query("SELECT * FROM User WHERE username=:username")
    User getUserByUsername(String username);
}

