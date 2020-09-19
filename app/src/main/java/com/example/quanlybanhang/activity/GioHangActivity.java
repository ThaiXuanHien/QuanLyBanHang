package com.example.quanlybanhang.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.quanlybanhang.R;
import com.example.quanlybanhang.adapter.GioHangAdapter;
import com.example.quanlybanhang.util.CheckConnection;

import java.text.DecimalFormat;


public class GioHangActivity extends AppCompatActivity {

    ListView lvGioHang;
    TextView txtThongBao;
    static TextView txtTongTien;
    Button btnThanhToan, btnTiepTuc;
    Toolbar toolbarGioHang;
    GioHangAdapter gioHangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        anhXa();
        actionToolbar();
        checkData();
        eventUtil();
        eventButton();
        xoaItemListView();
    }

    private void xoaItemListView() {
        lvGioHang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {


                AlertDialog.Builder builder = new AlertDialog.Builder(GioHangActivity.this);
                builder.setTitle("Xóa Sản Phẩm");
                builder.setMessage("Bạn có muốn Xóa Sản Phẩm này ?");
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (MainActivity.listGioHang.size() <= 0) {
                            txtThongBao.setVisibility(View.VISIBLE);
                        } else {
                            MainActivity.listGioHang.remove(position);
                            gioHangAdapter.notifyDataSetChanged();
                            eventUtil();
                            if (MainActivity.listGioHang.size() <= 0) {
                                txtThongBao.setVisibility(View.VISIBLE);
                            } else {
                                txtThongBao.setVisibility(View.INVISIBLE);
                                gioHangAdapter.notifyDataSetChanged();
                                eventUtil();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        gioHangAdapter.notifyDataSetChanged();
                        eventUtil();
                    }
                });
                AlertDialog alertDialog = builder.create();
                builder.show();
                return true;
            }
        });
    }

    private void eventButton() {
        btnTiepTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // kiem tra gio giang co san pham moi cho thanh toan
                if (MainActivity.listGioHang.size() > 0) {
                    Intent intent = new Intent(getApplicationContext(), ThongTinKhachHangActivity.class);
                    startActivity(intent);
                } else {
                    CheckConnection.showToastShort(getApplicationContext(), "Giỏ hàng chưa có sản phẩm");
                }
            }
        });
    }

    // tính tổng tiền cho txtTongtien
    public static void eventUtil() {
        long tongTien = 0;
        for (int i = 0; i < MainActivity.listGioHang.size(); i++) {
            tongTien += MainActivity.listGioHang.get(i).getTongTien();
        }
        DecimalFormat decimalFomat = new DecimalFormat("###,###,###");
        txtTongTien.setText(decimalFomat.format(tongTien) + " Đ");
    }

    // chưa có sản phẩm thì ko hiện listview, và hiện thông báo chưa có sản phẩm
    private void checkData() {
        if (MainActivity.listGioHang.size() <= 0) {
            gioHangAdapter.notifyDataSetChanged();
            txtThongBao.setVisibility(View.VISIBLE);
            lvGioHang.setVisibility(View.INVISIBLE);
        } else {
            gioHangAdapter.notifyDataSetChanged();
            txtThongBao.setVisibility(View.INVISIBLE);
            lvGioHang.setVisibility(View.VISIBLE);
        }
    }

    private void actionToolbar() {
        setSupportActionBar(toolbarGioHang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbarGioHang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void anhXa() {
        lvGioHang = findViewById(R.id.listviewGioHang);
        txtThongBao = findViewById(R.id.textviewThongBao);
        txtTongTien = findViewById(R.id.textviewValueTongTien);
        btnThanhToan = findViewById(R.id.buttonThanhToan);
        btnTiepTuc = findViewById(R.id.buttonTiepTucMuaHang);
        toolbarGioHang = findViewById(R.id.toolbarGioHang);
        gioHangAdapter = new GioHangAdapter(MainActivity.listGioHang, this);
        lvGioHang.setAdapter(gioHangAdapter);
    }
}