package com.example.quanlybanhang.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoaiSP {

    private int maLoaiSP;

    private String tenLoaiSP;

    private String hinhAnhLoaiSP;

    public LoaiSP(int maLoaiSP, String tenLoaiSP, String hinhAnhLoaiSP) {
        this.maLoaiSP = maLoaiSP;
        this.tenLoaiSP = tenLoaiSP;
        this.hinhAnhLoaiSP = hinhAnhLoaiSP;
    }

    public int getMaLoaiSP() {
        return maLoaiSP;
    }

    public void setMaLoaiSP(int maLoaiSP) {
        this.maLoaiSP = maLoaiSP;
    }

    public String getTenLoaiSP() {
        return tenLoaiSP;
    }

    public void setTenLoaiSP(String tenLoaiSP) {
        this.tenLoaiSP = tenLoaiSP;
    }

    public String getHinhAnhLoaiSP() {
        return hinhAnhLoaiSP;
    }

    public void setHinhAnhLoaiSP(String hinhAnhLoaiSP) {
        this.hinhAnhLoaiSP = hinhAnhLoaiSP;
    }
}