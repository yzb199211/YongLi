package com.yyy.yongli.storage;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yyy.yongli.R;
import com.yyy.yongli.model.haihong.StorageSearchBean;

import java.util.List;

public class StorageSearchAdapter extends RecyclerView.Adapter<StorageSearchAdapter.VH> {
    Context context;
    List<StorageSearchBean.TablesBean> list;

    public StorageSearchAdapter(Context context, List<StorageSearchBean.TablesBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_storage_search, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.tvName.setText(TextUtils.isEmpty(list.get(position).getSName()) ? "品名：" : "品名：" + list.get(position).getSName());
        holder.tvStore.setText(TextUtils.isEmpty(list.get(position).getSStockName()) ? "仓库：" : "仓库：" + list.get(position).getSStockName());
        holder.tvPos.setText(TextUtils.isEmpty(list.get(position).getSBerChID()) ? "库位：" : "库位：" + list.get(position).getSBerChID());
        holder.tvColor.setText(TextUtils.isEmpty(list.get(position).getSCustColorID()) ? "客户色号：" : "客户色号：" + list.get(position).getSCustColorID());
        holder.tvNum.setText("总卷数：" + list.get(position).getIQty());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class VH extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvStore;
        TextView tvPos;
        TextView tvNum;
        TextView tvColor;

        public VH(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvStore = itemView.findViewById(R.id.tv_store);
            tvPos = itemView.findViewById(R.id.tv_pos);
            tvNum = itemView.findViewById(R.id.tv_num);
            tvColor = itemView.findViewById(R.id.tv_color);
        }
    }
}
