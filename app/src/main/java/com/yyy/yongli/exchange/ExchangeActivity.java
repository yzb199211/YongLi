package com.yyy.yongli.exchange;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.yyy.yongli.R;
import com.yyy.yongli.dialog.LoadingDialog;
import com.yyy.yongli.interfaces.OnItemClickListener;
import com.yyy.yongli.interfaces.OnItemLongClickListener;
import com.yyy.yongli.interfaces.ResponseListener;
import com.yyy.yongli.model.haihong.ExchangeBean;
import com.yyy.yongli.util.NetConfig;
import com.yyy.yongli.util.NetParams;
import com.yyy.yongli.util.NetUtil;
import com.yyy.yongli.util.SharedPreferencesHelper;
import com.yyy.yongli.util.StringUtil;
import com.yyy.yongli.util.Toasts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExchangeActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.rv_exchange)
    RecyclerView rvExchange;
    String url;
    List<ExchangeBean.TablesBean> list;
    LinearLayoutManager layoutManager;
    ExchangeAdapter adapter;

    SharedPreferencesHelper preferencesHelper;

    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);
        ButterKnife.bind(this);
        preferencesHelper = new SharedPreferencesHelper(this, getString(R.string.preferenceCache));
        init();
    }

    private void init() {
        url = NetConfig.url + NetConfig.Pda_Method;
        list = new ArrayList<>();
        tvTitle.setText("成品未提交调拨单");
        userid = (String) preferencesHelper.getSharedPreference("userid", "");
        ivRight.setImageResource(R.mipmap.icon_add);
        layoutManager = new LinearLayoutManager(this);
        rvExchange.setLayoutManager(layoutManager);
        getDate();
    }

    private void getDate() {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(getParams(), url, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                Log.e("data", StringUtil.getHaiHongData(string));
                try {
                    Gson gson = new Gson();
                    ExchangeBean data = gson.fromJson(StringUtil.getHaiHongData(string), ExchangeBean.class);
                    if (data.isSuccess()) {
                        if (data.getTables() != null) {

                            if (data.getTables().get(0) != null && data.getTables().get(0).size() > 0) {
                                list.addAll(data.getTables().get(0));
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        LoadingDialog.cancelDialogForLoading();
                                        refreshList();
                                    }
                                });

                            } else {
                                loadFail("无数据");
                            }
                        } else {
                            loadFail("无数据");
                        }
                    } else {
                        loadFail(data.getMessage());
                    }
                } catch (Exception e) {
                    loadFail("数据解析错误");
                }

            }

            @Override
            public void onFail(IOException e) {
                loadFail("加载失败");
            }
        });
    }

    private void refreshList() {
        if (adapter == null) {
            Log.e("size", list.size() + "");
            adapter = new ExchangeAdapter(this, list);
            rvExchange.setAdapter(adapter);
            adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent();
                    intent.putExtra("id", list.get(position).getIRecNo() + "");
                    intent.putExtra("inputId", list.get(position).getIInBscDataStockMRecNo());
                    intent.putExtra("inputName", list.get(position).getSInStockName());
                    intent.putExtra("outputId", list.get(position).getIOutBscDataStockMRecNo());
                    intent.putExtra("outputName", list.get(position).getSOutStockName());
                    intent.putExtra("count", list.get(position).getICount());
                    intent.putExtra("length", list.get(position).getFQty());
                    intent.putExtra("weight", list.get(position).getFPurQty());
                    intent.setClass(ExchangeActivity.this, ExchangDetailActivity.class);
                    startActivityForResult(intent, 100);
                }
            });
            adapter.setOnItemLongClickListener(new OnItemLongClickListener() {
                @Override
                public void onLongClick(View view, int position) {
                    Log.e("Delete", position + "");
                }
            });
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("userid", userid));
        params.add(new NetParams("otype", "GetPDANotSubmitMMStockProductDb1"));
        params.add(new NetParams("db", "HxHHWk2018New"));
        params.add(new NetParams("callback", ""));
        return params;

    }

    @OnClick({R.id.iv_back, R.id.iv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_right:
                Intent intent = new Intent();
                intent.putExtra("id", "");
                intent.putExtra("inputId", 0);
                intent.putExtra("inputName", "");
                intent.putExtra("outputId", 0);
                intent.putExtra("outputName", "");
                intent.putExtra("count", 0);
                intent.putExtra("length", 0.0);
                intent.putExtra("weight", 0.0);
                intent.setClass(ExchangeActivity.this, ExchangDetailActivity.class);
                startActivityForResult(intent, 100);
                break;
        }
    }

    private void loadFail(String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LoadingDialog.cancelDialogForLoading();
                Toasts.showShort(ExchangeActivity.this, message);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            list.clear();
            refreshList();
            getDate();
        }
    }
}
