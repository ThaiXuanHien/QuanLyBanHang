package com.example.quanlybanhang.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.quanlybanhang.R;
import com.example.quanlybanhang.adapter.LoaiSPAdapter;
import com.example.quanlybanhang.adapter.SanPhamAdapter;
import com.example.quanlybanhang.model.GioHang;
import com.example.quanlybanhang.model.LoaiSP;
import com.example.quanlybanhang.model.SanPham;
import com.example.quanlybanhang.util.CheckConnection;
import com.example.quanlybanhang.util.Server;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbarTrangChu;
    RecyclerView rcvSanPhamMoiNhat;
    ViewFlipper viewFlipperTrangChu;
    ListView lvNav;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ArrayList<LoaiSP> loaiSPArrayList;
    LoaiSPAdapter loaiSPAdapter;
    ArrayList<SanPham> sanPhamArrayList;
    SanPhamAdapter sanPhamAdapter;

    public static ArrayList<GioHang> listGioHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            actionBar();
            actionViewFlipper();
            getDataLoaiSP();
            getDataSanPhamMoiNhat();
            catchOnItemListView();
        } else {
            CheckConnection.showToastShort(getApplicationContext(), "Không có kết nối INTERNET");
            finish();
        }
    }

    private void catchOnItemListView() {
        lvNav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, DienThoaiActivity.class);
                            intent.putExtra("maloaisp", loaiSPArrayList.get(i).getMaLoaiSP());
                            startActivity(intent);
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, LaptopActivity.class);
                            intent.putExtra("maloaisp", loaiSPArrayList.get(i).getMaLoaiSP());
                            startActivity(intent);
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, LienHeActivity.class);

                            startActivity(intent);
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, ThongTinActivity.class);

                            startActivity(intent);
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void getDataSanPhamMoiNhat() {
        Context context;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.link_GetSPMoiNhat,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response != null) {
                            int maSP = 0, gia = 0;
                            String tenSanPham = "";
                            String moTa = "", hinhAnhSP = "";
                            int maLoai = 0;
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject object = response.getJSONObject(i);
                                    maSP = object.getInt("maSP");
                                    tenSanPham = object.getString("tenSP");
                                    gia = object.getInt("gia");
                                    hinhAnhSP = object.getString("hinhSP");
                                    moTa = object.getString("moTa");
                                    maLoai = object.getInt("maLoaiSP");
                                    sanPhamArrayList.add(new SanPham(maSP, tenSanPham, gia, hinhAnhSP, moTa, maLoai));
                                    sanPhamAdapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void getDataLoaiSP() {
        Context context;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.link_GetLoaiSP, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            int maLoaiSP = 0;
                            String tenLoaiSP = "";
                            String hinhAnhLoaiSP = "";
                            JSONObject jsonObject = response.getJSONObject(i);
                            maLoaiSP = jsonObject.getInt("maLoaiSP");
                            tenLoaiSP = jsonObject.getString("tenLoaiSP");
                            hinhAnhLoaiSP = jsonObject.getString("hinhAnhLoaiSP");
                            loaiSPArrayList.add(new LoaiSP(maLoaiSP, tenLoaiSP, hinhAnhLoaiSP));
                            loaiSPAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    loaiSPArrayList.add(3, new LoaiSP(0, "Liên hệ", "https://www.pikpng.com/pngl/m/115-1157183_contacts-transparent-icons-png-download-contact-us-icon.png"));
                    loaiSPArrayList.add(4, new LoaiSP(0, "Thông tin", "https://p7.hiclipart.com/preview/130/123/677/computer-icons-information-icon-design-info-icon.jpg"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.showToastShort(getApplicationContext(), error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void actionViewFlipper() {
        ArrayList<String> listQuangCao = new ArrayList<>();
        listQuangCao.add("https://cdn.tgdd.vn/2020/09/banner/800-300-800x300-7.png");
        listQuangCao.add("https://cdn.tgdd.vn/2020/09/banner/800-300-800x300-11.png");
        listQuangCao.add("https://cdn.tgdd.vn/2020/09/banner/800-300-800x300-6.png");
        listQuangCao.add("https://cdn.tgdd.vn/2020/08/banner/laptop-chung-800-170-800x170.png");
        listQuangCao.add("https://cdn.tgdd.vn/2020/07/banner/800-170-800x170-72.png");
        listQuangCao.add("https://cdn.tgdd.vn/2020/09/banner/800-170-800x170-1.png");
        for (int i = 0; i < listQuangCao.size(); i++) {
            ImageView imageViewQuangCao = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(listQuangCao.get(i)).into(imageViewQuangCao);

            imageViewQuangCao.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipperTrangChu.addView(imageViewQuangCao);
        }
        viewFlipperTrangChu.setFlipInterval(5000);
        Animation animation_SlideInRight = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animation_SlideOutRight = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipperTrangChu.setInAnimation(animation_SlideInRight);
        viewFlipperTrangChu.setInAnimation(animation_SlideOutRight);

        viewFlipperTrangChu.setAutoStart(true);

    }

    private void actionBar() {
        setSupportActionBar(toolbarTrangChu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarTrangChu.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbarTrangChu.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void anhXa() {
        drawerLayout = findViewById(R.id.drawerlayout);
        toolbarTrangChu = findViewById(R.id.toolbarManHinhTrangChu);
        viewFlipperTrangChu = findViewById(R.id.viewflipperTrangChu);
        rcvSanPhamMoiNhat = findViewById(R.id.recycleviewSanPhamMoiNhat);
        lvNav = findViewById(R.id.listviewNav);
        navigationView = findViewById(R.id.navigationview);
        loaiSPArrayList = new ArrayList<>();
        loaiSPArrayList.add(0, new LoaiSP(0, "Trang chủ", "https://toppng.com/uploads/preview/home-icon-home-house-icon-house-icon-free-11553508857ouiuhg9nsa.png"));
        loaiSPAdapter = new LoaiSPAdapter(loaiSPArrayList, getApplicationContext());
        lvNav.setAdapter(loaiSPAdapter);
        sanPhamArrayList = new ArrayList<>();
        sanPhamAdapter = new SanPhamAdapter(sanPhamArrayList, getApplicationContext());
        rcvSanPhamMoiNhat.setHasFixedSize(true);
        rcvSanPhamMoiNhat.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        rcvSanPhamMoiNhat.setAdapter(sanPhamAdapter);
        if (listGioHang != null) {

        } else {
            listGioHang = new ArrayList<>();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_giohang:
                Intent intent = new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
