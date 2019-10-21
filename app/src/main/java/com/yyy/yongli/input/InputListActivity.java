package com.yyy.yongli.input;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yyy.yongli.R;
import com.yyy.yongli.dialog.LoadingDialog;
import com.yyy.yongli.interfaces.ResponseListener;
import com.yyy.yongli.util.NetParams;
import com.yyy.yongli.util.NetUtil;
import com.yyy.yongli.util.SharedPreferencesHelper;
import com.yyy.yongli.util.StringUtil;
import com.yyy.yongli.util.Toasts;
import com.yyy.yongli.view.MyRecyclerViewDivider;

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

    int page;
    int pageSize;

    int storageid;

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
        initView();
        getData();
    }

    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("otype", "GetToDayProductInList"));
        params.add(new NetParams("iBscDataStockMRecNo", storageid + ""));
        return params;
    }

    private void getData() {
        new NetUtil(getParams(), url, new ResponseListener() {
            @Override
            public void onSuccess(String string) {

            }

            @Override
            public void onFail(IOException e) {
                e.printStackTrace();
                FinishLoading(e.getMessage());
            }
        });
    }


    private void getDefaultData() {
        userid = (String) preferencesHelper.getSharedPreference("userid", "");
        storageid = getIntent().getIntExtra("storageid", 0);
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
