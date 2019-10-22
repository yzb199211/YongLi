package com.yyy.yongli.input;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.yyy.yongli.R;
import com.yyy.yongli.interfaces.OnItemClickListener;
import com.yyy.yongli.model.StorageScanBean;

import java.util.List;

public class InputAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<StorageScanBean> list;
    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(@Nullable OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public InputAdapter(Context context, List<StorageScanBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_input, parent, false);
            return new VH(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_scan1, parent, false);
            return new VH1(view);
        }

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        try {
            if (list.get(position).getType() == 1) {
                VH holder = (VH) viewHolder;
                holder.tvTitle.setText("规格：" + list.get(position).getsElements());
                holder.tvBatch.setText("批次：" + list.get(position).getsBatchNo());

                holder.tvTS.setText("TS：" + list.get(position).getfTS());
                holder.tvTC.setText("TC：" + list.get(position).getfTC());
                holder.tvPos.setText("仓位：" + (TextUtils.isEmpty(list.get(position).getsBerChID()) ? "" : list.get(position).getsBerChID()));

                holder.tvResistance.setText("电阻：" + list.get(position).getfResistance());
                holder.tvVoltage.setText("工作电压：" + list.get(position).getfVoltage());
                holder.tvVoltageResistance.setText("耐电压：" + list.get(position).getfVoltageResistance());

                holder.tvCurrent.setText("电流：" + list.get(position).getfCurrent());
                holder.tvElectrode.setText("电极：" + list.get(position).getsElectrode());
                holder.tvCount.setText("片数：" + list.get(position).getiQty());

                holder.tvCode.setText("大条码：" + list.get(position).getsInBarCode());
                holder.tvSmallCode.setText("小条码：" + list.get(position).getsBarCode());
                holder.tvOrder.setText("订单号：" + list.get(position).getsOrderNo());
                holder.tvAdajustTs.setText("TS±：" + list.get(position).getfAdjustTS());


                holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.white));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null)
                            onItemClickListener.onItemClick(v, position);
                    }
                });
            } else if (list.get(position).getType() == 0) {
                VH1 holder = (VH1) viewHolder;
                holder.tvCode.setText("条码：" + list.get(position).getsBarCode());
                holder.tvCount.setText("仓位：" + list.get(position).getsBerChID());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null)
                            onItemClickListener.onItemClick(v, position);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }

    public static class VH extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvTC;
        TextView tvTS;
        TextView tvPos;
        TextView tvCode;
        TextView tvBatch;
        TextView tvResistance;
        TextView tvVoltage;

        TextView tvVoltageResistance;
        TextView tvCurrent;
        TextView tvCount;
        TextView tvElectrode;

        TextView tvOrder;
        TextView tvSmallCode;
        TextView tvAdajustTs;
        LinearLayout llOne;
        LinearLayout llTwo;

        public VH(View v) {
            super(v);

            tvBatch = v.findViewById(R.id.tv_batch_no);
            tvTitle = v.findViewById(R.id.tv_title);

            tvSmallCode = v.findViewById(R.id.tv_small_code);
            tvPos = v.findViewById(R.id.tv_pos);

            tvTC = v.findViewById(R.id.tv_tc);
            tvAdajustTs = v.findViewById(R.id.tv_ts_adjuset);
            tvTS = v.findViewById(R.id.tv_ts);


            tvVoltage = v.findViewById(R.id.tv_voltage);
            tvResistance = v.findViewById(R.id.tv_resistance);
            tvVoltageResistance = v.findViewById(R.id.tv_voltage_resistance);

            tvCurrent = v.findViewById(R.id.tv_current);
            tvElectrode = v.findViewById(R.id.tv_electrode);
            tvCount = v.findViewById(R.id.tv_count);

            tvCode = v.findViewById(R.id.tv_code);

            tvOrder = v.findViewById(R.id.tv_order);


            llOne = v.findViewById(R.id.ll_one);
            llTwo = v.findViewById(R.id.ll_two);
        }
    }

    public static class VH1 extends RecyclerView.ViewHolder {
        TextView tvCode;
        TextView tvCount;

        public VH1(View v) {
            super(v);
            tvCode = v.findViewById(R.id.tv_code);
            tvCount = v.findViewById(R.id.tv_count);

        }
    }

}
