package com.yyy.yongli.input;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yyy.yongli.R;
import com.yyy.yongli.dialog.LoadingDialog;
import com.yyy.yongli.interfaces.ResponseListener;
import com.yyy.yongli.model.StorageScan;
import com.yyy.yongli.model.StorageScanBean;
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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InputListActivity extends AppCompatActivity {
    private static final String TAG = "InputListActivity";

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.rv_item)
    XRecyclerView rvItem;

    SharedPreferencesHelper preferencesHelper;

    String userid;
    String url;

    int page = 1;
    int pageSize = 20;

    int storageid;

    InputAdapter mAdapter;

    List<StorageScanBean> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_list);
        ButterKnife.bind(this);
        preferencesHelper = new SharedPreferencesHelper(this, getString(R.string.preferenceCache));
        init();
    }

    private void init() {
        getDefaultData();
        initList();
        initView();
        getData();
    }

    private void initList() {
        datas = new ArrayList<>();
    }

    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("otype", "GetToDayProductInList"));
        params.add(new NetParams("iBscDataStockMRecNo", storageid + ""));
        params.add(new NetParams("page", page + ""));
        params.add(new NetParams("pageSize", pageSize + ""));
        return params;
    }

    private void getData() {
        new NetUtil(getParams(), url, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    isSuccess(jsonObject);
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

    private void isSuccess(JSONObject jsonObject) {
        if (jsonObject.optBoolean("success")) {
            initData(jsonObject.optJSONObject("dataset").optString("ProductInList"));
        } else {
            FinishLoading(jsonObject.optString("message"));
        }
    }

    private void initData(String optString) {
        List<StorageScanBean> list = new Gson().fromJson(optString, new TypeToken<List<StorageScanBean>>() {
        }.getType());
        if (list.size() > 0) {
            datas.addAll(list);
            refreshList();
        }
    }

    private void refreshList() {
        if (mAdapter == null) {
            mAdapter = new InputAdapter(this, datas);
            rvItem.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }


    private void getDefaultData() {
        userid = (String) preferencesHelper.getSharedPreference("userid", "");
        storageid = getIntent().getIntExtra("storageid", 0);
        url = "http://36.22.188.50:8090/MobileServerNew/MobilePDAHandler.ashx";
    }

    private void initView() {
        ivRight.setVisibility(View.GONE);
        tvTitle.setText("今日入库");
        initRecycle();
    }

    private void initRecycle() {
        rvItem.setLayoutManager(new LinearLayoutManager(this));
        rvItem.setLoadingMoreEnabled(false);
        rvItem.setPullRefreshEnabled(false);
        rvItem.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        rvItem.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
        rvItem.setArrowImageView(R.mipmap.iconfont_downgrey);
        rvItem.getDefaultRefreshHeaderView()
                .setRefreshTimeVisible(true);
        rvItem.addItemDecoration(new MyRecyclerViewDivider(this, LinearLayoutManager.VERTICAL));
        setLoadingListener();
    }

    private void setLoadingListener() {
        rvItem.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {

            }
        });
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    private void FinishLoading(@NonNull String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LoadingDialog.cancelDialogForLoading();
                if (StringUtil.isNotEmpty(msg)) {
                    Toasts.showShort(InputListActivity.this, msg);

                }
            }
        });

    }
}
