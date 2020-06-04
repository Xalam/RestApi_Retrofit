package com.example.restapi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.Toolbar;

import com.example.restapi.Adapter.MahasiswaAdapter;
import com.example.restapi.Api.ApiClient;
import com.example.restapi.Api.ApiInterface;
import com.example.restapi.Model.GetMahasiswa;
import com.example.restapi.Model.Mahasiswa;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private MahasiswaAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    private ApiInterface apiInterface;
    Toolbar toolbar;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipeRefreshLayout = view.findViewById(R.id.swipe_layout);
        recyclerView = view.findViewById(R.id.recycler_view);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        toolbar = view.findViewById(R.id.toolbar_home);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        (((AppCompatActivity) getActivity()).getSupportActionBar()).setElevation(0);

        // Set Layout Manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        swipeRefreshLayout.setOnRefreshListener(() -> refreshData());
        refreshData();
    }

    public void refreshData() {
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

}
