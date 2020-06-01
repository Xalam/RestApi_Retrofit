package com.example.restapi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.restapi.Api.ApiClient;
import com.example.restapi.Api.ApiInterface;
import com.example.restapi.Model.PostPutDelMahasiswa;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsertActivity extends AppCompatActivity {

    EditText edtnim, edtnama, edtkelas, edtprodi;
    Button btnsubmit;
    ApiInterface apiInterface;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        edtnim = findViewById(R.id.txt_nim);
        edtnama = findViewById(R.id.txt_nama);
        edtkelas = findViewById(R.id.txt_kelas);
        edtprodi = findViewById(R.id.txt_prodi);
        btnsubmit = findViewById(R.id.btn_submit);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Tunggu Sob ...");

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

    }

    public void SubmitData(View view) {
        progressDialog.show();
        Call<PostPutDelMahasiswa> insertMhs = apiInterface.postMhs(
                edtnim.getText().toString(),
                edtnama.getText().toString(),
                edtkelas.getText().toString(),
                edtprodi.getText().toString()
        );

        insertMhs.enqueue(new Callback<PostPutDelMahasiswa>() {
            @Override
            public void onResponse(Call<PostPutDelMahasiswa> call, Response<PostPutDelMahasiswa> response) {
                progressDialog.hide();
                onAddSuccess(response.body().getMessage());
                MainActivity.mainActivity.refresh();
                finish();
            }

            @Override
            public void onFailure(Call<PostPutDelMahasiswa> call, Throwable t) {
                progressDialog.hide();
                onAddError(t.getMessage());
            }
        });
    }

    public void onAddSuccess(String message){
        Toast.makeText(MainActivity.mainActivity, message, Toast.LENGTH_SHORT).show();
    }

    public void onAddError(String message){
        Toast.makeText(MainActivity.mainActivity, message, Toast.LENGTH_SHORT).show();
    }
}
