package com.bolla.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bolla.myapplication.Classes.SessionManager;
import com.bolla.myapplication.adapter.HomeRvAdapter;
import com.bolla.myapplication.models.MovieModel;
import com.bolla.myapplication.R;
import com.bolla.myapplication.webservice.APIClient;
import com.bolla.myapplication.webservice.RetrofitFactory;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView rvHome;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    List<MovieModel> movietList = new ArrayList<>();
    SessionManager sessionManager;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ImageView img;
    private APIClient api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigation_view);
        View header = navigationView.getHeaderView(0);

        sessionManager = new SessionManager((HomeActivity.this));

        TextView tv_email = header.findViewById(R.id.tv_email);
        tv_email.setText(sessionManager.getUserDetails().get(sessionManager.KEY_EMAIL));

        img = header.findViewById(R.id.iv_image);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Uri imageUri = user.getPhotoUrl();
            if(imageUri!= null && !imageUri.equals("")){
                Glide.with(HomeActivity.this).
                        load(imageUri).into(img);
            }
        }

        rvHome = findViewById(R.id.rv_home);
        adapter = new HomeRvAdapter(HomeActivity.this, movietList);
        rvHome.setLayoutManager(layoutManager);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HomeActivity.this, RecyclerView.VERTICAL, false);
        rvHome.setItemAnimator(new DefaultItemAnimator());
        rvHome.addItemDecoration(new DividerItemDecoration(rvHome.getContext(), linearLayoutManager.getOrientation()));
        rvHome.setLayoutManager(linearLayoutManager);
        setupSideMenuButton();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {


            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_profile:
                        Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                        startActivity(intent);

                        break;

                    case R.id.action_settings:
                        Intent intent3 = new Intent(HomeActivity.this, SettingsActivity.class);
                        startActivity(intent3);
                        break;

                    case R.id.action_help:
                        Toast.makeText(HomeActivity.this, "help", Toast.LENGTH_SHORT).show();

                        break;


                    case R.id.action_signout:
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(HomeActivity.this, "exit", Toast.LENGTH_SHORT).show();
                        sessionManager.logoutUser();
                        Intent intent2 = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(intent2);
                        break;


                }

                return true;
            }
        });


        // specify an adapter (see also next example)
        rvHome.setAdapter(adapter);


        getAllMovies();
    }


    public void onClick_facebook(View view) {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse("http://www.facebook.com"));
        startActivity(intent);
    }

    public void onClick_twitter(View view) {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse("http://www.twitter.com"));
        startActivity(intent);

    }

    public void onClick_linkedIn(View view) {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse("http://www.linkedin.com"));
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        boolean isLoggedIn = sessionManager.checkLogin();
        if (isLoggedIn != true) {
            Intent intent = new Intent(HomeActivity.this,
                    LoginActivity.class);
            startActivity(intent);
        }

    }

    private void setupSideMenuButton() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getAllMovies() {
        api = RetrofitFactory.getRetrofit().create(APIClient.class);

        Call<List<MovieModel>> getAllMovies = api.getPosts();


        getAllMovies.enqueue(new Callback<List<MovieModel>>() {
            @Override
            public void onResponse(Call<List<MovieModel>> call, Response<List<MovieModel>> response) {

                movietList.addAll(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<MovieModel>> call, Throwable t) {

            }
        });

    }

}
