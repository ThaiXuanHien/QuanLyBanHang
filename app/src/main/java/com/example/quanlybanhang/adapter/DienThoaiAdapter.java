package com.example.quanlybanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.quanlybanhang.R;
import com.example.quanlybanhang.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DienThoaiAdapter extends BaseAdapter {

    Context context;
    ArrayList<SanPham> listDienThoai;

    public DienThoaiAdapter(Context context, ArrayList<SanPham> listDienThoai) {
        this.context = context;
        this.listDienThoai = listDienThoai;
    }

    @Override
    public int getCount() {
        return listDienThoai.size();
    }

    @Override
    public Object getItem(int i) {
        return listDienThoai.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        TextView txtTenDienThoai,txtGiaDienThoai,txtMoTaDienThoai;
        ImageView imgDienThoai;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(viewHolder == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_dienthoai, null);
            viewHolder.txtTenDienThoai = view.findViewById(R.id.textviewTenDienThoai);
            viewHolder.txtGiaDienThoai = view.findViewById(R.id.textviewGiaDienThoai);
            viewHolder.txtMoTaDienThoai = view.findViewById(R.id.textviewMoTaDienThoai);
            viewHolder.imgDienThoai = view.findViewById(R.id.imageviewItemDienThoai);
            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) view.getTag();
        }
        SanPham sanPham = (SanPham) getItem(i);
        viewHolder.txtTenDienThoai.setText(sanPham.getTenSP());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaDienThoai.setText("Giá: " + decimalFormat.format(sanPham.getGia()) + "Đ");
        viewHolder.txtMoTaDienThoai.setText(sanPham.getMoTa());
        Picasso.with(context).load(sanPham.getHinhSP()).into(viewHolder.imgDienThoai);
        return view;
    }
}
