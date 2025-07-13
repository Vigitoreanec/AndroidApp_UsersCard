package com.example.myapp_cardclient_dbsqlite;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp_cardclient_dbsqlite.storage.User;
import com.example.myapp_cardclient_dbsqlite.storage.UsersApi;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StartActivity extends AppCompatActivity {
    private static final int START_DELAY = 5000; // 5 секунд

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    TextView textView;
    TextView textView1;
    TextView textView2;
    UsersApi usersApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        textView = findViewById(R.id.textResult);
        textView1 = findViewById(R.id.textResult1);
        textView2 = findViewById(R.id.textResult2);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();
        usersApi = retrofit.create(UsersApi.class);

        getUsers();

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, START_DELAY);
    }

    private void getUsers() {
        Call<List<User>> call = usersApi.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call,
                                   Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<User> usersResponce = response.body();
                    if (!usersResponce.isEmpty()) {
                        User user = null;
                        user = usersResponce.get(0);
                        String result = "Пользователь ID: " + user.id + "\n" +
                                "Имя: " + user.name + "\n" +
                                "Email: " + user.email + "\n" +
                                "Телефон: " + user.phoneNumber + "\n";
                        textView.setText(result);
                        user = usersResponce.get(1);
                        String result1 = "Пользователь ID: " + user.id + "\n" +
                                "Имя: " + user.name + "\n" +
                                "Email: " + user.email + "\n" +
                                "Телефон: " + user.phoneNumber + "\n";
                        textView1.setText(result1);
                        user = usersResponce.get(2);
                        String result2 = "Пользователь ID: " + user.id + "\n" +
                                "Имя: " + user.name + "\n" +
                                "Email: " + user.email + "\n" +
                                "Телефон: " + user.phoneNumber + "\n";
                        textView2.setText(result2);
                    }
                } else {
                    textView1.setText("Клиенты не найдены");
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                textView1.setText("Error " + t.getMessage());
            }
        });
    }
}
