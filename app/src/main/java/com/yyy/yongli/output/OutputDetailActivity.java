package com.yyy.yongli.output;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.yyy.yongli.BaseActivity;
import com.yyy.yongli.R;
import com.yyy.yongli.dialog.JudgeDialog;
import com.yyy.yongli.dialog.LoadingDialog;
import com.yyy.yongli.interfaces.ResponseListener;
import com.yyy.yongli.lookup.LookUpActivity;
import com.yyy.yongli.lookup.LookUpBean;
import com.yyy.yongli.model.Storage;
import com.yyy.yongli.model.StorageListOrder;
import com.yyy.yongli.model.StorageMain;
import com.yyy.yongli.model.StorageStockMBean;
import com.yyy.yongli.pick.builder.OptionsPickerBuilder;
import com.yyy.yongli.pick.builder.TimePickerBuilder;
import com.yyy.yongli.pick.listener.OnOptionsSelectChangeListener;
import com.yyy.yongli.pick.listener.OnOptionsSelectListener;
import com.yyy.yongli.pick.listener.OnTimeSelectChangeListener;
import com.yyy.yongli.pick.listener.OnTimeSelectListener;
import com.yyy.yongli.pick.view.OptionsPickerView;
import com.yyy.yongli.pick.view.TimePickerView;

import com.yyy.yongli.scan.ScanNewActivity2;
import com.yyy.yongli.util.CodeConfig;
import com.yyy.yongli.util.NetConfig;
import com.yyy.yongli.util.NetParams;
import com.yyy.yongli.util.NetUtil;
import com.yyy.yongli.util.SharedPreferencesHelper;
import com.yyy.yongli.util.StringUtil;
import com.yyy.yongli.util.Toasts;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OutputDetailActivity extends BaseActivity {
    private final static String TAG = "OutputDetailActivity";
    private final static int ADDCODE = 100;
    private final static int SUMITCODE = 101;
    private final static int SCANCODE = 102;
    private final static int CUSTOMERCODE = 103;
    private final static int NOTICECODE = 104;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right2)
    ImageView ivRight2;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.tv_storage)
    TextView tvStorage;
    @BindView(R.id.switch_view)
    Switch switchView;
    @BindView(R.id.ll_detial)
    LinearLayout llDetial;
    @BindView(R.id.tv_supplier)
    TextView tvSupplier;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    @BindView(R.id.fl_empty)
    FrameLayout flEmpty;
    @BindView(R.id.scroll)
    ScrollView scroll;
    @BindView(R.id.bottom_layout)
    LinearLayout bottomLayout;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_notice)
    TextView tvNotice;

    String url;
    List<LookUpBean> customers;//客户列表
    List<LookUpBean> notices;//通知单列表
    List<StorageStockMBean> stocks;//仓库列表

    private TimePickerView pvTime;
    private OptionsPickerView pvCustom, pvStock;
    private JudgeDialog deleteDialog;
    private JudgeDialog cleanDialog;
    private JudgeDialog submitDialog;
    private JudgeDialog saveDialog;
    int isSelect = 0;//是否红冲

    String customerid = "";
    int stockid = 0;
    String noticeid = "";

    String selectDate;
    int codeNum = 0;
    int mainID = 0;
    SharedPreferencesHelper preferencesHelper;
    boolean isFirstLoad = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output_detail);
        ButterKnife.bind(this);
        preferencesHelper = new SharedPreferencesHelper(this, getString(R.string.preferenceCache));
        init();

    }

    private void init() {
//        String head = (String) preferencesHelper.getSharedPreference("url", "");
//        if (StringUtil.isNotEmpty(head)) {
//            url = head + "/" + NetConfig.Pda_Method;
//        }
//        url = NetConfig.url + NetConfig.Pda_Method;
        url = (String) preferencesHelper.getSharedPreference("url", "") + NetConfig.Pda_Method;
        tvTitle.setText("出库单");
        ivRight.setVisibility(View.GONE);
        customers = new ArrayList<>();
        stocks = new ArrayList<>();
        notices = new ArrayList<>();
        selectDate = StringUtil.getTime();
        tvDate.setText(StringUtil.getTime());

        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        if (StringUtil.isNotEmpty(data)) {
            initView(data);
        }
        setSwitch();
        getData();
    }

    /**
     * 初始化数据
     *
     * @param data
     */
    private void initView(String data) {
        StorageListOrder order = new Gson().fromJson(data, StorageListOrder.class);
        mainID = order.getIRecNo();
        selectDate = order.getDDate();
        selectDate = selectDate.substring(0, 10);
        customerid = (order.getIBscDataCustomerRecNo() == 0 ? "" : order.getIBscDataCustomerRecNo() + "");
        stockid = order.getIBscDataStockMRecNo();
        codeNum = order.getIQty();
        isSelect = order.getIRed();

        noticeid = order.getISDSendMRecNo() == 0 ? "" : order.getISDSendMRecNo() + "";

        tvDate.setText(selectDate);
        tvSupplier.setText(order.getSCustShortName());
        tvStorage.setText(order.getSStockName());
        tvNum.setText("商品数量：" + codeNum);
        etRemark.setText(order.getSReMark());
        if (isSelect == 1)
            switchView.setChecked(true);
        tvNotice.setText(StringUtil.isNotEmpty(order.getSSdSendNo()) ? order.getSSdSendNo() : "");
    }

    /**
     * 设置红冲监听
     */
    private void setSwitch() {
        switchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    isSelect = 1;
                else
                    isSelect = 0;
            }
        });
    }

    /**
     * 获取选择列表数据
     */
    private void getData() {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(getParams(), url, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                try {
                    Log.e(TAG, string);
                    initData(string);
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LoadingDialog.cancelDialogForLoading();
                            Toasts.showShort(OutputDetailActivity.this, "加载失败");
                            setEmpty();
                        }
                    });
                }
                ;
            }

            @Override
            public void onFail(IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadingDialog.cancelDialogForLoading();
                        Toasts.showShort(OutputDetailActivity.this, "加载失败");
                        setEmpty();
                    }
                });
            }
        });
    }

    /**
     * 初始化选择列表数据
     *
     * @param string
     * @throws Exception
     */
    private void initData(String string) throws Exception {
        Storage storage = new Gson().fromJson(string, Storage.class);
        if (storage.isSuccess()) {
            customers.addAll(storage.getDataset().getBscDataCustomer());

            stocks.addAll(storage.getDataset().getBscDataStockM());
            notices.addAll(storage.getDataset().getSDSendM());
            notices.add(0, new LookUpBean("", "无", ""));

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LoadingDialog.cancelDialogForLoading();
                    setSuccess();
                }
            });
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LoadingDialog.cancelDialogForLoading();
                    Toasts.showShort(OutputDetailActivity.this, storage.getMessage());
                    setEmpty();
                }
            });
        }
    }

    /**
     * 获取选择列表数据参数
     *
     * @return
     */
    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("otype", "GetBscData"));
        params.add(new NetParams("iType", "2"));
//        params.add(new NetParams("sCompanyCode", (String) preferencesHelper.getSharedPreference("db", "")));

        return params;
    }

    /**
     * s设置加载失败布局
     */
    private void setEmpty() {
        flEmpty.setVisibility(View.VISIBLE);
        bottomLayout.setVisibility(View.GONE);
        scroll.setVisibility(View.GONE);
        tvEmpty.setText(getString(R.string.refresh));
    }

    /**
     * 设置加载成功布局
     */
    private void setSuccess() {
        flEmpty.setVisibility(View.GONE);
        bottomLayout.setVisibility(View.VISIBLE);
        scroll.setVisibility(View.VISIBLE);
    }

    private void isClean() {
        if (cleanDialog == null)
            cleanDialog = new JudgeDialog(this, R.style.JudgeDialog, "清空后将直接保存数据，确认是否清空？");
        cleanDialog.setOnCloseListener(new JudgeDialog.OnCloseListener() {
            @Override
            public void onClick(boolean confirm) {
                new JudgeDialog.OnCloseListener() {
                    @Override
                    public void onClick(boolean confirm) {
                        if (confirm)
                            clearChild(null);
                    }
                };
            }
        });
        cleanDialog.show();
    }

    @OnClick({R.id.tv_empty, R.id.iv_back, R.id.iv_right2, R.id.iv_right, R.id.tv_storage, R.id.iv_clear, R.id.iv_scan,
            R.id.iv_add_detail, R.id.tv_supplier, R.id.tv_date, R.id.tv_delete, R.id.tv_save, R.id.tv_submit, R.id.tv_notice})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                if (mainID != 0)
                    back("", 0);
                else
                    finish();
                break;
            case R.id.iv_right2:
                break;
            case R.id.iv_right:
                break;
            case R.id.tv_storage:
                if (pvStock == null)
                    initPvStock();
                pvStock.show();
                break;
            case R.id.iv_clear:
                isClean();
                break;
            case R.id.iv_scan:
                if (stockid != 0) {
                    if (mainID == 0)
                        submit(SCANCODE, 0);
                    else {
                        goScan();
                    }
                } else
                    Toasts.showShort(OutputDetailActivity.this, "请选择仓库");
                break;
            case R.id.iv_add_detail:
                if (stockid != 0) {
                    if (mainID == 0)
                        submit(ADDCODE, 0);
                    else
                        goAdd();
                } else
                    Toasts.showShort(OutputDetailActivity.this, "请选择仓库");
                break;
            case R.id.tv_notice:
//                if (TextUtils.isEmpty(customerid)) {
                setLookUp(notices, "发货通知单", NOTICECODE);

//                } else {
//                    setLookUp(selectLookUp(customerid), "发货通知单", NOTICECODE);
//                }
                break;
            case R.id.tv_supplier:
                try {
                    if (TextUtils.isEmpty(noticeid)) {
                        setLookUp(customers, "客户选择", CUSTOMERCODE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_date:
                if (pvTime == null)
                    initTimePicker();
                pvTime.show();
                break;
            case R.id.tv_delete:
                if (mainID != 0)
                    isDelete();
                else
                    finish();
                break;
            case R.id.tv_save:
                save();
                break;
            case R.id.tv_submit:
                submit();
                break;
            case R.id.tv_empty:
                getData();
                break;
            default:
        }
    }

    private List<LookUpBean> selectLookUp(String customerid) {
        List<LookUpBean> list = new ArrayList<>();
        for (int i = 0; i < notices.size(); i++) {
            if (customerid.equals(notices.get(i).getsClassID()))
                list.add(notices.get(i));
        }
//        list.add(0, new LookUpBean("", "无", ""));
        return list;
    }

    private void setLookUp(List<LookUpBean> list, String title, int code) {
        Intent intent = new Intent();
        intent.setClass(OutputDetailActivity.this, LookUpActivity.class);
        intent.putExtra("data", new Gson().toJson(list));
        intent.putExtra("code", code);
        intent.putExtra("title", title);
        startActivityForResult(intent, code);
    }

    /**
     * 判断是否删除
     */
    private void isDelete() {
        if (deleteDialog == null) {
            deleteDialog = new JudgeDialog(this, R.style.JudgeDialog, "是否删除？");
            deleteDialog.setOnCloseListener(new JudgeDialog.OnCloseListener() {
                @Override
                public void onClick(boolean confirm) {
                    if (confirm)
                        deleteMain();
                }
            });
        }
        deleteDialog.show();
    }

    /**
     * 保存主表信息
     */
    private void save() {
        if (stockid == 0) {
            Toasts.showShort(this, "请选择仓库");
            return;
        }
        if (TextUtils.isEmpty(selectDate)) {
            Toasts.showShort(this, "请选择出库日期");
            return;
        }
        if (TextUtils.isEmpty(tvSupplier.getText().toString())) {
            Toasts.showShort(this, "请选择客户");
            return;
        }
        isSave();

    }

    /**
     * 判断是否保存
     */
    private void isSave() {
        if (saveDialog == null)
            saveDialog = new JudgeDialog(this, R.style.JudgeDialog, "是否保存？");
        saveDialog.setOnCloseListener(new JudgeDialog.OnCloseListener() {
            @Override
            public void onClick(boolean confirm) {
                if (confirm)
                    submit(SUMITCODE, 0);
            }
        });
        saveDialog.show();
    }

    /**
     * 保存主表信息
     */
    private void submit() {
        if (stockid == 0) {
            Toasts.showShort(this, "请选择仓库");
            return;
        }
        if (TextUtils.isEmpty(selectDate)) {
            Toasts.showShort(this, "请选择出库日期");
            return;
        }
        if (TextUtils.isEmpty(tvSupplier.getText().toString())) {
            Toasts.showShort(this, "请选择客户");
            return;
        }
        isSubmit();

    }

    /**
     * 判断是否提交
     */
    private void isSubmit() {
        if (submitDialog == null)
            submitDialog = new JudgeDialog(this, R.style.JudgeDialog, "是否提交？");
        submitDialog.setOnCloseListener(new JudgeDialog.OnCloseListener() {
            @Override
            public void onClick(boolean confirm) {
                if (confirm)
                    submit(SUMITCODE, 1);
            }
        });
        submitDialog.show();
    }

    /**
     * 初始化日期
     */
    private void initTimePicker() {//Dialog 模式下，在底部弹出
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                selectDate = StringUtil.getTime(date);
                tvDate.setText(selectDate);
            }
        })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                        Log.i("pvTime", "onTimeSelectChanged");
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .addOnCancelClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("pvTime", "onCancelClickListener");
                    }
                }).setContentTextSize(18).setBgColor(0xFFFFFFFF)
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);
            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);
            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                dialogWindow.setDimAmount(0.1f);
                //当显示只有一列是需要设置window宽度，防止两边有空隙；
                WindowManager.LayoutParams winParams;
                winParams = dialogWindow.getAttributes();
                winParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                dialogWindow.setAttributes(winParams);
            }
        }
    }


    /**
     * 初始化仓库
     */
    private void initPvStock() {

        pvStock = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tvStorage.setText(stocks.get(options1).getPickerViewText());
                stockid = stocks.get(options1).getIRecNo();
            }
        })
                .setTitleText("仓库选择")
                .setContentTextSize(18)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setSelectOptions(0)//默认选中项
                .isDialog(true)
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setLabels("", "", "")
                .setBgColor(0xFFFFFFFF) //设置外部遮罩颜色
                .build();

        pvStock.setPicker(stocks);//一级选择器
        Dialog mDialog = pvStock.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);
            params.leftMargin = 0;
            params.rightMargin = 0;
            pvStock.getDialogContainerLayout().setLayoutParams(params);
            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                dialogWindow.setDimAmount(0.1f);
                //当显示只有一列是需要设置window宽度，防止两边有空隙；
                WindowManager.LayoutParams winParams;
                winParams = dialogWindow.getAttributes();
                winParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                dialogWindow.setAttributes(winParams);
            }
        }
    }

    /**
     * 提交主表信息
     *
     * @param code
     * @param i
     */
    private void submit(int code, int i) {
        Log.e("params:", new Gson().toJson(getSubmitParams(i)));
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(getSubmitParams(i), url, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                Log.e(TAG, string);
                try {
                    StorageMain storageMain = new Gson().fromJson(string, StorageMain.class);
                    if (storageMain.isSuccess()) {
                        if (code == ADDCODE)
                            goAdd(string);
                        else if (code == SCANCODE)
                            goScan(string);
                        else if (code == SUMITCODE && i == 0) {
                            back("保存成功", 0);
                        } else {
                            back("提交成功", 1);
                        }
                    } else {
                        loadFail(storageMain.getMessage());
                        Log.e(TAG, storageMain.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (code == ADDCODE || code == SCANCODE)
                        loadFail("加载失败");
                    else if (code == SUMITCODE && i == 0)
                        loadFail("保存失败");
                    else
                        loadFail("提交失败");
                }
            }

            @Override
            public void onFail(IOException e) {
                if (code == ADDCODE || code == SCANCODE)
                    loadFail("加载失败");
                else if (code == SUMITCODE && i == 0)
                    loadFail("保存失败");
                else
                    loadFail("提交失败");
                Log.e(TAG, e.getMessage());
            }
        });
    }

    /**
     * 返回列表
     *
     * @param message
     */
    private void back(String message, int i) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LoadingDialog.cancelDialogForLoading();
                if (StringUtil.isNotEmpty(message))
                    Toasts.showShort(OutputDetailActivity.this, message);
                if (i == 1)
                    setResult(CodeConfig.DELETECODE);
                else
                    setResult(CodeConfig.REFRESHCODE);
                finish();
            }
        });
    }

    /**
     * 开始扫描
     */
    private void goAdd(String data) throws Exception {
        StorageMain storageMain = new Gson().fromJson(data, StorageMain.class);
        if (storageMain.isSuccess() == true) {
            mainID = storageMain.getDataset().getResult().get(0).getResult();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LoadingDialog.cancelDialogForLoading();
                    Intent intent = new Intent();
                    intent.setClass(OutputDetailActivity.this, ScanNewActivity2.class);
                    intent.putExtra("stockid", stockid);
                    intent.putExtra("mainID", mainID);
                    intent.putExtra("tableName", "MMProductOutD");
                    startActivityForResult(intent, 1);
                }
            });
        } else {
            loadFail(storageMain.getMessage());
            Log.e(TAG, storageMain.getMessage());
        }
    }

    private void goAdd() {
        Intent intent = new Intent();
        intent.setClass(OutputDetailActivity.this, ScanNewActivity2.class);
        intent.putExtra("stockid", stockid);
        intent.putExtra("mainID", mainID);
        intent.putExtra("red", isSelect);
        intent.putExtra("tableName", "MMProductOutD");
        startActivityForResult(intent, 1);
    }

    /**
     * 开始扫描
     */
    private void goScan(String data) throws Exception {
        StorageMain storageMain = new Gson().fromJson(data, StorageMain.class);
        if (storageMain.isSuccess() == true) {
            mainID = storageMain.getDataset().getResult().get(0).getResult();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LoadingDialog.cancelDialogForLoading();
                    Intent intent = new Intent();
                    intent.setClass(OutputDetailActivity.this, ScanNewActivity2.class);
                    intent.putExtra("stockid", stockid);
                    intent.putExtra("mainID", mainID);
                    intent.putExtra("tableName", "MMProductOutD");
                    startActivityForResult(intent, 1);
                }
            });
        } else {
            loadFail(storageMain.getMessage());
            Log.e(TAG, storageMain.getMessage());
        }
    }

    private void goScan() {
        Intent intent = new Intent();
        intent.setClass(OutputDetailActivity.this, ScanNewActivity2.class);
        intent.putExtra("stockid", stockid);
        intent.putExtra("mainID", mainID);
        intent.putExtra("tableName", "MMProductOutD");
        startActivityForResult(intent, 1);
    }

    /**
     * 提交主表参数
     *
     * @param isSubmit
     * @return
     */
    private List<NetParams> getSubmitParams(int isSubmit) {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("sTableName", "MMProductOutM"));
        if (mainID == 0)
            params.add(new NetParams("iRecNo", ""));
        else
            params.add(new NetParams("iRecNo", mainID + ""));
        params.add(new NetParams("iBscDataStockMRecNo", stockid + ""));
        params.add(new NetParams("iRed", isSelect + ""));
        params.add(new NetParams("iQty", codeNum + ""));
        params.add(new NetParams("iBscDataCustomerRecNo", customerid + ""));
        params.add(new NetParams("dDate", tvDate.getText().toString()));
        params.add(new NetParams("sRemark", etRemark.getText().toString()));
        params.add(new NetParams("sUserID", "master"));
        params.add(new NetParams("iSumbit", isSubmit + ""));
        params.add(new NetParams("otype", "MMProductSave"));
        if (TextUtils.isEmpty(noticeid))
            params.add(new NetParams("iSDSendMRecNo", ""));
        else
            params.add(new NetParams("iSDSendMRecNo", noticeid + ""));

        return params;
    }

    /**
     * 删除入库单
     */
    private void deleteMain() {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(getDeleteParams(), url, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                StorageMain storageMain = new Gson().fromJson(string, StorageMain.class);
                if (storageMain.isSuccess()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e(TAG, string);
                            Toasts.showShort(OutputDetailActivity.this, "删除成功");
                            LoadingDialog.cancelDialogForLoading();
                            setResult(CodeConfig.DELETECODE);
                            finish();
                        }
                    });
                } else {
                    loadFail("删除失败");
                }
            }

            @Override
            public void onFail(IOException e) {
                loadFail("删除失败");
            }
        });
    }

    /**
     * 删除入库单参数
     *
     * @return
     */
    private List<NetParams> getDeleteParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("sTableName", "MMProductOutM"));
        params.add(new NetParams("iRecNo", mainID + ""));
        params.add(new NetParams("otype", "DeleteProduct"));
//        params.add(new NetParams("sCompanyCode", (String) preferencesHelper.getSharedPreference("db", "")));
        return params;
    }

    /**
     * Exceptiom
     *
     * @param message
     */
    private void loadFail(String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LoadingDialog.cancelDialogForLoading();
                Toasts.showShort(OutputDetailActivity.this, message);
            }
        });
    }

//    @Override
//    protected void onDestroy() {
//        if (LoadingDialog.mLoadingDialog != null) {
//            LoadingDialog.mLoadingDialog.dismiss();
//        }
//        super.onDestroy();
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        Log.e("resultCode", requestCode + "");
        if (data != null)
            switch (resultCode) {
                case 1:
                    codeNum = data.getIntExtra("total", 0);
                    tvNum.setText("商品数量：" + codeNum);
                    break;
                case CUSTOMERCODE:
                    customerid = data.getStringExtra("id");
                    tvSupplier.setText(data.getStringExtra("name"));
                    break;
                case NOTICECODE:
                    if (StringUtil.isNotEmpty(noticeid) && !noticeid.equals(data.getStringExtra("id"))) {
                        isChangeNotice(data);
                    } else {
                        setNotiveView(data);
                    }
                    break;
                default:
                    break;
            }
    }

    JudgeDialog noticeDialog;

    /**
     * 判断是否更改通知单
     *
     * @param data
     */
    private void isChangeNotice(Intent data) {
        if (noticeDialog == null)
            noticeDialog = new JudgeDialog(this, R.style.JudgeDialog, "变更通知单将清空条码数据是否更改？");
        noticeDialog.setOnCloseListener(new JudgeDialog.OnCloseListener() {
            @Override
            public void onClick(boolean confirm) {
                if (confirm) {
                    changeNotice(data);
                }
            }
        });
        noticeDialog.show();
    }

    private void changeNotice(Intent data) {

        clearChild(data);
    }

    /**
     * 清空子表数据
     *
     * @param data
     */
    private void clearChild(@NonNull Intent data) {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(clearChildParams(), url, new ResponseListener() {
            @Override
            public void onSuccess(String string) {

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(string);
                    boolean isSuccess = jsonObject.optBoolean("success");
                    if (isSuccess) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvNum.setText("商品数量：0");
                                if (data != null)
                                    setNotiveView(data);
                                loadFail("清空成功");
                            }
                        });
                    } else {
                        loadFail(jsonObject.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    loadFail("清空失败");
                }

            }

            @Override
            public void onFail(IOException e) {
                loadFail("清空失败");
            }
        });
    }

    private void setNotiveView(Intent data) {
        noticeid = data.getStringExtra("id");
        tvNotice.setText(data.getStringExtra("name").equals("无")?"":data.getStringExtra("name"));
        customerid = data.getStringExtra("link_id");
        Log.e("customerid", customerid + ",");
        if (StringUtil.isNotEmpty(customerid)) {//关联客户信息
            for (int i = 0; i < customers.size(); i++) {
                if (customerid.equals(customers.get(i).getsCode())) {
                    tvSupplier.setText(customers.get(i).getsName());
                    break;
                }
            }
        }
    }

    private List<NetParams> clearChildParams() {
        List<NetParams> params = new ArrayList<>();
        String codes = "";
        params.add(new NetParams("otype", "MMProductDsave"));
        params.add(new NetParams("sTableName", "MMProductOutD"));
        params.add(new NetParams("iRecNo", mainID + ""));
        params.add(new NetParams("data", codes));
//        params.add(new NetParams("sCompanyCode", (String) preferencesHelper.getSharedPreference("db", "")));
        return params;
    }
}
