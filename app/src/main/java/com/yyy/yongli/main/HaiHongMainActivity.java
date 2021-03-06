package com.yyy.yongli.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.yyy.yongli.R;
import com.yyy.yongli.exchange.ExchangeActivity;
import com.yyy.yongli.interfaces.OnItemClickListener;
import com.yyy.yongli.output.OutputListActivity;
import com.yyy.yongli.produce.ProduceActivity;
import com.yyy.yongli.storage.StorageSearchActivity;
import com.yyy.yongli.total.TotalListActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HaiHongMainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.rv_menu)
    RecyclerView rvMenu;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    MenuUsualAdapter menuUsualAdapter;
//    SharedPreferencesHelper preferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
//        preferencesHelper = new SharedPreferencesHelper(this, getString(R.string.preferenceCache));
//        Log.e(TAG, (String) preferencesHelper.getSharedPreference("userid", ""));
        ivBack.setVisibility(View.GONE);
        ivRight.setVisibility(View.GONE);
        tvTitle.setText(getString(R.string.app_name1));
        setMenus();
    }

    private void setMenus() {
        List<Menu> list = new ArrayList<>();
//        list.add(new Menu(1, R.mipmap.icon_produce, "产量扫描"));
//        list.add(new Menu(2, R.mipmap.icon_output, "出库单"));
//        list.add(new Menu(3, R.mipmap.icon_statistics, "盘点单"));
        list.add(new Menu(4, R.mipmap.icon_exchange, "调拨单"));
        list.add(new Menu(5, R.mipmap.icon_storage_search, "库存查询"));
        menuUsualAdapter = new MenuUsualAdapter(list, this);
        rvMenu.setAdapter(menuUsualAdapter);
        NoScrollGvManager manager = new NoScrollGvManager(this, 4);
        manager.setAutoMeasureEnabled(true);
        rvMenu.setHasFixedSize(true);
        manager.setScrollEnabled(false);
        rvMenu.setLayoutManager(manager);
        menuUsualAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                goNext(list.get(position).getId());
            }
        });
    }

    private void goNext(int id) {
        Intent intent = new Intent();
        switch (id) {
            case 1:
                intent.setClass(this, ProduceActivity.class);
                startActivity(intent);
                break;
            case 2:
                intent.setClass(this, OutputListActivity.class);
                startActivity(intent);
                break;
            case 3:
                intent.setClass(this, TotalListActivity.class);
                startActivity(intent);
            case 4:
                intent.setClass(this, ExchangeActivity.class);
                startActivity(intent);
                break;
            case 5:
                intent.setClass(this, StorageSearchActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

}
