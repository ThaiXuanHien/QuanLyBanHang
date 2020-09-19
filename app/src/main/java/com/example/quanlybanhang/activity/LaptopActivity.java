package com.example.quanlybanhang.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quanlybanhang.R;
import com.example.quanlybanhang.adapter.LaptopAdapter;
import com.example.quanlybanhang.model.SanPham;
import com.example.quanlybanhang.util.CheckConnection;
import com.example.quanlybanhang.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LaptopActivity extends AppCompatActivity {
    Toolbar toolbarLaptop;
    ListView lvLaptop;
    LaptopAdapter laptopAdapter;
    ArrayList<SanPham> listLaptop;

    View progressbar;

    int maLoaiSP = 0;
    int page = 1;

    boolean isLoading = false;
    MyHandler myHandler;
    boolean checkLimitData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop);
        anhXa();

        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            getMaLoaiSP();
            actionToolbar();
            getData(page);
            loadMoreData();
        } else {
            CheckConnection.showToastShort(getApplicationContext(), "Không có kết nối INTERNET");
            finish();
        }
    }

    private void anhXa() {
        toolbarLaptop = findViewById(R.id.toolbarLaptop);
        lvLaptop = findViewById(R.id.listviewLaptop);
        listLaptop = new ArrayList<>();
        laptopAdapter = new LaptopAdapter(getApplicationContext(), listLaptop);
        lvLaptop.setAdapter(laptopAdapter);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        progressbar = layoutInflater.inflate(R.layout.progressbar, null);
        myHandler = new MyHandler();
    }

    private void getMaLoaiSP() {
        maLoaiSP = getIntent().getIntExtra("maloaisp", -1);
        Log.d("gia tri loai san pham", maLoaiSP + "");
    }

    private void actionToolbar() {
        setSupportActionBar(toolbarLaptop);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarLaptop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getData(int page) {
        Context context;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongDanDienThoai = Server.link_dienThoai + String.valueOf(page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongDanDienThoai, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int maSP = 0, gia = 0;
                String tenSanPham = "";
                String moTa = "", hinhAnhSP = "";
                int maLoai = 0;
                if (response != null && response.length() != 2) {
                    lvLaptop.removeFooterView(progressbar);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);
                            maSP = object.getInt("maSP");
                            tenSanPham = object.getString("tenSP");
                            gia = object.getInt("gia");
                            hinhAnhSP = object.getString("hinhSP");
                            moTa = object.getString("moTa");
                            maLoai = object.getInt("maLoaiSP");
                            listLaptop.add(new SanPham(maSP, tenSanPham, gia, hinhAnhSP, moTa, maLoai));
                            laptopAdapter.notifyDataSetChanged();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    checkLimitData = true;
                    lvLaptop.removeFooterView(progressbar);
                    CheckConnection.showToastShort(getApplicationContext(), "No data !");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("maloaisp", String.valueOf(maLoaiSP));
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }

    private void loadMoreData() {
        lvLaptop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ChiTietSanPhamActivity.class);
                intent.putExtra("thongTinSanPham", listLaptop.get(i));
                startActivity(intent);
            }
        });
        lvLaptop.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstItem, int visibleItem, int totalItem) {
                //FirstItem là cái dòng đầu tiên nhìn thấy đc của listview
                //VisibleItem là tổng số dòng mình thấy được của listview
                //TotalItem là tổng số dòng của listview

                // bắt vị trí cuối cùng listview
                if (firstItem + visibleItem == totalItem && totalItem != 0 && isLoading == false && checkLimitData == false) {
                    isLoading = true;
                    MyThread myThread = new MyThread();
                    myThread.start();

                }

            }
        });
    }

    public class MyHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 0:
                    lvLaptop.addFooterView(progressbar);
                    break;
                case 1:
                    getData(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }

    public class MyThread extends Thread {
        @Override
        public void run() {
            myHandler.sendEmptyMessage(0); // gọi đến myHandler -> case 0
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Message message = myHandler.obtainMessage(1); // liên kết các thread vs Handler -> case 1
            myHandler.sendMessage(message);
            super.run();
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
