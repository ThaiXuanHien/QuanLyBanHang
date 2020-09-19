package com.example.quanlybanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanlybanhang.R;
import com.example.quanlybanhang.model.LoaiSP;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoaiSPAdapter extends BaseAdapter {

    ArrayList<LoaiSP> loaiSPArrayList;
    Context context;

    public LoaiSPAdapter(ArrayList<LoaiSP> loaiSPArrayList, Context context) {
        this.loaiSPArrayList = loaiSPArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return loaiSPArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return loaiSPArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder {
        TextView txtTenLoaiSP;
        ImageView imgHinhLoaiSP;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_loaisp, null);
            viewHolder.txtTenLoaiSP = view.findViewById(R.id.textviewTenLoaiSP);
            viewHolder.imgHinhLoaiSP = view.findViewById(R.id.imageviewLoaiSP);
            view.setTag(viewHolder);
        } else {

        }
        viewHolder = (ViewHolder) view.getTag();
        LoaiSP loaiSP = (LoaiSP) getItem(i);
        viewHolder.txtTenLoaiSP.setText(loaiSP.getTenLoaiSP());
        Picasso.with(context).load(loaiSP.getHinhAnhLoaiSP()).into(viewHolder.imgHinhLoaiSP);

        return view;
    }
}
