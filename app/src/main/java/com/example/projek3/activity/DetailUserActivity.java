package com.example.projek3.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.projek3.R;
import com.example.projek3.adapters.SteppagerAdapter;
import com.example.projek3.database.UserfavoritHelppers;
import com.example.projek3.model.User;
import com.example.projek3.viewmodels.Detail_vm;
import com.example.projek3.viewmodels.FollowersV_vm;
import com.example.projek3.viewmodels.Followings_vm;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.projek3.MainActivity.E_NAME;
import static com.example.projek3.database.dbcontract.DataColumns.AVATAR;
import static com.example.projek3.database.dbcontract.DataColumns.CONTENT_URI;
import static com.example.projek3.database.dbcontract.DataColumns.URLUSER;
import static com.example.projek3.database.dbcontract.DataColumns.USERNAME;

public class DetailUserActivity extends AppCompatActivity {
    private TextView txt_nama, txt_detail_name, txt_detail_location, txt_repos, txt_detail_company, txt_detail_blog;
    private CircleImageView foto_user;
    private Detail_vm m_user;
    private ProgressBar loadingitem;
    private TabLayout tablayoutitem;
    private FloatingActionButton btnFavorit;
    private UserfavoritHelppers userfavoritHelppers;
    private Uri urliduser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdetails);

        loadingitem = findViewById(R.id.detail_progressBar);
        loadingitem.setVisibility(View.VISIBLE);
        SteppagerAdapter steppagerAdapter = new SteppagerAdapter(this, getSupportFragmentManager());
        ViewPager v_page = findViewById(R.id.detail_viewpager);
        v_page.setAdapter(steppagerAdapter);
        tablayoutitem = findViewById(R.id.detail_tabs);
        tablayoutitem.setupWithViewPager(v_page);
        userfavoritHelppers = UserfavoritHelppers.getInstance(getApplicationContext());
        userfavoritHelppers.open();

        txt_nama = findViewById(R.id.txt_nama);
        txt_detail_name = findViewById(R.id.txt_detail_name);
        txt_detail_company = findViewById(R.id.txt_detail_company);
        txt_detail_location = findViewById(R.id.txt_detail_location);
        txt_detail_blog = findViewById(R.id.txt_detail_blog);
        txt_repos = findViewById(R.id.txt_repos);
        foto_user = findViewById(R.id.foto_user);
        btnFavorit = findViewById(R.id.btn_favorite);

        m_user = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(Detail_vm.class);

        String fullname = getIntent().getStringExtra(E_NAME);
        m_user.setListUser(fullname);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(fullname);
        }


        if (isFavorite(fullname)) {
            btnFavorit.setImageResource(R.drawable.ic_baseline_favorite_24);
        }

        Log.d("Projek 3", "onCreate: " + fullname);

        setData();
    }

    private void setData() {
        Log.d("Projek 3", "setData: mulai");
        m_user.getListUser().observe(this, new Observer<User>() {

            @Override
            public void onChanged(final User user) {
                loadingitem.setVisibility(View.GONE);
                String uname = !user.getName().equals("null") ? user.getName() : "";
                String location = !user.getLocation().equals("null") ? user.getLocation() : "";
                String blog = !user.getBlog().equals("null") ? user.getBlog() : "";
                String followers = String.format(getResources().getString(R.string.followers), user.getFollowers());
                String followings = String.format(getResources().getString(R.string.followings), user.getFollowings());
                String repositories = String.format(getResources().getString(R.string.repository_person), user.getRepositories());
                String companies = !user.getCompany().equals("null") ? user.getCompany() : "";

                Objects.requireNonNull(tablayoutitem.getTabAt(0)).setText(followers);
                Objects.requireNonNull(tablayoutitem.getTabAt(1)).setText(followings);

                txt_nama.setText(uname);
                txt_detail_name.setText(user.getUsername());
                txt_detail_location.setText(location);
                txt_detail_blog.setText(blog);
                txt_repos.setText(repositories);
                txt_detail_company.setText(companies);
                Glide.with(DetailUserActivity.this)
                        .load(user.getAvatar())
                        .apply(new RequestOptions().override(160, 160))
                        .into(foto_user);
                FollowersV_vm followersVvm = new ViewModelProvider(DetailUserActivity.this, new ViewModelProvider.NewInstanceFactory()).get(FollowersV_vm.class);
                followersVvm.setListUser(user.getUsername());

                Followings_vm followingsVm = new ViewModelProvider(DetailUserActivity.this, new ViewModelProvider.NewInstanceFactory()).get(Followings_vm.class);
                followingsVm.setListUser(user.getUsername());

                btnFavorit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isFavorite(user.getUsername())) {

                            urliduser = Uri.parse(CONTENT_URI + "/" + user.getUsername());
                            getContentResolver().delete(urliduser, null, null);
                            Toast.makeText(DetailUserActivity.this, "item Berhasil dihapus", Toast.LENGTH_SHORT).show();
                            btnFavorit.setImageResource(R.drawable.ic_baseline_favorite_24);
                        } else {
                            ContentValues value = new ContentValues();
                            value.put(USERNAME, user.getUsername());
                            value.put(AVATAR, user.getAvatar());
                            value.put(URLUSER, user.getUrluser());
                            getContentResolver().insert(CONTENT_URI, value);
                            Toast.makeText(DetailUserActivity.this, "item Berhasil Ditambah", Toast.LENGTH_SHORT).show();
                            btnFavorit.setImageResource(R.drawable.ic_baseline_favorite_24);
                        }
                    }
                });
                Log.d("Projek 3", "showSelectedUser: " + E_NAME + "/" + user.getUsername());
            }
        });
    }


    boolean isFavorite(String username) {
        Cursor result = userfavoritHelppers.queryByUsername(username);
        return result.getCount() > 0;
    }


    protected void onDestroy() {
        super.onDestroy();
        userfavoritHelppers.close();
    }
}


