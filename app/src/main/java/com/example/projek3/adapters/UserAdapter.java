package com.example.projek3.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.projek3.R;
import com.example.projek3.model.User;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private ArrayList<User> uData = new ArrayList<>();
    private OnItemClickCallBack onItemClickCallBacks;

    public void setData(ArrayList<User> usersd) {
        uData.clear();
        uData.addAll(usersd);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parents, int viewType) {
        View view = LayoutInflater.from(parents.getContext()).inflate(
                R.layout.list_user, parents, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holders, int position) {
        holders.bind(uData.get(position));
    }

    public int getItemCount() {
        return uData.size();
    }

    public void setOnItemClickCallBack(OnItemClickCallBack onItemClickCallBack) {
        this.onItemClickCallBacks = onItemClickCallBack;
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView foto_user;
        private TextView txt_nama, urlUser;

        UserViewHolder(@NonNull View itemView) {
            super(itemView);

            foto_user = itemView.findViewById(R.id.foto_user);
            txt_nama = itemView.findViewById(R.id.txt_nama);
            urlUser = itemView.findViewById(R.id.txt_urlgithub);


            itemView.setOnClickListener(new View.OnClickListener() {

                public void onClick(View vi) {
                    onItemClickCallBacks.onItemClicked(uData.get(getAdapterPosition()));
                }
            });
        }

        void bind(User user) {
            Glide.with(itemView.getContext())
                    .load(user.getAvatar())
                    .apply(new RequestOptions().override(60, 60))
                    .into(foto_user);
            txt_nama.setText(user.getUsername());
            urlUser.setText(user.getUrluser());
        }

    }

    public interface OnItemClickCallBack {
        void onItemClicked(User user);
    }

}
