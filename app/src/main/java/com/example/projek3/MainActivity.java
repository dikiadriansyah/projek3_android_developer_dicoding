package com.example.projek3;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projek3.activity.DetailUserActivity;
import com.example.projek3.activity.FavoriteActivity;
import com.example.projek3.activity.SettingActivity;
import com.example.projek3.adapters.UserAdapter;
import com.example.projek3.model.User;
import com.example.projek3.viewmodels.SearchingViewModel;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private SearchingViewModel searchingViewModel;
    private ProgressBar loadingBar;
    private UserAdapter adapters;
    private TextView txtload;

    public static final String E_NAME = "extra_username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utama);

        txtload = findViewById(R.id.txtloading);

        RecyclerView ritem = findViewById(R.id.recycle_item);
        ritem.setLayoutManager(new LinearLayoutManager(this));
        adapters = new UserAdapter();
        adapters.notifyDataSetChanged();
        ritem.setAdapter(adapters);

        adapters.setOnItemClickCallBack(new UserAdapter.OnItemClickCallBack() {
            @Override
            public void onItemClicked(User user) {
                showSelectedUser(user);
            }
        });

        loadingBar = findViewById(R.id.loadingmain);
        loadingBar.setVisibility(View.GONE);

        searchingViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(SearchingViewModel.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        if (searchManager != null){
            SearchView searchViews = (SearchView) menu.findItem(R.id.fitursearch).getActionView();
            searchViews.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchViews.setQueryHint(getResources().getString(R.string.searching));
            searchViews.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    searchingViewModel.setListUser(query);
                    loadingBar.setVisibility(View.VISIBLE);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (!(newText.equals(""))){
                        txtload.setVisibility(View.INVISIBLE);
                        searchingViewModel.setListUser(newText);
                        loadingBar.setVisibility(View.VISIBLE);
                    }
                    return true;
                }
            });

            searchingViewModel.getListUser().observe(this, new Observer<ArrayList<User>>() {
                @Override
                public void onChanged(ArrayList<User> users) {
                    if (users != null){
                        adapters.setData(users);
                    }else {
                        Toast.makeText(MainActivity.this, getResources().getString(R.string.unknown), Toast.LENGTH_SHORT).show();

                    }
                    loadingBar.setVisibility(View.GONE);
                }
            });
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem items){
        if (items.getItemId() == R.id.menu_favorit){
            startActivity(new Intent(this, FavoriteActivity.class));
            return true;
        }else if (items.getItemId() == R.id.menu_notif){
            startActivity(new Intent(this, SettingActivity.class));
            return true;
        }

        else if (items.getItemId() == R.id.menu_bahasa) {
            startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
            return true;
        }
        return super.onOptionsItemSelected(items);
    }

    private void showSelectedUser(User user){
        Intent intent = new Intent(this, DetailUserActivity.class);
        intent.putExtra(E_NAME, user.getUsername());
        startActivity(intent);
    }


}
