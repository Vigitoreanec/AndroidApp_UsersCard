package com.example.myapp_cardclient_dbsqlite;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.myapp_cardclient_dbsqlite.data.DataBase;
import com.example.myapp_cardclient_dbsqlite.data.UserDao;
import com.example.myapp_cardclient_dbsqlite.data.UserEntity;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UserDetailActivity extends AppCompatActivity {
    private int userId;
    private UserDao userDao;
    private DataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_card);

        dataBase = Room.databaseBuilder(getApplicationContext(), DataBase.class, "Db_Users")
                .allowMainThreadQueries().build();
        userDao = dataBase.userDao();

        userId = getIntent().getIntExtra("USER_ID", -1);
        if (userId == -1) {
            Toast.makeText(this, "Ошибка: пользователь не найден", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        setupViews();
        loadUserDetails();
    }
    private void loadUserDetails() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                UserEntity userEntity = userDao.getUserById(userId);
                runOnUiThread(() -> {
                    if (userEntity != null) {
                        setupUserDetails(userEntity);
                    } else {
                        Toast.makeText(this, "Данные пользователя не найдены", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            } catch (Exception e) {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show();
                    finish();
                });
            }
        });
    }

    private void setupViews() {
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
    }

    private void setupUserDetails(UserEntity user) {

        ImageView avatar = findViewById(R.id.userAvatar);
        TextView name = findViewById(R.id.userName);
        TextView email = findViewById(R.id.userEmail);
        TextView phone = findViewById(R.id.userPhone);
        TextView age = findViewById(R.id.userAge);

        int imageRes = user.getId() % 2 == 0 ? R.drawable.image1 : R.drawable.image2;
        avatar.setImageResource(imageRes);

        name.setText("Имя: " + user.getNameUser());
        email.setText("Почта: " + user.getMailUser());
        phone.setText("Телефон: " + user.getPhoneUser());
        age.setText(String.format("Возраст: %d лет", user.getAgeUser()));


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Закрываем соединение с базой данных
        if (dataBase != null) {
            dataBase.close();
        }
    }
}
