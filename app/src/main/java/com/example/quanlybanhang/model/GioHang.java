package com.example.quanlybanhang.model;

public class GioHang {
    private int maSP;
    private String tenSP;
    private long tongTien;
    private String hinhSP;
    private int soLuong;

    public GioHang(int maSP, String tenSP, long tongTien, String hinhSP, int soLuong) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.tongTien = tongTien;
        this.hinhSP = hinhSP;
        this.soLuong = soLuong;
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

    public long getTongTien() {
        return tongTien;
    }

    public void setTongTien(long tongTien) {
        this.tongTien = tongTien;
    }

    public String getHinhSP() {
        return hinhSP;
    }

    public void setHinhSP(String hinhSP) {
        this.hinhSP = hinhSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
