package com.yyy.yongli.exchange2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yyy.yongli.R;
import com.yyy.yongli.interfaces.OnItemClickListener;
import com.yyy.yongli.model.StorageListOrder;
import com.yyy.yongli.util.StringUtil;

import java.util.List;


public class ExchangeAdapter2 extends RecyclerView.Adapter<ExchangeAdapter2.VH> {
    Context context;
    List<StorageListOrder> list;

    public ExchangeAdapter2(Context context, List<StorageListOrder> list) {
        this.context = context;
        this.list = list;
    }

    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_exchange2, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        try {
            String red;
//            holder.tvCustom.setText(list.get(position).getSCustShortName());
            holder.tvCode.setText(list.get(position).getSBillNo());
            holder.tvDate.setText(StringUtil.getDate(list.get(position).getDDate()));
//            if (list.get(position).getIRed() == 0)
//                red = "";
//            else
//                red = "红冲";
//            holder.tvRed.setText(red);
            holder.tvNum.setText(list.get(position).getIQty() + "");
//            holder.tvStock.setText(list.get(position).getSStockName());
            holder.tvStockIn.setText("调入仓库：" + list.get(position).getsInStockName());
            holder.tvStockOut.setText("调出仓库：" + list.get(position).getsOutStockName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null)
                        onItemClickListener.onItemClick(v, position);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class VH extends RecyclerView.ViewHolder {
        TextView tvCustom;
        //        TextView tvStock;
        TextView tvStockIn;
        TextView tvStockOut;
        TextView tvDate;
        TextView tvRed;
        TextView tvNum;
        TextView tvCode;

        public VH(View v) {
            super(v);
            tvCode = v.findViewById(R.id.tv_code);
            tvCustom = v.findViewById(R.id.tv_customer);
            tvDate = v.findViewById(R.id.tv_date);
            tvNum = v.findViewById(R.id.tv_number);
            tvRed = v.findViewById(R.id.tv_red);
//            tvStock = v.findViewById(R.id.tv_stock);
            tvStockIn = v.findViewById(R.id.tv_stock_in);
            tvStockOut = v.findViewById(R.id.tv_stock_out);
        }
    }
}

