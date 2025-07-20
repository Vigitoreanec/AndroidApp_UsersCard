package com.example.myapp_cardclient_dbsqlite.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    long insert(UserEntity userEntity);
    @Query("SELECT * FROM db_Users")
    List<UserEntity> getAllUsers();

    @Query("DELETE FROM db_Users")
    int clearAll();

    @Query("SELECT * FROM db_Users WHERE id  = :userId")
    UserEntity getUserById(long userId);
}
