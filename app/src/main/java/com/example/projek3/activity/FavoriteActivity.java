package com.example.projek3.activity;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projek3.R;
import com.example.projek3.adapters.UserAdapter;
import com.example.projek3.database.MapHelppers;
import com.example.projek3.database.UserfavoritHelppers;
import com.example.projek3.model.User;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

import static com.example.projek3.MainActivity.E_NAME;

import static com.example.projek3.database.dbcontract.DataColumns.CONTENT_URI;

public class FavoriteActivity extends AppCompatActivity implements LoadUserCallback {
    private RecyclerView list_favorit;
    private UserAdapter adapters;
    private ProgressBar loading_favorit;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites_activity);

        loading_favorit = findViewById(R.id.loadingfavorite);
        list_favorit = findViewById(R.id.list_favorit);
        list_favorit.setHasFixedSize(true);
        list_favorit.setLayoutManager(new LinearLayoutManager(this));
        adapters = new UserAdapter();
        list_favorit.setAdapter(adapters);
        adapters.setOnItemClickCallBack(new UserAdapter.OnItemClickCallBack() {
            @Override
            public void onItemClicked(User user) {
                showSelectedUser(user);
            }
        });

        UserfavoritHelppers userfavoritHelppers = UserfavoritHelppers.getInstance(getApplicationContext());
        userfavoritHelppers.open();

        HandlerThread handlerThread = new HandlerThread("DataObservers");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());

        DataObserver itemobserver = new DataObserver(handler, this);
        getContentResolver().registerContentObserver(CONTENT_URI, true, itemobserver);

        if (savedInstanceState == null) {
            new LoadUserAsync(this, this).execute();
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.favorites));
        }

    }

    private void showSelectedUser(User user) {
        Intent intent = new Intent(this, DetailUserActivity.class);
        intent.putExtra(E_NAME, user.getUsername());
        startActivity(intent);
    }

    public void preExecute() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loading_favorit.setVisibility(View.VISIBLE);
            }
        });
    }

    public void postExecute(ArrayList<User> users) {
        loading_favorit.setVisibility(View.GONE);
        if (users.size() > 0) {
            adapters.setData(users);
        } else {
            adapters.setData(new ArrayList<User>());
            Snackbar.make(list_favorit, "data tidak ditemukan", BaseTransientBottomBar.LENGTH_SHORT).show();
        }
    }

    private static class LoadUserAsync extends AsyncTask<Void, Void, ArrayList<User>> {
        private final WeakReference<Context> weakContexts;
        private final WeakReference<LoadUserCallback> weakCallbacks;

        private LoadUserAsync(Context context, LoadUserCallback callback) {
            weakContexts = new WeakReference<>(context);
            weakCallbacks = new WeakReference<>(callback);
        }

        protected void onPreExecute() {
            super.onPreExecute();
            weakCallbacks.get().preExecute();
        }

        protected ArrayList<User> doInBackground(Void... voids) {
            Context context = weakContexts.get();
            Cursor dataitemCursor = context.getContentResolver().query(CONTENT_URI, null, null, null, null);
            return MapHelppers.mapCursorToArrayList(Objects.requireNonNull(dataitemCursor));
        }

        protected void onPostExecute(ArrayList<User> users) {
            super.onPostExecute(users);
            weakCallbacks.get().postExecute(users);
        }
    }

    public static class DataObserver extends ContentObserver{
        final Context contexts;

        public DataObserver(Handler handler, Context context){
            super(handler);
            this.contexts = context;
        }

        public void onChange(boolean selfChange){
            super.onChange(selfChange);
            new LoadUserAsync(contexts, (LoadUserCallback) contexts).execute();
        }

    }

}

interface LoadUserCallback{
    void preExecute();
    void postExecute(ArrayList<User> users);
}