package com.example.quanlybanhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.quanlybanhang.R;
import com.example.quanlybanhang.model.GioHang;
import com.example.quanlybanhang.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChiTietSanPhamActivity extends AppCompatActivity {

    Toolbar toolbarChiTietSP;
    ImageView imgChiTietSP;
    TextView txtTenSP, txtGiaSP, txtMoTa;
    Button btnGioHang;
    Spinner spnSoLuong;
    int maSP = 0, gia = 0;
    String tenSanPham = "";
    String moTa = "", hinhAnhSP = "";
    int maLoai = 0;
    ArrayList<GioHang> gioHangArrayList = MainActivity.listGioHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        anhXa();
        actionToolbar();
        getInfoSanPham();
        catchEventSpinner();
        eventBtnGioHang();
    }

    private void eventBtnGioHang() {
        btnGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gioHangArrayList.size() > 0) {
                    int sl = Integer.parseInt(spnSoLuong.getSelectedItem().toString());
                    boolean exists = false; // kiem tra ton tai san pham , ton tai thi cap nhat lai thong tin
                    for (int i = 0; i < gioHangArrayList.size(); i++) {
                        if (gioHangArrayList.get(i).getMaSP() == maSP) {
                            gioHangArrayList.get(i).setSoLuong(gioHangArrayList.get(i).getSoLuong() + sl);
                            if (gioHangArrayList.get(i).getSoLuong() >= 10) {
                                gioHangArrayList.get(i).setSoLuong(10);
                            }
                            gioHangArrayList.get(i).setTongTien(gia * gioHangArrayList.get(i).getSoLuong());
                            exists = true;
                        }
                    }
                    if (exists == false) { // neu ko ton tai san pham thi ko cap nhat thong tin
                        int soLuong = Integer.parseInt(spnSoLuong.getSelectedItem().toString());
                        long tongTien = soLuong * gia;
                        gioHangArrayList.add(new GioHang(maSP, tenSanPham, tongTien, hinhAnhSP, soLuong));
                    }
                } else {
                    int soLuong = Integer.parseInt(spnSoLuong.getSelectedItem().toString());
                    long tongTien = soLuong * gia;
                    gioHangArrayList.add(new GioHang(maSP, tenSanPham, tongTien, hinhAnhSP, soLuong));
                }
                Intent intent = new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(intent);

            }

        });
    }

    // set giá trị cho spinner
    private void catchEventSpinner() {
        Integer[] soLuong = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, soLuong);
        spnSoLuong.setAdapter(arrayAdapter);
    }

    private void getInfoSanPham() {
        SanPham sanPham = (SanPham) getIntent().getSerializableExtra("thongTinSanPham");
        maSP = sanPham.getMaSP();
        gia = sanPham.getGia();
        tenSanPham = sanPham.getTenSP();
        hinhAnhSP = sanPham.getHinhSP();
        moTa = sanPham.getMoTa();
        maLoai = sanPham.getMaLoaiSP();
        txtTenSP.setText(tenSanPham);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtGiaSP.setText("Giá: " + decimalFormat.format(gia) + " Đ");
        txtMoTa.setText(moTa);
        Picasso.with(getApplicationContext()).load(hinhAnhSP).into(imgChiTietSP);
    }

    private void actionToolbar() {
        setSupportActionBar(toolbarChiTietSP);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChiTietSP.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void anhXa() {
        toolbarChiTietSP = findViewById(R.id.toolbarChiTietSP);
        imgChiTietSP = findViewById(R.id.imageviewChiTietSP);
        txtTenSP = findViewById(R.id.textviewTenChiTietSP);
        txtGiaSP = findViewById(R.id.textviewGiaChiTietSP);
        txtMoTa = findViewById(R.id.textviewMotaChiTietSP);
        spnSoLuong = findViewById(R.id.spinnerSoLuong);
        btnGioHang = findViewById(R.id.buttonThemGioHang);
    }
}