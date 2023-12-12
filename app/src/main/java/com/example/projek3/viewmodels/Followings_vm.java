package com.example.projek3.viewmodels;

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

public class Followings_vm extends ViewModel {
    private MutableLiveData<ArrayList<User>> listUsers = new MutableLiveData<>();

    public MutableLiveData<ArrayList<User>> getListUser() { return listUsers;
    }

    public void setListUser(String username){
        final ArrayList<User> listItems = new ArrayList<>();

        String url = "https://api.github.com/users/" + username + "/following";

        AsyncHttpClient users = new AsyncHttpClient();
        users.addHeader("Authorization", BuildConfig.TOKEN);
        users.addHeader("User-Agent", "request");
        users.get(
                url, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        try {
                            String result = new String(responseBody);
                            JSONArray responseArray = new JSONArray(result);

                            for (int i = 0; i < responseArray.length(); i++) {
                                JSONObject list = responseArray.getJSONObject(i);
                                User client = new User();
                                client.setUsername(list.getString("login"));
                                client.setAvatar(list.getString("avatar_url"));
                                listItems.add(client);
                            }
                            listUsers.postValue(listItems);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });
    }

}
