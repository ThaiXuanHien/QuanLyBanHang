package com.example.quanlybanhang.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quanlybanhang.R;
import com.example.quanlybanhang.util.CheckConnection;
import com.example.quanlybanhang.util.Server;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ThongTinKhachHangActivity extends AppCompatActivity {

    EditText edtTenKhachHang, edtEmail, editSDT;
    Button btnXacNhan, btnTroVe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_khach_hang);

        anhXa();

        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            xacNhan();
        } else {
            CheckConnection.showToastShort(getApplicationContext(), "No Internet !");
        }
    }

    private void xacNhan() {
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String tenKhachHang = edtTenKhachHang.getText().toString().trim();
                final String sdt = editSDT.getText().toString().trim();
                final String email = edtEmail.getText().toString().trim();
                if ((!tenKhachHang.isEmpty() && !email.isEmpty() && !sdt.isEmpty())) {
                    Context context;
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.link_donhang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String madh) {
                            // response trả về mã đơn hàng  - > chuyển response thành madh
                            if (Integer.parseInt(madh) > 0) {
                                RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
                                StringRequest stringRequest1 = new StringRequest(StringRequest.Method.POST, Server.link_ctDonhang, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (response.equals("1")) {
                                            MainActivity.listGioHang.clear();
                                            CheckConnection.showToastShort(getApplicationContext(), "Thành công");
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                        } else {
                                            CheckConnection.showToastShort(getApplicationContext(), "Lỗi");
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray = new JSONArray();
                                        for (int i = 0; i < MainActivity.listGioHang.size(); i++) {
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("madh", madh);
                                                jsonObject.put("masp", MainActivity.listGioHang.get(i).getMaSP());
                                                jsonObject.put("tensp", MainActivity.listGioHang.get(i).getTenSP());
                                                jsonObject.put("tongtien", MainActivity.listGioHang.get(i).getTongTien());
                                                jsonObject.put("soluong", MainActivity.listGioHang.get(i).getSoLuong());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String, String> hashMap = new HashMap<>();
                                        hashMap.put("json", jsonArray.toString());
                                        return hashMap;
                                    }
                                };
                                requestQueue1.add(stringRequest1);
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            HashMap<String, String> map = new HashMap<>();
                            map.put("tenKhachHang", tenKhachHang);
                            map.put("email", email);
                            map.put("sdt", sdt);

                            return map;
                        }
                    };
                    requestQueue.add(stringRequest);
                } else {
                    CheckConnection.showToastShort(getApplicationContext(), "Dữ liệu ko hợp lệ !");
                }
            }
        });
    }

    private void anhXa() {
        edtTenKhachHang = findViewById(R.id.edittextNhapTen);
        editSDT = findViewById(R.id.edittextNhapSDT);
        edtEmail = findViewById(R.id.edittextNhapEmail);
        btnXacNhan = findViewById(R.id.buttonXacNhan);
        btnTroVe = findViewById(R.id.buttonTroVe);
    }
}