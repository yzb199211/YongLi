package com.yyy.yongli.storage;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.yyy.yongli.R;
import com.yyy.yongli.dialog.LoadingDialog;
import com.yyy.yongli.interfaces.ResponseListener;
import com.yyy.yongli.model.haihong.ExchangeInputPosBean;
import com.yyy.yongli.model.haihong.ExchangePosBean;
import com.yyy.yongli.model.haihong.StorageSearchBean;
import com.yyy.yongli.pick.builder.OptionsPickerBuilder;
import com.yyy.yongli.pick.listener.OnOptionsSelectChangeListener;
import com.yyy.yongli.pick.listener.OnOptionsSelectListener;
import com.yyy.yongli.pick.view.OptionsPickerView;
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

public class StorageSearchActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_store)
    TextView tvStore;
    @BindView(R.id.et_pos)
    EditText etPos;
    @BindView(R.id.et_barcode)
    EditText etBarcode;
    @BindView(R.id.et_color)
    EditText etColor;
    @BindView(R.id.rv_storage)
    RecyclerView rvStorage;

    LinearLayoutManager manager;
    StorageSearchAdapter mAdapter;

    private OptionsPickerView pvStore;
    private OptionsPickerView pvPos;

    int storeId;
    int posId;

    String url;
    String userid;
    String storeName;
    String posName;

    List<ExchangePosBean.TablesBean> storeList;
    List<ExchangeInputPosBean.TablesBean> posList;
    List<StorageSearchBean.TablesBean> searchList;

    SharedPreferencesHelper preferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_search);
        ButterKnife.bind(this);
        preferencesHelper = new SharedPreferencesHelper(this, getString(R.string.preferenceCache));
        init();
        getData();
    }


    private void init() {
        storeList = new ArrayList<>();
        posList = new ArrayList<>();
        searchList = new ArrayList<>();

        manager = new LinearLayoutManager(this);
        rvStorage.setLayoutManager(manager);

        ivRight.setVisibility(View.GONE);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("查询");
        tvTitle.setText("库存查询");

        url = NetConfig.url + NetConfig.Pda_Method;
        userid = (String) preferencesHelper.getSharedPreference("userid", "");
        initEdit();
    }

    private void initEdit() {
        etBarcode.setInputType(InputType.TYPE_CLASS_NUMBER);
        etBarcode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    hintKeyBoard();
                    search();
                }
                return false;
            }
        });

        etPos.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    hintKeyBoard();
                    if (TextUtils.isEmpty(etPos.getText().toString())) {
                        posName = "";
                        posId = 0;
                        search();
                    } else if (posList.size() == 0) {
                        getPosData(1);
                    } else {
                        setPos();

                    }
//                    etBarcode.requestFocus();
                }
                return false;
            }
        });
        etColor.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    hintKeyBoard();
                    search();

                }
                return false;
            }
        });
    }

    //库位筛选
    private void setPos() {
        boolean isContain = false;
//                        int code = Integer.parseInt(tvInputPos.getText().toString());
        for (int i = 0; i < posList.size(); i++) {
            try {
                if ((etPos.getText().toString()).equals(posList.get(i).getSBerChID())) {
//                    if (posId != posList.get(i).getIRecNo())
//                        canSubmit = false;
                    posId = posList.get(i).getIRecNo();
                    posName = posList.get(i).getSBerChID();
                    etPos.setText(posName);
                    isContain = true;
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        if (!isContain) {
//            if (posId != 0)
//                canSubmit = false;
            posId = 0;
            posName = "";
            etPos.setText("");
            Toasts.showShort(StorageSearchActivity.this, "仓位不存在");
        } else {
            search();
        }
    }

    private void intiStorePick() {
        pvStore = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                if (storeId != storeList.get(options1).getIRecNo()) {
                    posList.clear();
                    posId = 0;
                    posName = "";
                    pvPos = null;
                    etPos.setText("");

                    storeId = storeList.get(options1).getIRecNo();
                    storeName = storeList.get(options1).getPickerViewText();
                    tvStore.setText(storeList.get(options1).getPickerViewText());
                }

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
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                    }
                })
                .build();

        pvStore.setPicker(storeList);//一级选择器
        Dialog mDialog = pvStore.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);
            params.leftMargin = 0;
            params.rightMargin = 0;
            pvStore.getDialogContainerLayout().setLayoutParams(params);
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
//        pvStore.show();
    }

    private void intiPosPick() {
        pvPos = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                if (posId != posList.get(options1).getIRecNo()) {
                    posId = posList.get(options1).getIRecNo();
                    posName = posList.get(options1).getPickerViewText();
                    etPos.setText(posList.get(options1).getPickerViewText());
                    etBarcode.requestFocus();
                    search();
                }

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
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                    }
                })
                .build();

        pvPos.setPicker(posList);//一级选择器
        Dialog mDialog = pvPos.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);
            params.leftMargin = 0;
            params.rightMargin = 0;
            pvPos.getDialogContainerLayout().setLayoutParams(params);
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
        pvPos.show();
    }

    //获取仓库信息
    private void getData() {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(getParams(), url, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                Log.e("Data", string);
                Gson gson = new Gson();
                ExchangePosBean data = gson.fromJson(StringUtil.getHaiHongData(string), ExchangePosBean.class);
                if (data.isSuccess()) {
                    if (data.getTables() != null) {
                        if (data.getTables().get(0) != null && data.getTables().get(0).size() > 0) {
                            storeList.addAll(data.getTables().get(0));
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    storeId = storeList.get(0).getIRecNo();
                                    storeName = storeList.get(0).getSStockName();
                                    tvStore.setText(storeName);
                                    LoadingDialog.cancelDialogForLoading();
                                    intiStorePick();

                                }
                            });
                        } else {
                            loadFail("无数据");
                        }
                    } else {
                        loadFail("无数据");
                    }
                } else {
                    loadFail((String) data.getMessage());
                }
            }

            @Override
            public void onFail(IOException e) {
                loadFail("加载失败");
                e.printStackTrace();
            }
        });
    }


    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("callback", ""));
        params.add(new NetParams("otype", "GetStockM"));
        params.add(new NetParams("filters", "sType='成品'"));
        params.add(new NetParams("userid", userid));
        return params;
    }

    //获取仓位信息
    private void getPosData(int type) {
        new NetUtil(getInputParams(), url, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                try {
                    Gson gson = new Gson();
                    ExchangeInputPosBean data = gson.fromJson(StringUtil.getHaiHongData(string), ExchangeInputPosBean.class);
                    if (data.isSuccess()) {
                        if (data.getTables() != null) {
                            if (data.getTables().get(0) != null && data.getTables().get(0).size() > 0) {
                                posList.clear();
                                posList.addAll(data.getTables().get(0));
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        LoadingDialog.cancelDialogForLoading();
                                        if (type == 1)
                                            setPos();
                                        else
                                            intiPosPick();
                                    }
                                });
                            } else {
                                loadFail("无数据");
                            }
                        } else {
                            loadFail("无数据");
                        }
                    } else {
                        loadFail((String) data.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    loadFail("数据解析错误");
                }

            }

            @Override
            public void onFail(IOException e) {

            }
        });
    }

    private List<NetParams> getInputParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("callback", ""));
        params.add(new NetParams("otype", "GetStockD"));
        params.add(new NetParams("userid", userid));
        params.add(new NetParams("iBscDataStockMRecNo", storeId + ""));
        return params;
    }

    private void search() {
        if (TextUtils.isEmpty(tvStore.getText().toString())) {
            Toasts.showLong(StorageSearchActivity.this, "仓库不能为空");
            return;
        }
        if (TextUtils.isEmpty(etPos.getText().toString()) && TextUtils.isEmpty(etBarcode.getText().toString()) && TextUtils.isEmpty(etColor.getText().toString())) {
            Toasts.showLong(StorageSearchActivity.this, "仓位、条码、色号不能都为空");
            return;
        }
        if (mAdapter != null) {
            searchList.clear();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    refreshList();
                }
            });
        }
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(getSearchParams(), url, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                try {
                    Gson gson = new Gson();
                    StorageSearchBean searchBean = gson.fromJson(StringUtil.getHaiHongData(string), StorageSearchBean.class);
                    if (searchBean.isSuccess()) {
                        if (searchBean.getTables().size() > 0 && searchBean.getTables().get(0).size() > 0) {
                            searchList.clear();
                            searchList.addAll(searchBean.getTables().get(0));
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    refreshList();
                                    loadFail("");
                                }
                            });
                        } else {
                            loadFail("无数据");
                        }
                    } else {
                        loadFail(searchBean.getMessage());
                    }
                } catch (Exception e) {
                    loadFail("数据加载失败");
                }

            }

            @Override
            public void onFail(IOException e) {
                e.printStackTrace();
                loadFail("获取数据失败");
            }
        });

    }

    private List<NetParams> getSearchParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("otype", "GetStockQty"));
        params.add(new NetParams("callback", ""));
        params.add(new NetParams("iBscDataStockMRecNo", storeId + ""));
        params.add(new NetParams("sBerChID", etPos.getText().toString()));
        params.add(new NetParams("sCustColorID", etColor.getText().toString()));
        params.add(new NetParams("sBarCode", etBarcode.getText().toString()));
        return params;
    }

    private void refreshList() {
        if (mAdapter == null) {
            mAdapter = new StorageSearchAdapter(this, searchList);
            rvStorage.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    @OnClick({R.id.tv_store, R.id.iv_pos, R.id.iv_back, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_store:
                if (storeList.size() < 0 || storeList.size() == 0) {
                    getData();
                }
                pvStore.show();
                break;
            case R.id.iv_pos:
                if (posList.size() == 0) {
                    getPosData(2);
                } else {
                    if (pvPos != null)
                        pvPos.show();
                    else {
                        intiPosPick();
                    }
                }
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_right:

                search();
                break;
            default:
                break;
        }
    }


    private void loadFail(String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LoadingDialog.cancelDialogForLoading();
                if (StringUtil.isNotEmpty(message))
                    Toasts.showLong(StorageSearchActivity.this, message);
            }
        });
    }

    public void hintKeyBoard() {
        //拿到InputMethodManager
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //如果window上view获取焦点 && view不为空
        if (imm.isActive() && getCurrentFocus() != null) {
            //拿到view的token 不为空
            if (getCurrentFocus().getWindowToken() != null) {
                //表示软键盘窗口总是隐藏，除非开始时以SHOW_FORCED显示。
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
}
