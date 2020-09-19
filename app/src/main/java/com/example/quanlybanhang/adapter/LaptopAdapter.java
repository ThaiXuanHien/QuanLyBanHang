package com.example.quanlybanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanlybanhang.R;
import com.example.quanlybanhang.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class LaptopAdapter extends BaseAdapter {
    Context context;
    ArrayList<SanPham> listLaptop;

    public LaptopAdapter(Context context, ArrayList<SanPham> listLaptop) {
        this.context = context;
        this.listLaptop = listLaptop;
    }

    @Override
    public int getCount() {
        return listLaptop.size();
    }

    @Override
    public Object getItem(int i) {
        return listLaptop.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        TextView txtTenLaptop,txtGiaLaptop,txtMoTaLaptop;
        ImageView imgLaptop;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(viewHolder == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_laptop, null);
            viewHolder.txtTenLaptop = view.findViewById(R.id.textviewTenLaptop);
            viewHolder.txtGiaLaptop = view.findViewById(R.id.textviewGiaLaptop);
            viewHolder.txtMoTaLaptop = view.findViewById(R.id.textviewMoTLaptop);
            viewHolder.imgLaptop = view.findViewById(R.id.imageviewItemLaptop);
            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) view.getTag();
        }
        SanPham sanPham = (SanPham) getItem(i);
        viewHolder.txtTenLaptop.setText(sanPham.getTenSP());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaLaptop.setText("Giá: " + decimalFormat.format(sanPham.getGia()) + "Đ");
        viewHolder.txtMoTaLaptop.setText(sanPham.getMoTa());
        Picasso.with(context).load(sanPham.getHinhSP()).into(viewHolder.imgLaptop);
        return view;
    }
}
