package com.yyy.yongli.scan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.yyy.yongli.R;
import com.yyy.yongli.dialog.LoadingDialog;
import com.yyy.yongli.input.InputAdapter;
import com.yyy.yongli.input.InputAddActivity;
import com.yyy.yongli.interfaces.OnItemClickListener;
import com.yyy.yongli.interfaces.ResponseListener;
import com.yyy.yongli.model.StorageScan;
import com.yyy.yongli.model.StorageScanBean;
import com.yyy.yongli.util.NetConfig;
import com.yyy.yongli.util.NetParams;
import com.yyy.yongli.util.NetUtil;
import com.yyy.yongli.util.SharedPreferencesHelper;
import com.yyy.yongli.util.StringUtil;
import com.yyy.yongli.util.Toasts;
import com.yyy.yongli.view.MyRecyclerViewDivider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScanNewActivity extends AppCompatActivity {
    private static final String TAG = "ScanNewActivity";
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.rv_scan)
    RecyclerView rvScan;
    @BindView(R.id.tv_total)
    TextView tvTotal;

    String userid;
    String url;
    String tableName;

    int mainId;
    int stockId;

    InputAdapter mAdapter;
    SharedPreferencesHelper sharedPreferencesHelper;

    List<StorageScanBean> products;
    Set<String> codes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_new);
        ButterKnife.bind(this);
        sharedPreferencesHelper = new SharedPreferencesHelper(this, getString(R.string.preferenceCache));
        init();
    }

    private void init() {
        initView();
        initList();
        getDefaultData();
        getData();

    }

    private void initList() {
        codes = new HashSet<>();
        products = new ArrayList<>();
    }


    private void initView() {
        tvTitle.setText("扫描条码");
        ivRight.setVisibility(View.GONE);
        rvScan.setLayoutManager(new LinearLayoutManager(this));
        rvScan.addItemDecoration(new MyRecyclerViewDivider(this, LinearLayoutManager.VERTICAL));
        setCodeListener();
    }

    private void setCodeListener() {
        etCode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_ENTER || event.getScanCode() == 148) && event.getAction() == KeyEvent.ACTION_UP) {
                    getCodeData(etCode.getText().toString());
                }
                return false;
            }
        });

    }

    private List<NetParams> getCodeparams(String code) {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("otype", "GetsBarCode"));
        params.add(new NetParams("sbarcode", code));
        params.add(new NetParams("sTableName", tableName));
        params.add(new NetParams("iBscDataStockMRecNo", stockId + ""));
        return params;
    }

    private void getCodeData(String code) {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(getCodeparams(code), url, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                Log.e("data", string);
                StorageScan data = new Gson().fromJson(string, StorageScan.class);
                if (data.isSuccess()) {
                    initData(removeRepeat(data.getDataset().getSBarCode()));
                    FinishLoading(null);
                } else {
                    FinishLoading(data.getMessage());
                }
            }

            @Override
            public void onFail(IOException e) {
                e.printStackTrace();
                FinishLoading(e.getMessage());
            }
        });
    }

    private void getDefaultData() {
        url = NetConfig.url + NetConfig.Pda_Method;
        userid = (String) sharedPreferencesHelper.getSharedPreference("userid", "");
        getIntentData();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        mainId = intent.getIntExtra("mainID", 0);
        stockId = intent.getIntExtra("stockid", 0);
        tableName = intent.getStringExtra("tableName");
    }

    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("otype", "GetProductD"));
        params.add(new NetParams("sTableName", tableName));
        params.add(new NetParams("iMainRecNo", mainId + ""));
        return params;
    }

    private void getData() {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(getParams(), url, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                StorageScan storageScan = new Gson().fromJson(string, StorageScan.class);
                if (storageScan.isSuccess()) {
                    initData(removeRepeat(storageScan.getDataset().getSBarCode()));
                    FinishLoading(null);
                } else {
                    FinishLoading(storageScan.getMessage());
                }
            }

            @Override
            public void onFail(IOException e) {
                e.printStackTrace();
                FinishLoading(e.getMessage());
            }
        });
    }

    private void initData(List<StorageScanBean> sBarCode) {
        if (sBarCode != null && sBarCode.size() > 0) {
            products.addAll(sBarCode);
            refreshList();
        }
    }


    private void refreshList() {
        Log.d(TAG, products.size() + "");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, TAG);
                if (mAdapter == null) {
                    mAdapter = new InputAdapter(ScanNewActivity.this, products);
                    rvScan.setAdapter(mAdapter);
                    tvTitle.setText("扫描条码" + "(" + products.size() + ")");
                    tvTotal.setText(getTotal(products));
                    mAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
//                            isDelete(position);
                        }
                    });
                } else {
                    mAdapter.notifyDataSetChanged();
                    tvTitle.setText("扫描条码" + "(" + products.size() + ")");
                    tvTotal.setText(getTotal(products));
                }
            }
        });
    }

    private String getTotal(List<StorageScanBean> list) {
        int total = 0;
        for (int i = 0; i < list.size(); i++) {
            total = total + list.get(i).getiQty();
        }
        return "总片数：" + total;
    }

    private int getTotals(List<StorageScanBean> list) {
        int total = 0;
        for (int i = 0; i < list.size(); i++) {
            total = total + list.get(i).getiQty();
        }
        return total;
    }

    private List<StorageScanBean> removeRepeat(List<StorageScanBean> sBarCode) {
        List<StorageScanBean> barCodes = new ArrayList<>();
        for (int i = 0; i < sBarCode.size(); i++) {
            int oldSize = codes.size();
            codes.add(sBarCode.get(i).getsBarCode());
            if (oldSize < codes.size()) {
                barCodes.add(sBarCode.get(i));
            }
        }
        return barCodes;
    }
    

    @OnClick({R.id.iv_back, R.id.tv_clear, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_clear:
                clearData();
                break;
            case R.id.tv_submit:
                saveData();
                break;
        }
    }

    /**
     * 保存参数
     *
     * @return
     */
    private List<NetParams> getSaveCodeParams() {
        String codes = "";
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("otype", "MMProductDsave"));
        params.add(new NetParams("sTableName", tableName));
        params.add(new NetParams("iRecNo", mainId + ""));
        for (int i = 0; i < products.size(); i++) {
            if (i != products.size() - 1)
                codes = codes + products.get(i).getsBarCode() + ","
                        + products.get(i).getiBscDataStockDRecNo() + "," + products.get(i).getiQty() + ";";
            else {
                codes = codes + products.get(i).getsBarCode() + ","
                        + products.get(i).getiBscDataStockDRecNo() + "," + products.get(i).getiQty();
            }
        }
        params.add(new NetParams("data", codes));
        return params;
    }

    private void saveData() {
        new NetUtil(getSaveCodeParams(), url, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(string);
                    if (jsonObject.optBoolean("success")) {
                        FinishLoading(null);
                        Intent intent = new Intent();
                        intent.putExtra("total", getTotals(products));
                        setResult(1, intent);
                        finish();
                    } else {
                        FinishLoading(jsonObject.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    FinishLoading(e.getMessage());
                }

            }

            @Override
            public void onFail(IOException e) {
                e.printStackTrace();
                FinishLoading(e.getMessage());
            }
        });
    }

    private void clearData() {
        products.clear();
        refreshList();
    }

    private void FinishLoading(@NonNull String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LoadingDialog.cancelDialogForLoading();
                if (StringUtil.isNotEmpty(msg)) {
                    Toasts.showShort(ScanNewActivity.this, msg);
                }
            }
        });

    }
}
