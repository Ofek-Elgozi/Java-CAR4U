package com.example.car4u.Model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.car4u.MyApplication;

@Database(entities = {User.class, Car.class}, version = 9)
abstract class AppLocalDbRepository extends RoomDatabase
{
    public abstract UserDao userDao();
    public abstract CarDao carDao();
}

public class AppLocalDB
{
    static public final AppLocalDbRepository db =
            Room.databaseBuilder(MyApplication.getContext(),
                    AppLocalDbRepository.class,
                    "dbFileName.db")
                    .fallbackToDestructiveMigration()
                    .build();
    private AppLocalDB(){}
}
