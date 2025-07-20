package com.example.myapp_cardclient_dbsqlite.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "db_Users")
public class UserEntity {
    @PrimaryKey(autoGenerate = true)
    int id;

    String nameUser;
    String mailUser;
    String phoneUser;
    Integer ageUser;

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public void setMailUser(String mailUser) {
        this.mailUser = mailUser;
    }

    public void setPhoneUser(String phoneUser) {
        this.phoneUser = phoneUser;
    }

    public void setAgeUser(Integer ageUser) {
        this.ageUser = ageUser;
    }

    public int getId() {
        return id;
    }

    public String getNameUser() {
        return nameUser;
    }

    public String getMailUser() {
        return mailUser;
    }

    public String getPhoneUser() {
        return phoneUser;
    }

    public Integer getAgeUser() {
        return ageUser;
    }
}
