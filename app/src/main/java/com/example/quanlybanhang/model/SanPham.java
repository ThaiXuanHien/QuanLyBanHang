package com.example.quanlybanhang.model;

import java.io.Serializable;

public class SanPham implements Serializable {


    private int maSP;

    private String tenSP;

    private int gia;

    private String hinhSP;

    private String moTa;

    private int maLoaiSP;

    public SanPham(int maSP, String tenSP, int gia, String hinhSP, String moTa, int maLoaiSP) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.gia = gia;
        this.hinhSP = hinhSP;
        this.moTa = moTa;
        this.maLoaiSP = maLoaiSP;
    }

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getHinhSP() {
        return hinhSP;
    }

    public void setHinhSP(String hinhSP) {
        this.hinhSP = hinhSP;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getMaLoaiSP() {
        return maLoaiSP;
    }

    public void setMaLoaiSP(int maLoaiSP) {
        this.maLoaiSP = maLoaiSP;
    }
}