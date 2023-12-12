package com.example.projek3.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.projek3.BuildConfig;
import com.example.projek3.model.User;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchingViewModel  extends ViewModel {
    private MutableLiveData<ArrayList<User>> listUsers = new MutableLiveData<>();

    public MutableLiveData<ArrayList<User>> getListUser() {
        return listUsers;
    }

    public void setListUser(final String username) {
        final ArrayList<User> listItems = new ArrayList<>();
        String url = "https://api.github.com/search/users?q=" + username;

        AsyncHttpClient users = new AsyncHttpClient();
        users.addHeader("Authorization", BuildConfig.TOKEN);
        users.addHeader("User-Agent", "request");
        users.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    if (responseObject.getInt("total_count") != 0) {
                        JSONArray list = responseObject.getJSONArray("items");

                        for (int i = 0; i < list.length(); i++) {
                            JSONObject user = list.getJSONObject(i);
                            User clientItems = new User();
                            clientItems.setUsername(user.getString("login"));
                            clientItems.setAvatar(user.getString("avatar_url"));
                            clientItems.setUrluser(user.getString("html_url"));


                            listItems.add(clientItems);
                        }
                        listUsers.postValue(listItems);
                    } else {
                        listUsers.postValue(null);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("Projek 3", "onFailure: " + error.getMessage());
            }
        });
    }
}
