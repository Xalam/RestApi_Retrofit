package com.example.restapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.restapi.Adapter.MahasiswaAdapter;
import com.example.restapi.Api.ApiClient;
import com.example.restapi.Api.ApiInterface;
import com.example.restapi.Model.GetMahasiswa;
import com.example.restapi.Model.Mahasiswa;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    ApiInterface apiInterface;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    public static MainActivity mainActivity;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingActionButton = findViewById(R.id.btn_add);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        swipeRefreshLayout = findViewById(R.id.swipe_layout);

        swipeRefreshLayout.setOnRefreshListener(() -> mainActivity.refresh());
        mainActivity = this;
        refresh();

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
}
