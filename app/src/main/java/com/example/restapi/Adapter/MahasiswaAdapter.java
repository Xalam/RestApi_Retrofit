package com.example.restapi.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restapi.EditActivity;
import com.example.restapi.Model.Mahasiswa;
import com.example.restapi.R;

import java.util.List;

public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.MyViewHolder> {
    List<Mahasiswa> mhslist;

    public MahasiswaAdapter(List<Mahasiswa> list_mhs){
        mhslist = list_mhs;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mahasiswa_list, parent, false);
        MyViewHolder mViewHolder = new MyViewHolder(mView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MahasiswaAdapter.MyViewHolder holder, final int position) {
        holder.tv_nim.setText("Nim = " + mhslist.get(position).getNim());
        holder.tv_nama.setText("Nama = " + mhslist.get(position).getNama());
        holder.tv_kelas.setText("Kelas = " + mhslist.get(position).getKelas());
        holder.tv_prodi.setText("Prodi = " + mhslist.get(position).getProdi());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(view.getContext(), EditActivity.class);
                mIntent.putExtra("Nim", mhslist.get(position).getNim());
                mIntent.putExtra("Nama", mhslist.get(position).getNama());
                mIntent.putExtra("Kelas", mhslist.get(position).getKelas());
                mIntent.putExtra("Prodi", mhslist.get(position).getProdi());
                view.getContext().startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mhslist.size();
    }

    public class  MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_nim, tv_nama, tv_kelas, tv_prodi;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_nim = itemView.findViewById(R.id.item_nim);
            tv_nama = itemView.findViewById(R.id.item_nama);
            tv_kelas = itemView.findViewById(R.id.item_kelas);
            tv_prodi = itemView.findViewById(R.id.item_prodi);
        }
    }
}
