package com.example.myapp_cardclient_dbsqlite;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.myapp_cardclient_dbsqlite.data.DataBase;
import com.example.myapp_cardclient_dbsqlite.data.UserDao;
import com.example.myapp_cardclient_dbsqlite.data.UserEntity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<UserEntity> usersList;
    UserDao userDao;
    DataBase dataBase;
    private LinearLayout usersContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataBase = Room.databaseBuilder(getApplicationContext(), DataBase.class, "Db_Users")
                .allowMainThreadQueries().build();
        userDao = dataBase.userDao();

        usersContainer = findViewById(R.id.usersContainer);

        usersList = userDao.getAllUsers();

        if (usersList != null && !usersList.isEmpty()) {
            addUserCard(usersList);
        } else {
            Toast.makeText(this, "Список пользователей пуст", Toast.LENGTH_SHORT).show();
            dataBase.close();
        }
        usersContainer.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UserDetailActivity.class);
        });
    }

    private void addUserCard(List<UserEntity> users) {
        usersContainer.removeAllViews();
        for (UserEntity user : users) {
            int position = user.getId();
            LinearLayout userCard = new LinearLayout(this);
            userCard.setOrientation(LinearLayout.VERTICAL);
            userCard.setGravity(Gravity.CENTER);
            userCard.setPadding(16, 16, 16, 16);
            userCard.setBackgroundColor(Color.parseColor("#B4B4B4"));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            params.setMargins(0, 0, 0, 16);
            userCard.setLayoutParams(params);

            LinearLayout caseUser = new LinearLayout(this);
            caseUser.setOrientation(LinearLayout.HORIZONTAL);
            //caseUser.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams paramsImage = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            paramsImage.width = 300;
            paramsImage.height = 300;
            ImageView image = new ImageView(this);
            int imageRes = position % 2 == 0 ? R.drawable.image1 : R.drawable.image2;
            image.setImageResource(imageRes);
            image.setLayoutParams(paramsImage);
            caseUser.addView(image);

            TextView text = new TextView(this);
            text.setText(user.getNameUser());
            text.setTextSize(21);
            text.setGravity(Gravity.CENTER);
            text.setTypeface(null, Typeface.BOLD);
            caseUser.addView(text);

            userCard.addView(caseUser);
            userCard.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, UserDetailActivity.class);
                intent.putExtra("USER_ID", user.getId());
                startActivity(intent);
            });

            usersContainer.addView(userCard);
        }
    }


}
