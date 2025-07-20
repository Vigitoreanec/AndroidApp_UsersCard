package com.example.myapp_cardclient_dbsqlite.userCard;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp_cardclient_dbsqlite.R;
import com.example.myapp_cardclient_dbsqlite.UserDetailActivity;
import com.example.myapp_cardclient_dbsqlite.storage.User;

import java.util.List;

public class UserAdapret extends RecyclerView.Adapter<UserAdapret.UserViewHolder> {
    private List<User> userList;
    private Context context;

    public UserAdapret(List<User> userList,Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_card, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.clientName.setText(user.name);

        int imageRes = position % 2 == 0 ? R.drawable.image1 : R.drawable.image2;
        holder.clientPhoto.setImageResource(imageRes);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, UserDetailActivity.class);
            intent.putExtra("USER_ID", user.id);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        ImageView clientPhoto;
        TextView clientName;
        View itemView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }


}

