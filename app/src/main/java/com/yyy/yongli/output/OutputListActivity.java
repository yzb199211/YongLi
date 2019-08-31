package com.yyy.yongli.output;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.yyy.yongli.R;
import com.yyy.yongli.dialog.LoadingDialog;
import com.yyy.yongli.interfaces.OnItemClickListener;
import com.yyy.yongli.interfaces.ResponseListener;
import com.yyy.yongli.model.StorageList;
import com.yyy.yongli.model.StorageListOrder;
import com.yyy.yongli.util.CodeConfig;
import com.yyy.yongli.util.NetConfig;
import com.yyy.yongli.util.NetParams;
import com.yyy.yongli.util.NetUtil;
import com.yyy.yongli.util.SharedPreferencesHelper;
import com.yyy.yongli.util.Toasts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OutputListActivity extends Activity {
    private final static String TAG = "StorageListActivity";
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.rv_storage)
    RecyclerView rvStorage;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    @BindView(R.id.fl_empty)
    FrameLayout flEmpty;
    SharedPreferencesHelper preferencesHelper;
    String userID;
    String url;

    OutputAdapter outputAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<StorageListOrder> list;
    int listPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output_list);
        ButterKnife.bind(this);
        preferencesHelper = new SharedPreferencesHelper(this, getString(R.string.preferenceCache));
//        userID = (String) preferencesHelper.getSharedPreference("userid", "");
        userID = "master";
        init();
    }

    private void init() {
//        String head = (String) preferencesHelper.getSharedPreference("url", "");
//        if (StringUtil.isNotEmpty(head)) {
//            url = head + "/" + NetConfig.Pda_Method;
//        }
        url = NetConfig.url + NetConfig.Pda_Method;
        list = new ArrayList<>();
        tvTitle.setText("出库单");

        ivRight.setImageResource(R.mipmap.icon_add);
        layoutManager = new LinearLayoutManager(this);
        rvStorage.setLayoutManager(layoutManager);
        getData();
    }

    /**
     * 获取列表数据
     */
    private void getData() {
//        Log.e(TAG, "DO");
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(getParams(), url, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                try {
                    Log.e(TAG, string);
                    StorageList storageList = new Gson().fromJson(string, StorageList.class);
                    if (storageList.isSuccess()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                LoadingDialog.cancelDialogForLoading();
                                setSuccess();
                                list.clear();
                                list.addAll(storageList.getDataset().getListProductM());
                                setData();
                            }
                        });
                    } else {
                        failLoad(storageList.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    failLoad("加载失败");
                }
            }

            @Override
            public void onFail(IOException e) {
                failLoad("加载失败");
            }
        });

    }

    /**
     * 加载列表数据
     */
    private void setData() {
        if (outputAdapter != null) {
            outputAdapter.notifyDataSetChanged();
            Log.e(TAG, list.size() + "");
        } else {
            outputAdapter = new OutputAdapter(this, list);
            outputAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    listPosition = position;
                    String data = new Gson().toJson(list.get(position));
                    Intent intent = new Intent();
                    intent.putExtra("data", data);
                    intent.setClass(OutputListActivity.this, OutputDetailActivity.class);
                    startActivityForResult(intent, 0);
                }
            });
            rvStorage.setAdapter(outputAdapter);
        }
    }


    /**
     * 获取列表数据参数
     *
     * @return
     */
    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("sTableName", "MMProductOutM"));
        params.add(new NetParams("otype", "GetListProductM"));
        params.add(new NetParams("sUserID", userID));
//        params.add(new NetParams("sCompanyCode", (String) preferencesHelper.getSharedPreference("db", "")));
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
                listPosition = -1;
                intent.setClass(OutputListActivity.this, OutputDetailActivity.class);
                startActivityForResult(intent, 1);
                break;
            default:
                break;
        }
    }

    /**
     * 加载失败
     *
     * @param message
     */
    private void loadFail(String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LoadingDialog.cancelDialogForLoading();
                Toasts.showShort(OutputListActivity.this, message);
            }
        });
    }

    /**
     * 加载失败
     *
     * @param message
     */
    private void failLoad(String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LoadingDialog.cancelDialogForLoading();
                Toasts.showShort(OutputListActivity.this, message);
                setEmpty();
            }
        });
    }

    /**
     * 设置加载失败布局
     */
    private void setEmpty() {
        flEmpty.setVisibility(View.VISIBLE);
        rvStorage.setVisibility(View.GONE);
        tvEmpty.setText(getString(R.string.refresh));
    }

    /**
     * 设置加载成功布局
     */
    private void setSuccess() {
        flEmpty.setVisibility(View.GONE);
        rvStorage.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, resultCode + "");
        if (resultCode == CodeConfig.DELETECODE) {
            if (listPosition != -1) {
                list.remove(listPosition);
                outputAdapter.notifyItemRemoved(listPosition);
                outputAdapter.notifyDataSetChanged();
            }
        } else if (resultCode == CodeConfig.REFRESHCODE) {
            getData();
        }
    }


}
