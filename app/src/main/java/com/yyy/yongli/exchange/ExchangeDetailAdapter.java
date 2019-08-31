package com.yyy.yongli.exchange;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yyy.yongli.R;
import com.yyy.yongli.model.haihong.ExchangeDetailBean;

import java.util.List;

public class ExchangeDetailAdapter extends RecyclerView.Adapter<ExchangeDetailAdapter.VH> {
    Context context;
    List<ExchangeDetailBean.TablesBean> list;

    public ExchangeDetailAdapter(Context context, List<ExchangeDetailBean.TablesBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override

    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_exchange_detail, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        try {
            holder.tvCode.setText(TextUtils.isEmpty(list.get(position).getSBarCode()) ? "条码：" : "条码：" + list.get(position).getSBarCode());
            holder.tvTray.setText(TextUtils.isEmpty(list.get(position).getSTrayCode()) ? "托盘号：" : "托盘号：" + list.get(position).getSTrayCode());
            holder.tvName.setText(TextUtils.isEmpty(list.get(position).getSName()) ? "品名：" : "品名：" + list.get(position).getSName());
            holder.tvColor.setText(TextUtils.isEmpty(list.get(position).getSColorName()) ? "色号：" : "色号：" + list.get(position).getSColorName());

            holder.tvLength.setText("米数：" + list.get(position).getFQty() + "");
            holder.tvWeight.setText("重量：" + list.get(position).getFPurQty() + "");
            holder.tvOldPos.setText(TextUtils.isEmpty(list.get(position).getSOutBerChID()) ? "仓位：" : "仓位：" + list.get(position).getSOutBerChID());
            holder.tvOrderNo.setText(TextUtils.isEmpty(list.get(position).getSOutOrderNo()) ? "批次订单号：" : "批次订单号：" + list.get(position).getSOutOrderNo());
            holder.tvBatchNo.setText(TextUtils.isEmpty(list.get(position).getSBatchNo()) ? "缸号：" : "缸号：" + list.get(position).getSBatchNo());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class VH extends RecyclerView.ViewHolder {
        TextView tvCode;
        TextView tvTray;
        TextView tvName;
        TextView tvColor;
        TextView tvLength;
        TextView tvWeight;
        TextView tvOldPos;
        TextView tvOrderNo;
        TextView tvBatchNo;

        public VH(View v) {
            super(v);
            tvCode = v.findViewById(R.id.tv_code);
            tvTray = v.findViewById(R.id.tv_Tray);
            tvName = v.findViewById(R.id.tv_name);
            tvColor = v.findViewById(R.id.tv_color);
            tvLength = v.findViewById(R.id.tv_length);
            tvWeight = v.findViewById(R.id.tv_weight);
            tvOldPos = v.findViewById(R.id.tv_old_pos);
            tvOrderNo = v.findViewById(R.id.tv_order_no);
            tvBatchNo = v.findViewById(R.id.tv_batch_no);
        }
    }
}
