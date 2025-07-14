package com.example.myapp_cardclient_dbsqlite.storage;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponce {
    private List<User> users;

    public UserResponce(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }
}
