package com.yyy.yongli.exchange;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import com.yyy.yongli.BaseActivity;
import com.yyy.yongli.R;
import com.yyy.yongli.dialog.LoadingDialog;
import com.yyy.yongli.interfaces.ResponseListener;
import com.yyy.yongli.login.LoginBean;
import com.yyy.yongli.model.haihong.ExchangeBarcodeBean;
import com.yyy.yongli.model.haihong.ExchangeDetailBean;
import com.yyy.yongli.model.haihong.ExchangeInputPosBean;
import com.yyy.yongli.model.haihong.ExchangePosBean;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExchangDetailActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;

    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_length)
    TextView tvLength;
    @BindView(R.id.tv_weight)
    TextView tvWeight;
    @BindView(R.id.rv_item)
    RecyclerView rvItem;
    @BindView(R.id.tv_syn)
    TextView tvSyn;
    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.tv_output)
    TextView tvOutput;
    @BindView(R.id.tv_input)
    TextView tvInput;
    @BindView(R.id.tv_input_pos)
    EditText tvInputPos;
    @BindView(R.id.et_barcode)
    EditText etBarcode;

    String ListId;
    int ListInputId;
    int ListInputPosId = 0;
    String ListInputName;
    String ListInputPosName;
    int ListOutputId;
    String ListOutputName;
    int inputSelected = 0;
    int outputSelected = 0;

    String url;
    int count;
    double length;
    double weight;

    SharedPreferencesHelper preferencesHelper;

    String userid;

    ExchangeDetailAdapter adapter;

    List<ExchangeDetailBean.TablesBean> list;
    List<ExchangePosBean.TablesBean> posList;
    List<ExchangeInputPosBean.TablesBean> inputPosList;
    private OptionsPickerView pvInput;
    private OptionsPickerView pvInputPos;
    private OptionsPickerView pvOutput;
    Set<String> codes;
    boolean canSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchang_detail);
        ButterKnife.bind(this);
        preferencesHelper = new SharedPreferencesHelper(this, getString(R.string.preferenceCache));
        inti();
    }

    private void inti() {
        userid = (String) preferencesHelper.getSharedPreference("userid", "");
        list = new ArrayList<>();
        posList = new ArrayList<>();
        inputPosList = new ArrayList<>();
        codes = new HashSet<>();

        ivRight.setVisibility(View.GONE);
        tvTitle.setText("扫描调拨");
        rvItem.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        ListId = intent.getStringExtra("id");
        if (TextUtils.isEmpty(ListId)) {
            canSubmit = false;
        } else canSubmit = true;
        ListInputId = intent.getIntExtra("inputId", 0);
        ListInputName = intent.getStringExtra("inputName");
        ListOutputId = intent.getIntExtra("outputId", 0);
        ListOutputName = intent.getStringExtra("outputName");

        count = intent.getIntExtra("count", 0);
        length = intent.getDoubleExtra("length", 0);
        weight = intent.getDoubleExtra("weight", 0);
        tvNum.setText("总匹数：" + count);
        tvLength.setText("总米数：" + length);
        tvWeight.setText("总重量：" + weight);
        if (StringUtil.isNotEmpty(ListInputName))
            tvInput.setText(ListInputName);
        if (StringUtil.isNotEmpty(ListOutputName))
            tvOutput.setText(ListOutputName);

        url = NetConfig.url + NetConfig.Pda_Method;
        intiEdit();
        if (StringUtil.isNotEmpty(ListId))
            getData();
    }

    private void intiEdit() {
        etBarcode.setInputType(InputType.TYPE_TEXT_VARIATION_URI);
        etBarcode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    hintKeyBoard();
                    String code = etBarcode.getText().toString();
                    if (StringUtil.isNotEmpty(code)) {
                        if (codes.contains(code)) {
                            Toasts.showShort(ExchangDetailActivity.this, "条码已存在");
                            etBarcode.setText("");
                        } else {
                            getBarcodeData(code);
                        }
                    }
                }
                return false;
            }
        });

        tvInputPos.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    hintKeyBoard();
                    if (inputPosList.size() == 0 && TextUtils.isEmpty(tvInputPos.getText().toString())) {
                        Toasts.showShort(ExchangDetailActivity.this, "无数据");
                        ListInputPosName = "";
                        ListInputPosId = 0;

                    } else {
                        if (ListInputId != 0 && inputPosList.size() == 0) {
                            getInputPos(1);
                        } else {
                            setTvInput();
                        }
                    }
                }
                return false;
            }
        });
    }


    //库位筛选
    private void setTvInput() {
        boolean isContain = false;
//                        int code = Integer.parseInt(tvInputPos.getText().toString());
        for (int i = 0; i < inputPosList.size(); i++) {
            try {
                if ((tvInputPos.getText().toString()).equals(inputPosList.get(i).getSBerChID())) {
                    if (ListInputPosId != inputPosList.get(i).getIRecNo())
                        canSubmit = false;
                    ListInputPosId = inputPosList.get(i).getIRecNo();
                    ListInputPosName = inputPosList.get(i).getSBerChID();
                    tvInputPos.setText(ListInputPosName);
                    isContain = true;
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        if (!isContain) {
            if (ListOutputId != 0)
                canSubmit = false;
            ListInputPosId = 0;
            ListInputPosName = "";
            tvInputPos.setText("");
            Toasts.showShort(ExchangDetailActivity.this, "库位不存在");
        }
    }

    //获取条码数据
    private void getBarcodeData(String code) {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(getBarcodeParams(code), url, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            etBarcode.setText("");
                            etBarcode.requestFocus();

                        }
                    });
                    Gson gson = new Gson();
                    ExchangeBarcodeBean data = gson.fromJson(StringUtil.getHaiHongData(string), ExchangeBarcodeBean.class);
                    if (data.isSuccess()) {
                        if (data.getTables() != null && data.getTables().get(0) != null && data.getTables().get(0).size() > 0) {
                            List<ExchangeBarcodeBean.TablesBean> list = data.getTables().get(0);
                            canSubmit = false;
//                            codes.add(etBarcode.getText().toString());

                            for (int i = 0; i < list.size(); i++) {
                                codes.add(list.get(i).getSBarCode());
                                codes.add(list.get(i).getSTrayCode());
                                ExchangeDetailBean.TablesBean barcodes = new ExchangeDetailBean.TablesBean();
                                barcodes.setSBarCode(list.get(i).getSBarCode());
                                barcodes.setSTrayCode(list.get(i).getSTrayCode());
                                barcodes.setFQty(list.get(i).getFQty());
                                barcodes.setFPurQty(list.get(i).getFPurQty());
                                barcodes.setSName(list.get(i).getSName());
                                barcodes.setSOutBerChID(list.get(i).getSBerChID());
                                barcodes.setSOutOrderNo(list.get(i).getSOutOrderNo());
                                barcodes.setSColorName(list.get(i).getsColorName());
                                barcodes.setSBatchNo(list.get(i).getSBatchNo());
                                count = count + list.get(i).getiQtyNew();
                                weight = weight + list.get(i).getFPurQty();
                                length = length + list.get(i).getFQty();
                                ExchangDetailActivity.this.list.add(barcodes);
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    refresh();
                                    LoadingDialog.cancelDialogForLoading();


                                }
                            });
                        } else {
                            loadFail("无数据");
                        }
                    } else {
                        loadFail((String) data.getMessage());
                    }
                } catch (Exception e) {
                    loadFail("数据解析错误");
                    e.printStackTrace();
                }

            }

            @Override
            public void onFail(IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        etBarcode.setText("");
                        etBarcode.requestFocus();

                    }
                });
                loadFail("加载失败");
                e.printStackTrace();
            }
        });
    }

    private List<NetParams> getBarcodeParams(String sBarCode) {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("callback", ""));
        params.add(new NetParams("otype", "GetBarcodeFromStockQtyByOnlyBarcodeOrPileNo"));
        params.add(new NetParams("sBarCode", sBarCode));
        params.add(new NetParams("iBscDataStockMRecNo", ListOutputId + ""));
        //        params.add(new NetParams("sBarCode", "00458058"));
        //        params.add(new NetParams("iBscDataStockMRecNo", "134" + ""));
        params.add(new NetParams("userid", userid));
        return params;


    }

    /*获取援用数据*/
    private void getData() {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(getParams(), url, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                Log.e("data1", string);
                try {
                    Gson gson = new Gson();
                    ExchangeDetailBean data = gson.fromJson(StringUtil.getHaiHongData(string), ExchangeDetailBean.class);
                    if (data.isSuccess()) {
                        if (data.getTables() != null) {
                            if (data.getTables().get(0) != null && data.getTables().get(0).size() > 0) {
                                list.addAll(data.getTables().get(0));
                                for (int i = 0; i < list.size(); i++) {
                                    codes.add(list.get(i).getSBarCode());
                                }
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        LoadingDialog.cancelDialogForLoading();
                                        tvInputPos.setText(list.get(0).getSInBerChID());
                                        ListInputPosId = list.get(0).getIInBscDataStockDRecNo();
                                        ListInputPosName = list.get(0).getSInBerChID();
                                        refresh();
                                    }
                                });
//                            refresh();
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
                    e.printStackTrace();
                    loadFail("数据解析错误");
                }

            }

            @Override
            public void onFail(IOException e) {
                loadFail("加载失败");
            }
        });
    }

    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("otype", "GetPDANotSubmitMMStockProductDbD1"));
        params.add(new NetParams("callback", ""));
        params.add(new NetParams("iRecNo", ListId));
        return params;
    }

    //刷新列表
    private void refresh() {
        if (adapter == null) {
            adapter = new ExchangeDetailAdapter(this, list);
            rvItem.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        tvNum.setText("总匹数：" + count);
        tvLength.setText("总米数：" + length);
        tvWeight.setText("总重量：" + weight);
    }

    //获取仓库信息
    private void getPos(int type) {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(getPosParams(), url, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                Log.e("Data", string);
                Gson gson = new Gson();
                ExchangePosBean data = gson.fromJson(StringUtil.getHaiHongData(string), ExchangePosBean.class);
                if (data.isSuccess()) {
                    if (data.getTables() != null) {
                        if (data.getTables().get(0) != null && data.getTables().get(0).size() > 0) {
                            posList.addAll(data.getTables().get(0));
                            for (int i = 0; i < posList.size(); i++) {
                                if (ListInputId == posList.get(i).getIRecNo())
                                    inputSelected = i;
                                if (ListOutputId == posList.get(i).getIRecNo())
                                    outputSelected = i;
                                if (outputSelected != 0 && inputSelected != 0)
                                    break;
                            }
//                            if (ListInputId != 0) {
//                                inputSelected = posList.indexOf(new ExchangePosBean.TablesBean(ListInputId, ListInputName)) == -1 ? 0 : posList.indexOf(new ExchangePosBean.TablesBean(ListInputId, ListInputName));
//                            }
//                            if (ListOutputId != 0)
//                                outputSelected = posList.indexOf(new ExchangePosBean.TablesBean(ListOutputId, ListOutputName)) == -1 ? 0 : posList.indexOf(new ExchangePosBean.TablesBean(ListOutputId, ListOutputName));

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    LoadingDialog.cancelDialogForLoading();
                                    if (type == 1)
                                        intiOutputPick();
                                    else if (type == 2)
                                        intiInputPick();
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

    private List<NetParams> getPosParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("callback", ""));
        params.add(new NetParams("otype", "GetStockM"));
        params.add(new NetParams("filters", "sType='成品'"));
        params.add(new NetParams("userid", userid));
        return params;
    }

    //获取调入仓位信息
    private void getInputPos(int type) {
        new NetUtil(getInputParams(), url, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                try {
                    Gson gson = new Gson();
                    ExchangeInputPosBean data = gson.fromJson(StringUtil.getHaiHongData(string), ExchangeInputPosBean.class);
                    if (data.isSuccess()) {
                        if (data.getTables() != null) {
                            if (data.getTables().get(0) != null && data.getTables().get(0).size() > 0) {
                                inputPosList.clear();
                                inputPosList.addAll(data.getTables().get(0));
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        LoadingDialog.cancelDialogForLoading();
                                        if (type == 1)
                                            setTvInput();
                                        else
                                            intiInputPosPick();
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
        params.add(new NetParams("iBscDataStockMRecNo", ListInputId + ""));
        return params;
    }

    //删除接口
    private void deleteOrder() {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(deleteParams(), url, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                try {
                    Log.e("Delete", string);
                    Gson gson = new Gson();
                    LoginBean data = gson.fromJson(StringUtil.getHaiHongData(string), LoginBean.class);
                    if (data.isSuccess()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                LoadingDialog.cancelDialogForLoading();
                                Toasts.showShort(ExchangDetailActivity.this, "删除成功");
                                setResult(100);
                                finish();
                            }
                        });

                    } else {
                        loadFail(data.getMessage());
                    }
                } catch (Exception e) {
                    loadFail("数据解析错误");
                }

            }

            @Override
            public void onFail(IOException e) {
                loadFail("请求失败");
                e.printStackTrace();
            }
        });
    }

    private List<NetParams> deleteParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("callback", ""));
        params.add(new NetParams("otype", "MMStockProductDbMDelete1"));
        params.add(new NetParams("iRecNo", ListId + ""));
        return params;
    }

    //保存接口
    private void saveData(int type) {
        saveParams();
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(saveParams(), url, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                try {
                    Log.e("data", string);
                    Gson gson = new Gson();
                    LoginBean data = gson.fromJson(StringUtil.getHaiHongData(string), LoginBean.class);
                    if (data.isSuccess()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                LoadingDialog.cancelDialogForLoading();
                                if (type == 1) {
                                    Toasts.showShort(ExchangDetailActivity.this, "保存成功");
                                    setResult(100);
                                    finish();
                                } else {
                                    ListId = data.getMessage();
                                    submit();
                                }
                            }
                        });

                    } else {
                        loadFail(data.getMessage());
                    }
                } catch (Exception e) {
                    loadFail("数据解析错误");
                }

            }

            @Override
            public void onFail(IOException e) {
                loadFail("请求失败");
            }
        });
    }

    private List<NetParams> saveParams() {
        String codes = "";
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                if (TextUtils.isEmpty(list.get(i).getSBarCode()))
                    codes = list.get(i).getSTrayCode();
                else
                    codes = list.get(i).getSBarCode();
            } else {
                if (TextUtils.isEmpty(list.get(i).getSBarCode()))
                    codes = codes + "," + list.get(i).getSTrayCode();
                else
                    codes = codes + "," + list.get(i).getSBarCode();
            }
        }
        Log.e("code", codes);
        String id = TextUtils.isEmpty(ListId) ? "0" : ListId + "";

        List<NetParams> params = new ArrayList<>();
//        params.add(new NetParams("iMMStockProductDbMRecNo", "0"));
//        params.add(new NetParams("userid", userid));
//        params.add(new NetParams("otype", "SaveMMStockProductDb1"));
//        params.add(new NetParams("iOutBscDataStockMRecNo", 192 + ""));
//        params.add(new NetParams("iInBscDataStockMRecNo", 192 + ""));
//        params.add(new NetParams("iInBscDataStockDRecNo", ListInputPosId != 0 ? ListInputPosId + "" : ""));
//        params.add(new NetParams("iInBscDataStockDRecNo", 0 + ""));

        params.add(new NetParams("iMMStockProductDbMRecNo", id));
        params.add(new NetParams("userid", userid));
        params.add(new NetParams("otype", "SaveMMStockProductDb1"));
        params.add(new NetParams("iOutBscDataStockMRecNo", ListOutputId + ""));
        params.add(new NetParams("iInBscDataStockMRecNo", ListInputId + ""));
////        params.add(new NetParams("iInBscDataStockDRecNo", ListInputPosId != 0 ? ListInputPosId + "" : ""));
        params.add(new NetParams("iInBscDataStockDRecNo", ListInputPosId + ""));


        params.add(new NetParams("sBarcodes", codes));
        Log.e("iInBscDataStockDRecNo", ListInputPosId + "");
        Log.e("iOutBscDataStockMRecNo", ListOutputId + "");
        Log.e("iInBscDataStockMRecNo", ListInputId + "");
        Log.e("sBarcodes", codes + "");
        Log.e("userid", userid + "");
        Log.e("iMMStockProductDbMRecNo", ListId + "");
        return params;
    }

    //提交接口
    private void submit() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LoadingDialog.showDialogForLoading(ExchangDetailActivity.this);
            }
        });

        new NetUtil(submitParams(), url, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                try {
                    Gson gson = new Gson();
                    LoginBean data = gson.fromJson(StringUtil.getHaiHongData(string), LoginBean.class);
                    if (data.isSuccess()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                LoadingDialog.cancelDialogForLoading();
                                Toasts.showShort(ExchangDetailActivity.this, "提交成功");
                                setResult(100);
                                finish();
                            }
                        });

                    } else {
                        loadFail(data.getMessage());
                    }
                } catch (Exception e) {
                    loadFail("数据解析错误");
                }
            }

            @Override
            public void onFail(IOException e) {
                loadFail("请求失败");
            }
        });
    }

    private List<NetParams> submitParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("callback", ""));
        params.add(new NetParams("otype", "MMStockProductDbMSubmit1"));
        params.add(new NetParams("iRecNo", ListId));
        return params;
    }

    @OnClick({R.id.iv_back, R.id.tv_output, R.id.tv_input, R.id.iv_input_pos, R.id.tv_syn, R.id.tv_delete, R.id.tv_save, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_output:
                if (posList == null || posList.size() == 0) {
                    getPos(1);
                } else {
                    if (pvOutput == null)
                        intiOutputPick();
                    else
                        pvOutput.show();
                }
                break;
            case R.id.tv_input:
                if (posList == null || posList.size() == 0) {
                    getPos(2);
                } else {
                    if (pvInput == null)
                        intiInputPick();
                    else
                        pvInput.show();
                }
                break;
            case R.id.iv_input_pos:
                if (ListInputId != 0) {
                    if (pvInputPos == null)
                        getInputPos(2);
                    else {
                        if (pvInput == null)
                            intiInputPosPick();
                        else
                            pvInputPos.show();
                    }
                } else {
                    Toasts.showShort(ExchangDetailActivity.this, "调入仓库不能为空");
                }
                break;
            case R.id.tv_syn:
                break;
            case R.id.tv_delete:
                if (ListId == "") {
                    finish();
                } else {
                    deleteOrder();
                }
                break;
            case R.id.tv_save:
                canSave(1);
//                saveData(1);
                break;
            case R.id.tv_submit:
                if (!canSubmit) {
                    canSave(2);
                } else {
                    submit();
                }

                break;
            default:
                break;
        }
    }

    private void canSave(int i) {
        if (ListInputId == 0) {
            Toasts.showShort(this, "调入仓库不能为空");
            return;
        }
        if (ListOutputId == 0) {
            Toasts.showShort(this, "调出仓库不能为空");
            return;
        }
        if (list.size() == 0) {
            Toasts.showShort(this, "条码不能为空");
            return;
        }
        saveData(i);
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

    private void intiOutputPick() {
        pvOutput = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
//                String tx = options1Items.get(options1).getPickerViewText()
//                        + options2Items.get(options1).get(options2)
                /* + options3Items.get(options1).get(options2).get(options3).getPickerViewText()*/
                ListOutputId = posList.get(options1).getIRecNo();
                ListOutputName = posList.get(options1).getPickerViewText();
                tvOutput.setText(posList.get(options1).getPickerViewText());

            }
        })
                .setTitleText("调出仓库选择")
                .setContentTextSize(18)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setSelectOptions(outputSelected)//默认选中项
//                .setBgColor(Color.BLACK)
//                .setTitleBgColor(Color.DKGRAY)
//                .setTitleColor(Color.LTGRAY)
//                .setCancelColor(Color.YELLOW)
//                .setSubmitColor(Color.YELLOW)
//                .setTextColorCenter(Color.LTGRAY)
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setLabels("", "", "")
                .isDialog(true)
                .setBgColor(0xFFFFFFFF) //设置外部遮罩颜色
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
//                        String str = "options1: " + options1 + "\noptions2: " + options2 + "\noptions3: " + options3;
//                        Toast.makeText(StorageActivity.this, str, Toast.LENGTH_SHORT).show();
                    }
                })
                .build();

//        pvOptions.setSelectOptions(1,1);
        pvOutput.setPicker(posList);//一级选择器
//        pvCustom.setPicker(options1Items, options2Items);//二级选择器
        /*pvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器*/
        Dialog mDialog = pvOutput.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);
            params.leftMargin = 0;
            params.rightMargin = 0;
            pvOutput.getDialogContainerLayout().setLayoutParams(params);
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
        pvOutput.show();
    }

    private void intiInputPick() {
        pvInput = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
//                String tx = options1Items.get(options1).getPickerViewText()
//                        + options2Items.get(options1).get(options2)
                /* + options3Items.get(options1).get(options2).get(options3).getPickerViewText()*/
                if (ListInputId != posList.get(options1).getIRecNo())
                    canSubmit = false;
                ListInputId = posList.get(options1).getIRecNo();
                ListInputName = posList.get(options1).getPickerViewText();
                tvInput.setText(posList.get(options1).getPickerViewText());
                tvInputPos.setText("");
                ListInputPosName = "";
                ListInputPosId = 0;
                pvInputPos = null;
                inputPosList.clear();
            }
        })
                .setTitleText("调入仓库选择")
                .setContentTextSize(18)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setSelectOptions(inputSelected)//默认选中项
//                .setBgColor(Color.BLACK)
//                .setTitleBgColor(Color.DKGRAY)
//                .setTitleColor(Color.LTGRAY)
//                .setCancelColor(Color.YELLOW)
//                .setSubmitColor(Color.YELLOW)
//                .setTextColorCenter(Color.LTGRAY)
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setLabels("", "", "")
                .isDialog(true)
                .setBgColor(0xFFFFFFFF) //设置外部遮罩颜色
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
//                        String str = "options1: " + options1 + "\noptions2: " + options2 + "\noptions3: " + options3;
//                        Toast.makeText(StorageActivity.this, str, Toast.LENGTH_SHORT).show();
                    }
                })
                .build();

//        pvOptions.setSelectOptions(1,1);
        pvInput.setPicker(posList);//一级选择器
//        pvCustom.setPicker(options1Items, options2Items);//二级选择器
        /*pvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器*/
        Dialog mDialog = pvInput.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);
            params.leftMargin = 0;
            params.rightMargin = 0;
            pvInput.getDialogContainerLayout().setLayoutParams(params);
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
        pvInput.show();
    }

    private void intiInputPosPick() {
        pvInputPos = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
//                String tx = options1Items.get(options1).getPickerViewText()
//                        + options2Items.get(options1).get(options2)
                /* + options3Items.get(options1).get(options2).get(options3).getPickerViewText()*/
                if (ListInputPosId != inputPosList.get(options1).getIRecNo())
                    canSubmit = false;
                ListInputPosId = inputPosList.get(options1).getIRecNo();
                ListInputPosName = inputPosList.get(options1).getPickerViewText();
                tvInputPos.setText(inputPosList.get(options1).getPickerViewText());

            }
        })
                .setTitleText("调入仓位选择")
                .setContentTextSize(18)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setSelectOptions(0)//默认选中项
//                .setBgColor(Color.BLACK)
//                .setTitleBgColor(Color.DKGRAY)
//                .setTitleColor(Color.LTGRAY)
//                .setCancelColor(Color.YELLOW)
//                .setSubmitColor(Color.YELLOW)
//                .setTextColorCenter(Color.LTGRAY)
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setLabels("", "", "")
                .isDialog(true)
                .setBgColor(0xFFFFFFFF) //设置外部遮罩颜色
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
//                        String str = "options1: " + options1 + "\noptions2: " + options2 + "\noptions3: " + options3;
//                        Toast.makeText(StorageActivity.this, str, Toast.LENGTH_SHORT).show();
                    }
                })
                .build();

//        pvOptions.setSelectOptions(1,1);
        pvInputPos.setPicker(inputPosList);//一级选择器
//        pvCustom.setPicker(options1Items, options2Items);//二级选择器
        /*pvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器*/
        Dialog mDialog = pvInputPos.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);
            params.leftMargin = 0;
            params.rightMargin = 0;
            pvInputPos.getDialogContainerLayout().setLayoutParams(params);
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
        pvInputPos.show();
    }

    private void loadFail(String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LoadingDialog.cancelDialogForLoading();
                Toasts.showShort(ExchangDetailActivity.this, message);
            }
        });
    }

}
