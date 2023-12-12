package com.example.clientapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class userAdapter extends RecyclerView.Adapter<userAdapter.UserViewHolder> {
    private ArrayList<User> mData = new ArrayList<>();

    public void setData(ArrayList<User> users) {
        mData.clear();
        mData.addAll(users);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public userAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_user, parent, false);

        return new UserViewHolder(view);
    }

    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imgUser;
        private TextView txtUsername, urlUser;

        UserViewHolder(@NonNull View itemView) {
            super(itemView);
            imgUser = itemView.findViewById(R.id.foto_user);
            txtUsername = itemView.findViewById(R.id.txt_nama);
            urlUser = itemView.findViewById(R.id.txt_urlgithub);
        }

        void bind(User user) {
            Glide.with(itemView.getContext())
                    .load(user.getAvatar())
                    .apply(new RequestOptions().override(50, 50))
                    .into(imgUser);
            txtUsername.setText(user.getUsername());
            urlUser.setText(user.getUrlUser());
        }

    }

}
