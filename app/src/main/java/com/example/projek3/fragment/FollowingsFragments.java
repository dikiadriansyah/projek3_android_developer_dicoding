package com.example.projek3.fragment;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.projek3.viewmodels.Followings_vm;

import java.util.ArrayList;


public class FollowingsFragments extends Fragment {
    private RecyclerView list_following;
    private Followings_vm followingsVm;
    private UserAdapter adapters;
    private ProgressBar loadingfollowings;

    public FollowingsFragments() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_followings, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        list_following = view.findViewById(R.id.list_following);
        loadingfollowings = view.findViewById(R.id.loadingfollowing);
        adapters = new UserAdapter();

        adapters.setOnItemClickCallBack(new UserAdapter.OnItemClickCallBack() {
            @Override
            public void onItemClicked(User user) {
                Intent intent = new Intent(getContext(), DetailUserActivity.class);
                intent.putExtra(MainActivity.E_NAME, user.getUsername());
                startActivity(intent);
            }
        });
        followingsVm = new ViewModelProvider(requireActivity(), new ViewModelProvider.NewInstanceFactory()).get(Followings_vm.class);

        getDataUser();
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapters.notifyDataSetChanged();
        list_following.setHasFixedSize(true);
        list_following.setLayoutManager(new LinearLayoutManager((getContext())));
        list_following.setAdapter(adapters);
        loadingfollowings.setVisibility(View.VISIBLE);
    }

    private void getDataUser() {
        followingsVm.getListUser().observe(getViewLifecycleOwner(), new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(ArrayList<User> users) {
                loadingfollowings.setVisibility(View.GONE);

                if (users != null) {
                    adapters.setData(users);
                }
            }
        });
    }
}
