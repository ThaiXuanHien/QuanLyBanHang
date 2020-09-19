package com.example.quanlybanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanlybanhang.R;
import com.example.quanlybanhang.activity.GioHangActivity;
import com.example.quanlybanhang.activity.MainActivity;
import com.example.quanlybanhang.model.GioHang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangAdapter extends BaseAdapter {

    ArrayList<GioHang> gioHangArrayList;
    Context context;

    public GioHangAdapter(ArrayList<GioHang> gioHangArrayList, Context context) {
        this.gioHangArrayList = gioHangArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return gioHangArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return gioHangArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder {
        TextView txtTenSPGioHang, txtGiaSPGioHang;
        ImageView imgSPGioHang;
        Button btnMinus, btnPlus, btnValue;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_giohang, null);

            viewHolder.txtTenSPGioHang = view.findViewById(R.id.textviewTenSPGioHang);
            viewHolder.txtGiaSPGioHang = view.findViewById(R.id.textviewGiaSPGioHang);
            viewHolder.imgSPGioHang = view.findViewById(R.id.imageviewItemGioHang);
            viewHolder.btnMinus = view.findViewById(R.id.buttonMinus);
            viewHolder.btnPlus = view.findViewById(R.id.buttonPlus);
            viewHolder.btnValue = view.findViewById(R.id.buttonValue);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        GioHang gioHang = (GioHang) getItem(i);
        viewHolder.txtTenSPGioHang.setText(gioHang.getTenSP());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaSPGioHang.setText("Giá: " + decimalFormat.format(gioHang.getTongTien()) + "Đ");
        Picasso.with(context).load(gioHang.getHinhSP()).into(viewHolder.imgSPGioHang);
        viewHolder.btnValue.setText(gioHang.getSoLuong() + "");

        int soLuong = Integer.parseInt(viewHolder.btnValue.getText().toString());
        if (soLuong >= 10) {
            viewHolder.btnPlus.setVisibility(View.INVISIBLE);
            viewHolder.btnMinus.setVisibility(View.VISIBLE);
        }
        if (soLuong == 1) {
            viewHolder.btnPlus.setVisibility(View.VISIBLE);
            viewHolder.btnMinus.setVisibility(View.INVISIBLE);
        }
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int soLuongMoiNhat = Integer.parseInt(finalViewHolder.btnValue.getText().toString()) + 1;
                int soLuongHienTai = MainActivity.listGioHang.get(i).getSoLuong();
                long tongTienHienTai = MainActivity.listGioHang.get(i).getTongTien();
                MainActivity.listGioHang.get(i).setSoLuong(soLuongMoiNhat);
                long tongTienMoiNhat = (soLuongMoiNhat * tongTienHienTai) / soLuongHienTai;
                MainActivity.listGioHang.get(i).setTongTien(tongTienMoiNhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtGiaSPGioHang.setText("Giá: " + decimalFormat.format(tongTienMoiNhat) + "Đ");
                GioHangActivity.eventUtil();
                if (soLuongMoiNhat > 9) {
                    finalViewHolder.btnPlus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnMinus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValue.setText(soLuongMoiNhat + "");

                } else {
                    finalViewHolder.btnPlus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnMinus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValue.setText(soLuongMoiNhat + "");
                }
            }
        });
        viewHolder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int soLuongMoiNhat = Integer.parseInt(finalViewHolder.btnValue.getText().toString()) - 1;
                int soLuongHienTai = MainActivity.listGioHang.get(i).getSoLuong();
                long tongTienHienTai = MainActivity.listGioHang.get(i).getTongTien();
                MainActivity.listGioHang.get(i).setSoLuong(soLuongMoiNhat);
                long tongTienMoiNhat = (soLuongMoiNhat * tongTienHienTai) / soLuongHienTai;
                MainActivity.listGioHang.get(i).setTongTien(tongTienMoiNhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtGiaSPGioHang.setText("Giá: " + decimalFormat.format(tongTienMoiNhat) + "Đ");
                GioHangActivity.eventUtil();
                if (soLuongMoiNhat < 2) {
                    finalViewHolder.btnPlus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnMinus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnValue.setText(soLuongMoiNhat + "");

                } else {
                    finalViewHolder.btnPlus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnMinus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValue.setText(soLuongMoiNhat + "");
                }
            }
        });
        return view;
    }
}
