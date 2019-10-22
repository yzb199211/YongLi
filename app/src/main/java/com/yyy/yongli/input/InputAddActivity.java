package com.yyy.yongli.input;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
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
import com.yyy.yongli.model.StorageScan;
import com.yyy.yongli.model.StorageScanBean;
import com.yyy.yongli.model.StorageStockMBean;
import com.yyy.yongli.model.StorageStockPosBean;
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

import org.json.JSONArray;
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
    @BindView(R.id.ll_item)
    LinearLayout llItem;

    SharedPreferencesHelper preferencesHelper;

    String url;
    String userid;

    int isRed = 0;
    int storageId = 0;
    int posStorage = -1;
    int posId = 0;

    String bigCode;

    List<Items> lookUps;
    List<StorageStockMBean> storages;
    List<StorageStockPosBean> posBeans;
    List<String> codes;

    private OptionsPickerView pvStorage;
    private OptionsPickerView pvPos;

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
        posBeans = new ArrayList<>();
        codes = new ArrayList<>();
    }

    private void initView() {
        tvTitle.setText("入库单");
        ivRight.setVisibility(View.GONE);
        tvRight.setVisibility(View.VISIBLE);
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

        tvPos.setOnSelectClickListener(new OnSelectClickListener() {
            @Override
            public void onSelectClick(View view) {
                if (storageId == 0) {
                    Toasts.showShort(InputAddActivity.this, "请选择仓库");
                } else if (storageId != posStorage) {
                    getPosData();
                } else if (posBeans.size() > 0) {
                    pvPos.show();
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
                if (storageId == 0) {
                    Toasts.showShort(InputAddActivity.this, "请选择仓库");
                    return;
                }

                judgeCode(etCode.getText());
            }
        });
    }

    private void judgeCode(String text) {
        if (text.length() == 10 || text.length() == 9) {
            if (isRed == 1) {
                sendRedData(text);
            } else {
                judgeSend(text);
            }
            etCode.setContent("");
        } else {
            Toasts.showShort(this, "错误条码");
        }
    }

    private void judgeSend(String code) {
        if (code.length() == 10) {
            setBigCode(code);
        } else if (code.length() == 9) {
            getCodeData(code);
        }


    }


    private void setBigCode(String code) {
        bigCode = code;
        tvBarcode.setVisibility(View.VISIBLE);
        tvBarcode.setTitle("大条码：");
        tvBarcode.setContent(code);
    }

    private void setRedListener() {
        switchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isRed = isChecked ? 1 : 0;
                clearData();
            }
        });
    }

    private void clearData() {
        bigCode = "";
        codes.clear();
        removeView();
    }

    private void removeView() {
        tvBarcode.setVisibility(View.GONE);
        llItem.removeAllViews();
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
                if (storageId == 0) {
                    Toasts.showShort(InputAddActivity.this, "请选择仓库");
                    return;
                }
                goActivity();
                break;
        }
    }

    private List<NetParams> getCodeparams(String code) {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("otype", "GetBarCode"));
        params.add(new NetParams("sBarCode", code));
        return params;
    }

    private void getCodeData(String code) {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(getCodeparams(code), url, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                StorageScan data = new Gson().fromJson(string, StorageScan.class);
                judgeCodesuccess(data);
            }

            @Override
            public void onFail(IOException e) {
                e.printStackTrace();
                FinishLoading(e.getMessage());
            }
        });
    }

    private void judgeCodesuccess(StorageScan data) {
        if (data.isSuccess()) {
            if (data.getDataset().getSBarCode().size() > 0) {
                codes.add(data.getDataset().getSBarCode().get(0).getsBarCode());
                setCodeView(data.getDataset().getSBarCode());
                FinishLoading(null);
            } else {
                FinishLoading("无条码数据");
            }
        } else {
            FinishLoading(data.getMessage());
        }
    }

    private void setCodeView(List<StorageScanBean> sBarCode) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < sBarCode.size(); i++) {
                    View view = LayoutInflater.from(InputAddActivity.this).inflate(R.layout.item_scan_new, llItem, false);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.e("delete", "删除数据");
                        }
                    });
                    llItem.addView(setViewData(view, sBarCode.get(i)));
                }
                judgeSendData();
            }
        });
    }


    private View setViewData(View v, StorageScanBean storageScanBean) {
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


        tvBatch = v.findViewById(R.id.tv_batch_no);
        tvTitle = v.findViewById(R.id.tv_title);

        tvTC = v.findViewById(R.id.tv_tc);
        tvPos = v.findViewById(R.id.tv_pos);
        tvTS = v.findViewById(R.id.tv_ts);


        tvVoltage = v.findViewById(R.id.tv_voltage);
        tvResistance = v.findViewById(R.id.tv_resistance);
        tvVoltageResistance = v.findViewById(R.id.tv_voltage_resistance);

        tvCurrent = v.findViewById(R.id.tv_current);
        tvElectrode = v.findViewById(R.id.tv_electrode);
        tvCount = v.findViewById(R.id.tv_count);

        tvCode = v.findViewById(R.id.tv_code);

        tvTitle.setText("规格：" + storageScanBean.getsElements());
        tvBatch.setText("批次：" + storageScanBean.getsBatchNo());

        tvTS.setText("TS：" + storageScanBean.getfTS());
        tvTC.setText("TC：" + storageScanBean.getfTC());
        tvPos.setText("仓位：" + (TextUtils.isEmpty(storageScanBean.getsBerChID()) ? "" : storageScanBean.getsBerChID()));

        tvResistance.setText("电阻：" + storageScanBean.getfResistance());
        tvVoltage.setText("工作电压：" + storageScanBean.getfVoltage());
        tvVoltageResistance.setText("耐电压：" + storageScanBean.getfVoltageResistance());

        tvCurrent.setText("电流：" + storageScanBean.getfCurrent());
        tvElectrode.setText("电极：" + storageScanBean.getsElectrode());
        tvCount.setText("片数：" + storageScanBean.getiQty());

        tvCode.setText("条码：" + storageScanBean.getsBarCode());
        return v;
    }

    private List<NetParams> sendDataParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("otype", "AddBarCode"));
        params.add(new NetParams("sBarCode1", codes.get(0)));
        params.add(new NetParams("sBarCode2", codes.get(1)));
        params.add(new NetParams("sInBarCode", bigCode));
        params.add(new NetParams("sUserID", userid));
        params.add(new NetParams("iBscDataStockMRecNo", storageId + ""));
        params.add(new NetParams("iBscDataStockDRecNo", posId + ""));
        return params;
    }

    private void judgeSendData() {
        if (codes.size() == 2 && StringUtil.isNotEmpty(bigCode)) {
            sendData();
            clearData();
        }
    }

    private void sendData() {
        FinishLoading(null);
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(sendDataParams(), url, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    if (jsonObject.optBoolean("success")) {

                        FinishLoading("入库成功");

                    } else {
                        FinishLoading(jsonObject.optString("message"));
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

    private List<NetParams> getRedParams(String code) {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("otype", "AddBarCodeRed"));
        params.add(new NetParams("sUserID", userid));
        params.add(new NetParams("iBscDataStockMRecNo", storageId + ""));
        params.add(new NetParams("iBscDataStockDRecNo", ""));
        params.add(new NetParams("sBarCode", code));
        return params;
    }

    private void sendRedData(String code) {
        removeView();
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(getRedParams(code), url, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    if (jsonObject.optBoolean("success")) {
                        List<StorageScanBean> list = new Gson().fromJson(jsonObject.optJSONObject("dataset").optString("BarCodeRed"), new TypeToken<List<StorageScanBean>>() {
                        }.getType());
                        setCodeView(list);
                        FinishLoading("红冲成功");
                    } else {
                        FinishLoading(jsonObject.optString("message"));
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
        setDialog(pvStorage);
    }

    private List<NetParams> getPosParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("otype", "GetStockD"));
        params.add(new NetParams("iBscDataStockMRecNo", storageId + ""));
        return params;
    }

    private void getPosData() {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(getPosParams(), url, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                judgePos(string);
            }

            @Override
            public void onFail(IOException e) {
                e.printStackTrace();
                FinishLoading(e.getMessage());
            }
        });
    }

    private void judgePos(String string) {
        try {
            JSONObject jsonObject = new JSONObject(string);
            if (jsonObject.optBoolean("success")) {
                initPosData(jsonObject.optJSONObject("dataset").optString("BscDataStockD"));
            } else {
                FinishLoading(jsonObject.optString("message"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            FinishLoading(e.getMessage());
        }
    }

    private void initPosData(String dataset) {
        posBeans.addAll(new Gson().fromJson(dataset, new TypeToken<List<StorageStockPosBean>>() {
        }.getType()));
        posStorage = storageId;
        if (posBeans.size() > 0)
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    initPosPick();
                }
            });
        FinishLoading(null);

    }

    private void initPosPick() {
        pvPos = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                posId = posBeans.get(options1).getIRecNo();
                tvPos.setContent(posBeans.get(options1).getSBerChID());
            }
        })
                .setTitleText("库位选择")
                .setContentTextSize(18)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setSelectOptions(0)//默认选中项
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setLabels("", "", "")
                .isDialog(true)
                .setBgColor(0xFFFFFFFF) //设置外部遮罩颜色
                .build();
        pvPos.setPicker(posBeans);//一级选择器
        setDialog(pvPos);
    }

    private void setDialog(OptionsPickerView pickview) {
        getDialogLayoutParams();
        pickview.getDialogContainerLayout().setLayoutParams(getDialogLayoutParams());
        initDialogWindow(pickview.getDialog().getWindow());
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
        startActivity(new Intent().setClass(this, InputListActivity.class).putExtra("storageid", storageId));
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
