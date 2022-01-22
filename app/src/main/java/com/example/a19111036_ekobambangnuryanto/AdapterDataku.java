package com.example.a19111036_ekobambangnuryanto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterDataku extends RecyclerView.Adapter<AdapterDataku.ViewHolder> {
    Context context;
    List<Dataku> list;

    onCallBack onCallBack;

    public void setOnCallBack(AdapterDataku.onCallBack onCallBack) {
        this.onCallBack = onCallBack;
    }

    public AdapterDataku(Context context, List<Dataku> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_data_leot, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_id.setText(" ID Barang         : " + list.get(position).getId());
        holder.tv_name.setText(" Nama Barang  : " + list.get(position).getName());
        holder.tv_type.setText(" Jenis Kain        : " + list.get(position).getType());
        holder.tv_price.setText(" Harga               : " + list.get(position).getPrice());

        holder.tblHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCallBack.onTblHapus(list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_id, tv_name, tv_type, tv_price;
        ImageButton tblHapus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_id = itemView.findViewById(R.id.tv_id);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_type = itemView.findViewById(R.id.tv_type);
            tv_price = itemView.findViewById(R.id.tv_price);

            tblHapus = itemView.findViewById(R.id.tbl_hapus);
        }
    }

    public interface onCallBack {
        void onTblHapus(Dataku dataku);
    }
}
