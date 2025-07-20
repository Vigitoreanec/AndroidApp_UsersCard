package com.example.myapp_cardclient_dbsqlite;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.myapp_cardclient_dbsqlite.data.DataBase;
import com.example.myapp_cardclient_dbsqlite.data.UserDao;
import com.example.myapp_cardclient_dbsqlite.data.UserEntity;
import com.example.myapp_cardclient_dbsqlite.storage.User;
import com.example.myapp_cardclient_dbsqlite.storage.UsersApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    LinearLayout container;
    UsersApi usersApi;
    private List<User> usersList = new ArrayList<>();
    DataBase dataBase;
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        textView = findViewById(R.id.textResult);
        container = findViewById(R.id.usersContainer);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();
        usersApi = retrofit.create(UsersApi.class);
        getUsers();
        dataBase = Room.databaseBuilder(getApplicationContext(), DataBase.class, "Db_Users")
                .allowMainThreadQueries().build();
        userDao = dataBase.userDao();

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            int usersList = 0;
            intent.putExtra("USERS_LIST", new ArrayList<>(usersList));
            startActivity(intent);
        }, START_DELAY);
    }

    private void getUsers() {
        Call<List<User>> call = usersApi.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    usersList = response.body();
                    if (!usersList.isEmpty()) {
                        displayUserData(usersList);
                        createDb(usersList);
                    }
                } else {
                    textView.setText("Клиенты не найдены");
                }
            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                textView.setText("Error " + t.getMessage());
            }
        });
    }
    private void createDb(List<User> usersList) {
        int count = userDao.clearAll();
        Toast.makeText(this, "Таблица Удалена Специально! Записей было удалено: " + count, Toast.LENGTH_SHORT).show();
        for (int i = 0; i < usersList.size(); i++) {
            UserEntity user = new UserEntity();
            user.setNameUser(usersList.get(i).name);
            user.setMailUser(usersList.get(i).email);
            user.setPhoneUser(usersList.get(i).phoneNumber);
            user.setAgeUser(new Random().nextInt(41) + 15);
            userDao.insert(user);
        }
    }
    private void displayUserData(List<User> users) {
        container.removeAllViews();
        for (User user : users) {
            textView = new TextView(this);
            String userInfo = String.format("Пользователь ID: %s\nИмя: %s\nEmail: %s\nТелефон: %s\n", user.id, user.name, user.email, user.phoneNumber);
            textView.setPadding(0, 8, 0, 16);
            textView.setText(userInfo);
            container.addView(textView);
        }
    }

//    private void displayUserData(List<User> users) {
//
//        int usersSize = Math.min(users.size(), 3);
//        for (int i = 0; i < usersSize; i++) {
//            User user = users.get(i);
//            String userInfo = String.format(
//                    "Пользователь ID: %s\nИмя: %s\nEmail: %s\nТелефон: %s\n",
//                    user.id, user.name, user.email, user.phoneNumber
//            );
//
//            switch (i) {
//                case 0:
//                    textView.setText(userInfo);
//                    break;
//
//                case 1:
//                    textView1.setText(userInfo);
//                    break;
//
//                case 2:
//                    textView2.setText(userInfo);
//                    break;
//            }
//        }
//    }


//    private void displayUsersData(List<User> users) {
//        User user = null;
//        user = users.get(0);
//        String result = "Пользователь ID: " + user.id + "\n" +
//                "Имя: " + user.name + "\n" +
//                "Email: " + user.email + "\n" +
//                "Телефон: " + user.phoneNumber + "\n";
//        textView.setText(result);
//        user = users.get(1);
//        String result1 = "Пользователь ID: " + user.id + "\n" +
//                "Имя: " + user.name + "\n" +
//                "Email: " + user.email + "\n" +
//                "Телефон: " + user.phoneNumber + "\n";
//        textView1.setText(result1);
//        user = users.get(2);
//        String result2 = "Пользователь ID: " + user.id + "\n" +
//                "Имя: " + user.name + "\n" +
//                "Email: " + user.email + "\n" +
//                "Телефон: " + user.phoneNumber + "\n";
//        textView2.setText(result2);
//    }
}

