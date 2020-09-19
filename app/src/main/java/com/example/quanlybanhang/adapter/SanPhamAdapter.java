package com.example.quanlybanhang.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlybanhang.R;
import com.example.quanlybanhang.activity.ChiTietSanPhamActivity;
import com.example.quanlybanhang.model.SanPham;
import com.example.quanlybanhang.util.CheckConnection;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {
    ArrayList<SanPham> sanPhamArrayList;
    Context context;

    public SanPhamAdapter(ArrayList<SanPham> sanPhamArrayList, Context context) {
        this.sanPhamArrayList = sanPhamArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_sanpham_moinhat, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SanPham sanPham = sanPhamArrayList.get(position);
        holder.txtTenSPMN.setText(sanPham.getTenSP());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGiaSPMN.setText("Giá: " + decimalFormat.format(sanPham.getGia()) + "Đ");
        Picasso.with(context).load(sanPham.getHinhSP()).into(holder.imgSPMN);
    }

    @Override
    public int getItemCount() {
        return sanPhamArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenSPMN, txtGiaSPMN;
        ImageView imgSPMN;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenSPMN = itemView.findViewById(R.id.textviewTenSPMN);
            txtGiaSPMN = itemView.findViewById(R.id.textviewGiaSPMN);
            imgSPMN = itemView.findViewById(R.id.imageviewSPMN);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ChiTietSanPhamActivity.class);
                    intent.putExtra("thongTinSanPham",sanPhamArrayList.get(getPosition()));
                    CheckConnection.showToastShort(context,sanPhamArrayList.get(getPosition()).getTenSP());
                    context.startActivity(intent);
                }
            });
        }
    }
}
