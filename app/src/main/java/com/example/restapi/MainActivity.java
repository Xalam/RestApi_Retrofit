package com.example.restapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.example.restapi.Adapter.MahasiswaAdapter;
import com.example.restapi.Api.ApiClient;
import com.example.restapi.Api.ApiInterface;
import com.example.restapi.Model.GetMahasiswa;
import com.example.restapi.Model.Mahasiswa;
import com.example.restapi.Other.BottomNavBehavior;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    FloatingActionButton floatingActionButton;
    ApiInterface apiInterface;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    public static MainActivity mainActivity;
    SwipeRefreshLayout swipeRefreshLayout;
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingActionButton = findViewById(R.id.btn_add);
//        recyclerView = findViewById(R.id.recycler_view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        apiInterface = ApiClient.getClient().create(ApiInterface.class);
//        swipeRefreshLayout = findViewById(R.id.swipe_layout);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        frameLayout = findViewById(R.id.frame_layout);

//        swipeRefreshLayout.setOnRefreshListener(() -> mainActivity.refresh());
//        mainActivity = this;
//        refresh();

//      StatusBar Color Changed
        Window window = MainActivity.this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorWhite));
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        loadFragment(new HomeFragment());

//      HideShow Bottom Nav
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavBehavior());
    }

    public void refresh() {
        swipeRefreshLayout.setRefreshing(true);
        Call<GetMahasiswa> mhsCall = apiInterface.getMahasiswa();
        mhsCall.enqueue(new Callback<GetMahasiswa>() {
            @Override
            public void onResponse(Call<GetMahasiswa> call, Response<GetMahasiswa> response) {
                swipeRefreshLayout.setRefreshing(false);
                List<Mahasiswa> mhslist = response.body().getData();
                Log.d("Retrofit Get", "Jumlah Mahasiswa : " +
                        String.valueOf(mhslist.size()));
                adapter = new MahasiswaAdapter(mhslist);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<GetMahasiswa> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Log.d("Retrofit Get", "Jumlah" + t.toString());
            }
        });
    }

    public void TambahData(View view) {
        startActivity(new Intent(MainActivity.this, InsertActivity.class));
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        Fragment fragment = null;

        switch (item.getItemId()){
            case R.id.menu_home:
                fragment = new HomeFragment();
                break;
            case R.id.menu_history:
                fragment = new HistoryFragment();
                break;
            case R.id.menu_notif:
                fragment = new NotifFragment();
                break;
            case R.id.menu_profile:
                fragment = new ProfileFragment();
                break;
        }

        return loadFragment(fragment);
    };
}
