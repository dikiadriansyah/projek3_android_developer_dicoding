package com.example.projek3.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.projek3.BuildConfig;
import com.example.projek3.model.User;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Detail_vm extends ViewModel {
private MutableLiveData<User> listUsers = new MutableLiveData<>();

public  MutableLiveData<User> getListUser(){
    return  listUsers;
}

public void setListUser(String username){
    String url = "https://api.github.com/users/" + username;

    AsyncHttpClient users = new AsyncHttpClient();
    users.addHeader("Authorization", BuildConfig.TOKEN);
    users.addHeader("User-Agent", "request");
    users.get(url, new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            try {
                String result = new String(responseBody);
                JSONObject responseObject = new JSONObject(result);

                User items = new User();
                items.setAvatar(responseObject.getString("avatar_url"));
                items.setName(responseObject.getString("name"));
                items.setUsername(responseObject.getString("login"));
                items.setLocation(responseObject.getString("location"));
                items.setBlog(responseObject.getString("blog"));
                items.setFollowings(responseObject.getInt("following"));
                items.setFollowers(responseObject.getInt("followers"));
                items.setRepository(responseObject.getInt("public_repos"));
                items.setCompany(responseObject.getString("company"));
                listUsers.setValue(items);
            }catch (JSONException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

        }
    });
}

}
