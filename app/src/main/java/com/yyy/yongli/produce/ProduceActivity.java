package com.yyy.yongli.produce;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
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
import com.yyy.yongli.lookup.LookUpActivity;
import com.yyy.yongli.lookup.LookUpBean;
import com.yyy.yongli.lookup.LookUpUtil;
import com.yyy.yongli.util.NetConfig;
import com.yyy.yongli.util.NetParams;
import com.yyy.yongli.util.NetUtil;
import com.yyy.yongli.util.PxUtil;
import com.yyy.yongli.util.StringUtil;
import com.yyy.yongli.util.Toasts;
import com.yyy.yongli.view.EditItem;
import com.yyy.yongli.view.ScanItem;
import com.yyy.yongli.view.SelectItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProduceActivity extends AppCompatActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.item_product)
    ScanItem itemProduct;
    @BindView(R.id.item_check)
    SelectItem itemCheck;
    @BindView(R.id.item_worker)
    ScanItem itemWorker;
    @BindView(R.id.item_code)
    EditItem itemCode;
    @BindView(R.id.item_standard)
    EditItem itemStandard;
    @BindView(R.id.item_scrap)
    EditItem itemScrap;
    @BindView(R.id.item_scrap_response)
    SelectItem itemScrapResponse;
    @BindView(R.id.item_scrap_reason)
    SelectItem itemScrapReason;
    @BindView(R.id.item_rework)
    EditItem itemRework;
    @BindView(R.id.item_rework_response)
    SelectItem itemReworkResponse;
    @BindView(R.id.item_rework_reason)
    SelectItem itemReworkReason;
    @BindView(R.id.item_rework_process)
    SelectItem itemReworkProcess;
    @BindView(R.id.ll_content)
    LinearLayout llContent;

    String Process;
    String Worker;
    String Check;
    String ReworkReason;
    String ScrapReason;

    String productId;//产量工序id
    String code;//条码
    String workerId;//员工id
    String workerLinkId;//员工部门id
    String checkId;//检验工序id
    String scarpReasonId;//报废原因id
    String scarpResponseId;//报废工序id
    String reworkReasonId;//返修原因id
    String rewordResponseId;//返修责任工序原因id
    String reWorkProcessId;//返修工序id

    List<LookUpBean> processses;
    List<LookUpBean> workers;
    List<LookUpBean> checks;
    List<LookUpBean> reworkReasons;
    List<LookUpBean> scrapReasons;
    List<Items> items = new ArrayList<>();

    boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produce);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        ivRight.setVisibility(View.GONE);
        tvRight.setText("提交");
        tvRight.setVisibility(View.VISIBLE);

        tvTitle.setText("产量扫描");
        itemProduct.setTitle("产量工序：");
        itemProduct.setHint("输入或扫描产量工序");
        itemProduct.setInputType(InputType.TYPE_CLASS_NUMBER);


        itemCheck.setTitle("检验工序：");
        itemCheck.setHint("请选择检验工序");
        itemCheck.setVisibility(View.GONE);

        itemWorker.setTitle("员工：");
        itemWorker.setHint("输入或扫描员工");
        itemWorker.setInputType(InputType.TYPE_CLASS_NUMBER);

        itemCode.setTitle("条码：");
        itemCode.setHint("请输入条码");
        itemCode.setInputType(InputType.TYPE_CLASS_NUMBER);
        itemCode.setDigits("0123456789");


        itemStandard.setTitle("合格数量：");
        itemStandard.setInputType(InputType.TYPE_CLASS_NUMBER);
        itemStandard.setDigits("0123456789");
        itemStandard.setHint("请输入合格数量");

        itemScrap.setTitle("报废数量：");
        itemScrap.setInputType(InputType.TYPE_CLASS_NUMBER);
        itemScrap.setDigits("0123456789");
        itemScrap.setHint("请输入报废数量");

        itemScrapResponse.setTitle("责任工序：");
        itemScrapResponse.setHint("请选择报废责任工序");


        itemScrapReason.setTitle("报废原因：");
        itemScrapReason.setHint("请选择报废原因");


        itemRework.setTitle("返修数量：");
        itemRework.setInputType(InputType.TYPE_CLASS_NUMBER);
        itemRework.setDigits("0123456789");
        itemRework.setHint("请输入返修数量");

        itemReworkProcess.setTitle("返修工序：");
        itemReworkProcess.setHint("请选择返修工序");


        itemReworkReason.setTitle("返修原因：");
        itemReworkReason.setHint("请选择返修原因");


        itemReworkResponse.setTitle("责任工序：");
        itemReworkResponse.setHint("请选择返修责任工序");


        processses = new ArrayList<>();
        workers = new ArrayList<>();
        reworkReasons = new ArrayList<>();
        scrapReasons = new ArrayList<>();
        setEntry();
        setWather();
        getData();
    }

    private void setClick() {
//        Log.e("TAG", "i：" + items.size());
        for (int i = 0; i < items.size(); i++) {
//            Log.e("TAG", "i：" + i);
            Items item = items.get(i);
            if (item.getSelectItem() != null) {
                items.get(i).getSelectItem().setOnSelectClickListener(new OnSelectClickListener() {
                    @Override
                    public void onSelectClick(View view) {
                        Log.e("click", "clicked");
                        goSelect(item.getTag(), item.getData(), item.getTitle());
                    }
                });
            } else {
                item.getScanItem().setOnSelectClickListener(new OnSelectClickListener() {
                    @Override
                    public void onSelectClick(View view) {
                        goSelect(item.getTag(), item.getData(), item.getTitle());
                    }
                });
            }
        }
    }

    /*跳转LookUp*/
    private void goSelect(int tag, String data, String title) {
        Intent intent = new Intent();
        intent.putExtra("code", tag);
        intent.putExtra("data", data);
        intent.putExtra("title", title);
        intent.setClass(this, LookUpActivity.class);
        startActivityForResult(intent, tag);
    }


    private void getData() {
        LoadingDialog.cancelDialogForLoading();
        isLoading = true;
        new NetUtil(getParams(), NetConfig.url + NetConfig.Produce_Method, new ResponseListener() {
            @Override
            public void onSuccess(String string) {

                try {
                    JSONObject jsonObject = new JSONObject(string);
                    boolean isSucess = jsonObject.optBoolean("success");
                    if (isSucess) {
                        String result = jsonObject.optString("dataset");
                        JSONObject dataObject = new JSONObject(result);
                        Process = dataObject.optString("CLGX");
                        Worker = dataObject.optString("YG");
                        ReworkReason = dataObject.optString("FXReason");
                        ScrapReason = dataObject.optString("BFReason");
                        Check = dataObject.optString("JYGX");

                        setSelectItem();
                        setListData();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                LoadingDialog.cancelDialogForLoading();
                                isLoading = false;
                            }
                        });
                    } else {
                        toastMessage(StringUtil.isNotEmpty(jsonObject.optString("message")) ? jsonObject.optString("message") : "无返回信息");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    toastMessage("数据解析错误");
                } catch (Exception error) {
                    toastMessage("网络错误");
                }
            }

            @Override
            public void onFail(IOException e) {
                toastMessage("未知错误");
                e.printStackTrace();

            }
        });
    }

    /*设置List数据*/
    private void setListData() {
        processses = new Gson().fromJson(Process, new TypeToken<List<LookUpBean>>() {
        }.getType());
        workers = new Gson().fromJson(Worker, new TypeToken<List<LookUpBean>>() {
        }.getType());
        checks = new Gson().fromJson(Check, new TypeToken<List<LookUpBean>>() {
        }.getType());
        reworkReasons = new Gson().fromJson(ReworkReason, new TypeToken<List<LookUpBean>>() {
        }.getType());
        scrapReasons = new Gson().fromJson(ScrapReason, new TypeToken<List<LookUpBean>>() {
        }.getType());
    }

    /**
     * 设置lookup
     */
    private void setSelectItem() {
        Log.e("TAG", "setSelectItem");
        if (StringUtil.isNotEmpty(Process)) {
            items.add(new Items(itemProduct, LookUpUtil.PRODUCE_CODE, "产量工序", Process));
            items.add(new Items(itemScrapResponse, LookUpUtil.SCRAP_PROCESS_CODE, "责任工序", Process));
            items.add(new Items(itemReworkProcess, LookUpUtil.REWORK_PROCESS_CODE, "返修工序", Process));
            items.add(new Items(itemReworkResponse, LookUpUtil.REWORK_RESPONSE_CODE, "责任工序", Process));
        }
        if (StringUtil.isNotEmpty(Worker)) {
            items.add(new Items(itemWorker, LookUpUtil.WORKER_CODE, "员工", Worker));
        }
        if (StringUtil.isNotEmpty(ScrapReason)) {
            items.add(new Items(itemScrapReason, LookUpUtil.SCRAP_CODE, "报废原因", ScrapReason));
        }
        if (StringUtil.isNotEmpty(ReworkReason)) {
            items.add(new Items(itemReworkReason, LookUpUtil.REWORKREASON_CODE, "返修原因", ReworkReason));
        }
        if (StringUtil.isNotEmpty(Check)) {
            items.add(new Items(itemCheck, LookUpUtil.CHECK_CODE, "检验工序", Check));

        }
        setClick();
    }

    private void setEntry() {

        itemProduct.setOnEntryListener(new OnEntryListener() {
            @Override
            public void onEntry(View view) {
                String code = itemProduct.getText();
                if (TextUtils.isEmpty(code))
                    productId = "";
                else {
                    if (processses != null && processses.size() > 0)
                        for (int i = 0; i < processses.size(); i++) {
                            if (code.equals(processses.get(i).getsCode())) {
                                productId = code;
                                itemProduct.setContent(processses.get(i).getsName());
                                break;
                            }
                        }
                }
            }
        });
        itemWorker.setOnEntryListener(new OnEntryListener() {
            @Override
            public void onEntry(View view) {
                String code = itemWorker.getText();
                if (TextUtils.isEmpty(code)) {
                    workerId = "";
                    workerLinkId = "";
                } else {
                    if (workers != null && workers.size() > 0)
                        for (int i = 0; i < workers.size(); i++) {
                            if (code.equals(workers.get(i).getsCode())) {
                                workerId = code;
                                workerLinkId = workers.get(i).getsClassID();
                                itemWorker.setContent(workers.get(i).getsName());
                                break;
                            }
                        }
                }
            }
        });
        itemCode.setOnEntryListener(new OnEntryListener() {
            @Override
            public void onEntry(View view) {
                if (StringUtil.isNotEmpty(itemCode.getText()))
                    getCodeDetail();
                else {

                }
            }
        });
    }

    /*s设置数量监听*/
    private void setWather() {
        itemScrap.setOnChangedListener(new OnChangedListener() {
            @Override
            public void onTextChanged(String text) {
//                Log.e("TExt", text);
                try {
                    int number;
                    if (StringUtil.isNotEmpty(text)) {
                        number = Integer.parseInt(text);
                        if (number > 0) {
                            setScrap(true);
                        } else {
                            setScrap(false);
                        }
                    } else {
                        setScrap(false);
                    }
                } catch (NumberFormatException e) {
                    toastMessage("只能输入数字");
                }

            }
        });
        itemRework.setOnChangedListener(new OnChangedListener() {
            @Override
            public void onTextChanged(String text) {
//                Log.e("TExt", text);
                try {
                    int number;
                    if (StringUtil.isNotEmpty(text)) {
                        number = Integer.parseInt(text);
                        if (number > 0) {
                            setRework(true);
                        } else {
                            setRework(false);
                        }
                    } else {
                        setRework(false);
                    }
                } catch (NumberFormatException e) {
                    toastMessage("只能输入数字");
                }

            }
        });
    }

    /*获取条码明细*/
    private void getCodeDetail() {
        LoadingDialog.showDialogForLoading(this);
        isLoading = true;
        new NetUtil(getCodeParams(), NetConfig.url + NetConfig.Produce_Method, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    boolean isSuccess = jsonObject.optBoolean("success");
                    if (isSuccess) {
                        String result = jsonObject.optString("result");
                        List<String> codeDetail = new Gson().fromJson(result, new TypeToken<List<String>>() {
                        }.getType());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                LoadingDialog.cancelDialogForLoading();
                                isLoading = false;
                                setCodeDetai(codeDetail);
                            }
                        });

                    } else {
                        toastMessage(StringUtil.isNotEmpty(jsonObject.optString("message")) ? jsonObject.optString("message") : "无返回信息");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    toastMessage("数据解析错误");
                } catch (Exception error) {
                    toastMessage("未知错误");
                }
            }

            @Override
            public void onFail(IOException e) {
                e.printStackTrace();
                toastMessage("网络错误");
            }
        });
    }

    /*加载条码数据*/
    private void setCodeDetai(List<String> codeDetail) {
        llContent.removeAllViews();
        if (codeDetail != null && codeDetail.size() > 0) {
            code = itemCode.getText();
            llContent.setVisibility(View.VISIBLE);
            for (int i = 0; i < codeDetail.size(); i++) {
                LinearLayout linearLayout;
                if (i == 0 || i % 2 == 1) {
                    linearLayout = getLinear();
                    linearLayout.addView(getItemView(codeDetail.get(i)));
                    llContent.addView(linearLayout);
                } else {
                    linearLayout = (LinearLayout) llContent.getChildAt(llContent.getChildCount() - 1);
                    linearLayout.addView(getItemView(codeDetail.get(i)));
                }
            }
        } else {
            llContent.setVisibility(View.GONE);
        }
    }

    //设置条码每行容器
    private LinearLayout getLinear() {
        LinearLayout linearLayout = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(params);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        return linearLayout;
    }

    /*设置条码详情样式*/
    private TextView getItemView(String text) {
        TextView textView = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
        textView.setLayoutParams(params);
        textView.setTextColor(getResources().getColor(R.color.default_content_color));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.sp_14));
        int padding = PxUtil.dip2px(this, 5);
        textView.setPadding(padding, padding, padding, padding);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setBackgroundColor(getResources().getColor(R.color.white));
        textView.setText(text);
        textView.setSingleLine();
        return textView;
    }

    private List<NetParams> getCodeParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("otype", "GetBarCode"));
        params.add(new NetParams("sBarCode", itemCode.getText()));
        return params;
    }

    private List<NetParams> getParams() {
        List<NetParams> list = new ArrayList<>();
        list.add(new NetParams("otype", "GetLookUp"));
        return list;
    }

    /**
     * 控制是否展开报废原因
     *
     * @param b
     */
    private void setScrap(boolean b) {
        if (b) {
            itemScrapResponse.setVisibility(View.VISIBLE);
            itemScrapReason.setVisibility(View.VISIBLE);
        } else {
            itemScrapReason.setVisibility(View.GONE);
            itemScrapResponse.setVisibility(View.GONE);
        }
    }

    /**
     * 控制是否展开返修原因
     *
     * @param b
     */
    private void setRework(boolean b) {
        if (b) {
            itemReworkResponse.setVisibility(View.VISIBLE);
            itemReworkReason.setVisibility(View.VISIBLE);
            itemReworkProcess.setVisibility(View.VISIBLE);
        } else {
            itemReworkResponse.setVisibility(View.GONE);
            itemReworkReason.setVisibility(View.GONE);
            itemReworkProcess.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.iv_back, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_right:
                sendData();
                break;
            default:
                break;
        }
    }

    private void sendData() {
        Log.e("params:", new Gson().toJson(getSendParam()));
        if (TextUtils.isEmpty(productId)) {
            toastMessage("产量工序不能为空");
            return;
        }
        if (TextUtils.isEmpty(workerId)) {
            toastMessage("员工不能为空");
            return;
        }
        if (!TextUtils.isEmpty(workerId) && TextUtils.isEmpty(workerLinkId)) {
            toastMessage("员工没有部门");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            toastMessage("条码不能为空");
            return;
        }
        LoadingDialog.showDialogForLoading(this);
        isLoading = true;
        new NetUtil(getSendParam(), NetConfig.url + NetConfig.Produce_Method, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                Log.e("data", string);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(string);
                    boolean isSuccess = jsonObject.optBoolean("success");
                    if (isSuccess) {
                        toastMessage("提交成功");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                clearData();
                            }
                        });
                    } else {
                        toastMessage(StringUtil.isNotEmpty(jsonObject.optString("message")) ? jsonObject.optString("message") : "无返回信息");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    toastMessage("数据解析错误");
                } catch (Exception error) {
                    toastMessage("未知错误");
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadingDialog.cancelDialogForLoading();
                        isLoading = false;
                    }
                });
            }

            @Override
            public void onFail(IOException e) {
                e.printStackTrace();
                toastMessage("网络错误");
            }
        });
    }

    private void clearData() {
        //清除条码明细
        llContent.removeAllViews();
        llContent.setVisibility(View.GONE);
        code = "";
        itemCode.setContent(code);
        //清除检验
        checkId = "";
        itemCheck.setContent("");
        //清除合格数据
        itemStandard.setContent("");
        //清除报废数据
        itemScrap.setContent("");

        scarpReasonId = "";
        itemScrapReason.setContent(scarpReasonId);

        scarpResponseId = "";
        itemScrapResponse.setContent(scarpResponseId);
        //清除返修数据
        itemRework.setContent("");

        reworkReasonId = "";
        itemReworkReason.setContent(reworkReasonId);

        rewordResponseId = "";
        itemReworkResponse.setContent(rewordResponseId);

        reWorkProcessId = "";
        itemReworkProcess.setContent(reWorkProcessId);
    }

    private List<NetParams> getSendParam() {
        List<NetParams> params = new ArrayList<>();
        //必填参数
        params.add(new NetParams("sBarCode", code));
        params.add(new NetParams("sSerialID", productId));
        params.add(new NetParams("sBscDataPersonID", workerId));
        params.add(new NetParams("sUserID", "master"));
        params.add(new NetParams("sDeptID", workerLinkId));
        //合格参数
        params.add(new NetParams("iQty ", itemStandard.getText()));
        //报废参数
        params.add(new NetParams("bfQty", itemScrap.getText()));
        params.add(new NetParams("bfzrgx", scarpResponseId));
        params.add(new NetParams("bfyy", scarpReasonId));
        //返修参数
        params.add(new NetParams("fxQty", itemRework.getText()));
        params.add(new NetParams("fxzrgx", reworkReasonId));
        params.add(new NetParams("fxyy", reworkReasonId));
        params.add(new NetParams("fxgx", reWorkProcessId));

        params.add(new NetParams("otype", "SaveAndSubmit"));

        return params;
    }

    /*弹出提示*/
    private void toastMessage(String message) {
        if (StringUtil.isNotEmpty(message))
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (isLoading) {
                        LoadingDialog.cancelDialogForLoading();
                        isLoading = false;
                    }
                    Toasts.showShort(ProduceActivity.this, message);
                }
            });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isLoading) {
            LoadingDialog.cancelDialogForLoading();
            isLoading = false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String name = data.getStringExtra("name");
            String id = data.getStringExtra("id");
            String link_id = data.getStringExtra("link_id");

            switch (resultCode) {
                case LookUpUtil.PRODUCE_CODE:
                    itemProduct.setContent(name);
                    productId = id;
                    break;
                case LookUpUtil.CHECK_CODE:
                    itemCheck.setContent(name);
                    checkId = id;
                    break;
                case LookUpUtil.WORKER_CODE:
                    itemWorker.setContent(name);
                    workerId = id;
                    workerLinkId = link_id;
                    break;
                case LookUpUtil.SCRAP_CODE:
                    itemScrapReason.setContent(name);
                    scarpReasonId = id;
                    break;
                case LookUpUtil.SCRAP_PROCESS_CODE:
                    itemScrapResponse.setContent(name);
                    scarpResponseId = id;
                    break;
                case LookUpUtil.REWORKREASON_CODE:
                    itemReworkReason.setContent(name);
                    reworkReasonId = id;
                    break;
                case LookUpUtil.REWORK_PROCESS_CODE:
                    itemReworkProcess.setContent(name);
                    reWorkProcessId = id;
                    break;
                case LookUpUtil.REWORK_RESPONSE_CODE:
                    itemReworkResponse.setContent(name);
                    rewordResponseId = id;
                    break;
                default:
                    break;
            }
        }
    }
}
