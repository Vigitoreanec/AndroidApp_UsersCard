package com.example.myapp_cardclient_dbsqlite.storage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UsersApi {
    @GET("users")
    Call<List<User>> getUsers();
}
