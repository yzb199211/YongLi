package com.yyy.yongli.input;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yyy.yongli.R;
import com.yyy.yongli.dialog.LoadingDialog;
import com.yyy.yongli.interfaces.OnChangedListener;
import com.yyy.yongli.interfaces.OnEntryListener;
import com.yyy.yongli.interfaces.OnSelectClickListener;
import com.yyy.yongli.interfaces.ResponseListener;
import com.yyy.yongli.lookup.Items;
import com.yyy.yongli.model.StorageStockMBean;
import com.yyy.yongli.pick.builder.OptionsPickerBuilder;
import com.yyy.yongli.pick.listener.OnOptionsSelectListener;
import com.yyy.yongli.pick.view.OptionsPickerView;
import com.yyy.yongli.util.NetParams;
import com.yyy.yongli.util.NetUtil;
import com.yyy.yongli.util.SharedPreferencesHelper;
import com.yyy.yongli.util.StringUtil;
import com.yyy.yongli.util.Toasts;
import com.yyy.yongli.view.EditItem;
import com.yyy.yongli.view.SelectItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InputAddActivity extends AppCompatActivity {
    private static final String TAG = "InputAddActivity";

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_Storage)
    SelectItem tvStorage;
    @BindView(R.id.tv_pos)
    SelectItem tvPos;
    @BindView(R.id.tv_barcode)
    SelectItem tvBarcode;
    @BindView(R.id.et_code)
    EditItem etCode;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.switch_view)
    Switch switchView;

    SharedPreferencesHelper preferencesHelper;

    String url;
    String userid;

    int isRed = 0;
    int storageId = 0;
    int posId = 0;

    List<Items> lookUps;
    List<StorageStockMBean> storages;

    private OptionsPickerView pvStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_add);
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
        lookUps = new ArrayList<>();
        storages = new ArrayList<>();
    }

    private void initView() {
        tvTitle.setText("入库单");
        ivRight.setVisibility(View.GONE);
        tvRight.setText("今日入库");
        initStorageSelect();
        initPosSelect();
        initBarcode();
        initScanCode();
        setRedListener();
    }

    private void initBarcode() {
        tvBarcode.setTitle("大条码：");
    }


    private void initPosSelect() {
        tvPos.setTitle("库位选择：");
        tvPos.setHint("请选择库位");
        int currentpos;
        tvPos.setOnSelectClickListener(new OnSelectClickListener() {
            @Override
            public void onSelectClick(View view) {
                if (storageId == 0) {
                    Toasts.showShort(InputAddActivity.this, "请选择仓库");
                } else {
                }
            }
        });
    }

    private void initStorageSelect() {
        tvStorage.setTitle("仓库选择：");
        tvStorage.setHint("请选择仓库");
        tvStorage.setOnSelectClickListener(new OnSelectClickListener() {
            @Override
            public void onSelectClick(View view) {
                if (storages.size() > 0)
                    pvStorage.show();
            }
        });
    }

    private void initScanCode() {
        etCode.setTitle("条码扫描：");
        etCode.setHint("请输入条码");
        etCode.setInputType(InputType.TYPE_CLASS_NUMBER);
        setCodeListener();
    }

    private void setCodeListener() {
        etCode.setOnEntryListener(new OnEntryListener() {
            @Override
            public void onEntry(View view) {
                whichCode(etCode.getText());
            }
        });
    }

    private void whichCode(String text) {
        if (text.length() == 10) {

        } else if (text.length() == 9) {

        } else {
        }
    }

    private void setRedListener() {
        switchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isRed = isChecked ? 1 : 0;
            }
        });
    }

    private void getDefaultData() {
        userid = (String) preferencesHelper.getSharedPreference("userid", "");
        url = "http://36.22.188.50:8090/MobileServerNew/MobilePDAHandler.ashx";
    }

    @OnClick({R.id.iv_back, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_right:
                goActivity();
                break;
        }
    }

    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("otype", "GetStockM"));
        params.add(new NetParams("sUserID", userid));
        return params;
    }

    private void getData() {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(getParams(), url, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    judgeSuccess(jsonObject, jsonObject.optBoolean("success"));

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

    private void judgeSuccess(JSONObject jsonObject, boolean success) throws JSONException {
        if (success) {
            initData(jsonObject.optString("dataset"));
        } else {
            FinishLoading(jsonObject.optString(jsonObject.optString("message")));
        }
    }

    private void initData(String dataset) throws JSONException {

        JSONObject jsonObject = new JSONObject(dataset);
        storages.addAll(new Gson().fromJson(jsonObject.optString("BscDataStockM"), new TypeToken<List<StorageStockMBean>>() {
        }.getType()));
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initStoragePick();
            }
        });

        FinishLoading(null);
    }

    private void initStoragePick() {
        pvStorage = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                storageId = storages.get(options1).getIRecNo();
                tvStorage.setContent(storages.get(options1).getSStockName());
            }
        })
                .setTitleText("仓库选择")
                .setContentTextSize(18)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setSelectOptions(0)//默认选中项
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setLabels("", "", "")
                .isDialog(true)
                .setBgColor(0xFFFFFFFF) //设置外部遮罩颜色
                .build();
        pvStorage.setPicker(storages);//一级选择器
        setDialog(pvStorage.getDialog());
    }

    private void setDialog(Dialog dialog) {
        getDialogLayoutParams();
        pvStorage.getDialogContainerLayout().setLayoutParams(getDialogLayoutParams());
        initDialogWindow(dialog.getWindow());
    }

    private void initDialogWindow(Window window) {
        window.setWindowAnimations(R.style.picker_view_slide_anim);//修改动画样式
        window.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
        window.setDimAmount(0.1f);
        window.setAttributes(getDialogWindowLayoutParams(window));
    }

    private WindowManager.LayoutParams getDialogWindowLayoutParams(Window window) {
        WindowManager.LayoutParams winParams;
        winParams = window.getAttributes();
        winParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        return winParams;
    }

    private FrameLayout.LayoutParams getDialogLayoutParams() {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.BOTTOM);
        params.leftMargin = 0;
        params.rightMargin = 0;
        return params;
    }

    private void goActivity() {
        startActivity(new Intent().setClass(this, InputAddActivity.class));
    }

    private void FinishLoading(@NonNull String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LoadingDialog.cancelDialogForLoading();
                if (StringUtil.isNotEmpty(msg)) {
                    Toasts.showShort(InputAddActivity.this, msg);
                }
            }
        });

    }
}
