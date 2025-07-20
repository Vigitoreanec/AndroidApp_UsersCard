package com.example.myapp_cardclient_dbsqlite.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {UserEntity.class}, version = 1)
public abstract class DataBase extends RoomDatabase {
    public abstract UserDao userDao();
}

