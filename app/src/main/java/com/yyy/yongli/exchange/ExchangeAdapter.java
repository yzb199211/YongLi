package com.yyy.yongli.exchange;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yyy.yongli.R;
import com.yyy.yongli.interfaces.OnItemClickListener;
import com.yyy.yongli.interfaces.OnItemLongClickListener;
import com.yyy.yongli.model.haihong.ExchangeBean;

import java.util.List;

public class ExchangeAdapter extends RecyclerView.Adapter<ExchangeAdapter.VH> {
    Context context;
    List<ExchangeBean.TablesBean> list;
    OnItemClickListener onItemClickListener;
    OnItemLongClickListener onItemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public ExchangeAdapter(Context context, List<ExchangeBean.TablesBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_exchange, parent, false);
        return new ExchangeAdapter.VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        try {
            ExchangeBean.TablesBean bean = list.get(position);
            holder.tvCode.setText(TextUtils.isEmpty(bean.getSBillNo()) ? "单号：" : "单号：" + bean.getSBillNo());
            holder.tvDate.setText(TextUtils.isEmpty(bean.getSDateStr()) ? "日期：" : "日期：" + bean.getSDateStr());
            holder.tvInputUser.setText(TextUtils.isEmpty(bean.getSUserName()) ? "制单人：" : "制单人：" + bean.getSUserName());
            holder.tvNum.setText("总匹数：" + bean.getICount());
            holder.tvTotal.setText("总米数：" + bean.getFQty());
            holder.tvInputDate.setText("总重量：" + bean.getFPurQty());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, position);
                    }
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (onItemLongClickListener != null) {
                        onItemLongClickListener.onLongClick(v, position);
                    }
                    return false;
                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
            Log.e("error", "空指针");
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class VH extends RecyclerView.ViewHolder {
        TextView tvCode;
        TextView tvDate;
        TextView tvNum;
        TextView tvTotal;
        TextView tvInputDate;
        TextView tvInputUser;

        public VH(View v) {
            super(v);
            tvCode = v.findViewById(R.id.tv_code);
            tvDate = v.findViewById(R.id.tv_date);
            tvNum = v.findViewById(R.id.tv_num);
            tvTotal = v.findViewById(R.id.tv_total);
            tvInputDate = v.findViewById(R.id.tv_input_date);
            tvInputUser = v.findViewById(R.id.tv_input_user);

        }
    }
}
