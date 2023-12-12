package com.example.projek3.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projek3.MainActivity;
import com.example.projek3.R;
import com.example.projek3.activity.DetailUserActivity;
import com.example.projek3.adapters.UserAdapter;
import com.example.projek3.model.User;
import com.example.projek3.viewmodels.FollowersV_vm;

import java.util.ArrayList;



public class FollowersFragments extends Fragment {
    private RecyclerView list_follower;
    private FollowersV_vm followersVvm;
    private UserAdapter adapters;
    private ProgressBar loadingFollowers;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_followers, container, false);
    }

    public FollowersFragments() {

    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        list_follower = view.findViewById(R.id.list_follower);
        loadingFollowers = view.findViewById(R.id.loadingfollower);
        adapters = new UserAdapter();
        adapters.setOnItemClickCallBack(new UserAdapter.OnItemClickCallBack() {
            @Override
            public void onItemClicked(User user) {
                Intent intents = new Intent(getContext(), DetailUserActivity.class);
                intents.putExtra(MainActivity.E_NAME, user.getUsername());
                startActivity(intents);
            }
        });

        followersVvm = new ViewModelProvider(requireActivity(), new ViewModelProvider.NewInstanceFactory()).get(FollowersV_vm.class);

        getDataUser();
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapters.notifyDataSetChanged();
        list_follower.setHasFixedSize(true);
        list_follower.setLayoutManager(new LinearLayoutManager((getContext())));
        list_follower.setAdapter(adapters);
        loadingFollowers.setVisibility(View.VISIBLE);
    }

    private void getDataUser() {
        followersVvm.getListUser().observe(getViewLifecycleOwner(), new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(ArrayList<User> userse) {
                loadingFollowers.setVisibility(View.GONE);
                Log.d("Projek 3", "onChanged: " + userse);
                if (userse != null) {
                    adapters.setData(userse);
                    Log.d("Projek 3", "onChanged: " + userse);
                }
            }
        });
    }
}
