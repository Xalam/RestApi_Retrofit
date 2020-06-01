package com.example.restapi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restapi.Api.ApiClient;
import com.example.restapi.Api.ApiInterface;
import com.example.restapi.Model.PostPutDelMahasiswa;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditActivity extends AppCompatActivity {

    EditText edtnama, edtkelas, edtprodi;
    TextView txtnim;
    Button btnupdate, btndelete;
    ApiInterface apiInterface;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        txtnim = findViewById(R.id.txt_nim_edit);
        edtnama = findViewById(R.id.txt_nama_edit);
        edtkelas = findViewById(R.id.txt_kelas_edit);
        edtprodi = findViewById(R.id.txt_prodi_edit);
        btnupdate = findViewById(R.id.btn_submit_edit);
        btndelete = findViewById(R.id.btn_delete_edit);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Tunggu Sob ...");

        Intent intent = getIntent();
        txtnim.setText(intent.getStringExtra("Nim"));
        edtnama.setText(intent.getStringExtra("Nama"));
        edtkelas.setText(intent.getStringExtra("Kelas"));
        edtprodi.setText(intent.getStringExtra("Prodi"));

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

    }

    public void UpdateData(View view) {
        progressDialog.show();
        Call<PostPutDelMahasiswa> updatemhs = apiInterface.putMhs(
                txtnim.getText().toString(),
                edtnama.getText().toString(),
                edtkelas.getText().toString(),
                edtprodi.getText().toString()
        );

        updatemhs.enqueue(new Callback<PostPutDelMahasiswa>() {
            @Override
            public void onResponse(Call<PostPutDelMahasiswa> call, Response<PostPutDelMahasiswa> response) {
                progressDialog.hide();
                MainActivity.mainActivity.refresh();
                Toast.makeText(MainActivity.mainActivity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<PostPutDelMahasiswa> call, Throwable t) {
                progressDialog.hide();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void DeleteData(View view) {
        progressDialog.show();
        Call<PostPutDelMahasiswa> deleteMhs = apiInterface.deleteMahasiswa(txtnim.getText().toString());

        deleteMhs.enqueue(new Callback<PostPutDelMahasiswa>() {
            @Override
            public void onResponse(Call<PostPutDelMahasiswa> call, Response<PostPutDelMahasiswa> response) {
                progressDialog.hide();
                MainActivity.mainActivity.refresh();
                Toast.makeText(MainActivity.mainActivity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<PostPutDelMahasiswa> call, Throwable t) {
                progressDialog.hide();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
