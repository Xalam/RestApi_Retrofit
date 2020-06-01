package com.example.restapi.Api;

import com.example.restapi.Model.GetMahasiswa;
import com.example.restapi.Model.PostPutDelMahasiswa;

import kotlin.jvm.Strictfp;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiInterface {
    @GET("view.php")
    Call<GetMahasiswa> getMahasiswa();
    @FormUrlEncoded
    @POST("insert.php")
    Call<PostPutDelMahasiswa> postMhs(@Field("nim") String nim,
                                      @Field("nama") String nama,
                                      @Field("kelas") String kelas,
                                      @Field("prodi") String prodi);
    @FormUrlEncoded
    @POST("update.php")
    Call<PostPutDelMahasiswa> putMhs(@Field("nim") String nim,
                                     @Field("nama") String nama,
                                     @Field("kelas") String kelas,
                                     @Field("prodi") String prodi);

    @FormUrlEncoded
    @POST("delete.php")
    Call<PostPutDelMahasiswa> deleteMahasiswa(@Field("nim") String nim);

}
