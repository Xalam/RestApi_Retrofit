package com.example.restapi.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetMahasiswa {

    @SerializedName("status")
    private Integer status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<Mahasiswa> data = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Mahasiswa> getData() {
        return data;
    }

    public void setData(List<Mahasiswa> data) {
        this.data = data;
    }

}
