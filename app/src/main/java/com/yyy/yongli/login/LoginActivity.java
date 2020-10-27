package com.yyy.yongli.login;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.yyy.yongli.BaseActivity;
import com.yyy.yongli.R;
import com.yyy.yongli.dialog.LoadingDialog;
import com.yyy.yongli.interfaces.ResponseListener;
import com.yyy.yongli.main.MainActivity;
import com.yyy.yongli.output.OutputListActivity;
import com.yyy.yongli.permission.PermissionListener;
import com.yyy.yongli.util.NetConfig;
import com.yyy.yongli.util.NetParams;
import com.yyy.yongli.util.NetUtil;
import com.yyy.yongli.util.PxUtil;
import com.yyy.yongli.util.SharedPreferencesHelper;
import com.yyy.yongli.util.StringUtil;
import com.yyy.yongli.util.Toasts;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    private final String TAG = "LoginActivity";
    @BindView(R.id.iv_login_header)
    ImageView ivLoginHeader;
    @BindView(R.id.et_user)
    EditText etUser;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.btn_login)
    TextView btnLogin;
    @BindView(R.id.btn_sweep)
    TextView btnSweep;
    private static final int REQUEST_CODE_SCAN = 11;
    SharedPreferencesHelper preferencesHelper;
    String userid;
    String password;
    String url;

    boolean istext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        preferencesHelper = new SharedPreferencesHelper(this, getString(R.string.preferenceCache));
        istext = true;
        initView();
    }

    private void initView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setImageParam();
        setDefaultData();

    }

    private void setImageParam() {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) ivLoginHeader.getLayoutParams();
        params.height = PxUtil.getHeight(this);
        ivLoginHeader.setLayoutParams(params);
    }

    private void setDefaultData() {
        String name = (String) preferencesHelper.getSharedPreference("userid", "");
        etUser.setText(name);
        etUser.setSelection(name.length());
        etUser.setOnKeyListener(this);
        etPwd.setOnKeyListener(this);
    }


    @OnClick({R.id.btn_login, R.id.btn_sweep})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_login:
                try {
                    getText();

                    isNone();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_sweep:
                permission(intent);
                break;
        }
    }

    private void getText() {
        userid = etUser.getText().toString();
        password = etPwd.getText().toString();
    }


    /**
     * 判断url,用户名和密码是否为空
     */
    private void isNone() throws Exception {

        url = setUrl();
        if (TextUtils.isEmpty(getUrl())) {
            Toasts.showShort(LoginActivity.this, "请扫描二维码");
            return;
        }
        if (TextUtils.isEmpty(userid)) {
            Toasts.showShort(LoginActivity.this, "请输入用户名");
            return;
        }
        getContact();
    }


    private String getUrl() {
        if (istext == true)
            preferencesHelper.put("url", NetConfig.url);
        return (String) preferencesHelper.getSharedPreference("url", "");
    }

    private String setUrl() {

        return getUrl() + NetConfig.Login_Method;
    }

    /**
     * 获取数据
     */
    private void getContact() {
        LoadingDialog.showDialogForLoading(this);
//        Log.e("url", url);
        new NetUtil(getParams(), url, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                try {
                    initData(string);
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            FinishLoading("无法连接服务器");
                        }
                    });
                }
            }

            @Override
            public void onFail(IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        FinishLoading(e.getMessage());
                        Log.e(TAG, e.getMessage());
                    }
                });

            }
        });
    }

    /**
     * 设置参数
     *
     * @return
     */
    private List<NetParams> getParams() {
        List<NetParams> list = new ArrayList<>();
        list.add(new NetParams("userid", userid));
        list.add(new NetParams("password", password));

//        list.add(new NetParams("sCompanyCode", (String) preferencesHelper.getSharedPreference("db", "")));
        return list;
    }

    /**
     * 返回数据处理
     *
     * @param response
     */
    private void initData(String response) throws Exception {

        Gson gson = new Gson();
//        Log.d(TAG, response);
        LoginBean model = gson.fromJson(response, LoginBean.class);
        if (model.isSuccess()) {
            setPrefrence();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    goActivity();
                    FinishLoading(null);
                    finish();
                }
            });


        } else
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, model.getMessage());
                    FinishLoading(model.getMessage());
                }
            });

    }

    private void setPrefrence() {
        preferencesHelper.put("userid", userid);
    }

    private void goActivity() {
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void FinishLoading(@NonNull String msg) {
        LoadingDialog.cancelDialogForLoading();
        if (StringUtil.isNotEmpty(msg)) {
            Toasts.showLong(this, msg);
        }

    }

    /**
     * 扫描权限申请和扫描逻辑处理
     *
     * @param intent
     */
    private void permission(Intent intent) {
        requestRunPermisssion(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, new PermissionListener() {
            @Override
            public void onGranted() {
                intent.setClass(LoginActivity.this, CaptureActivity.class);
                /* ZxingConfig是配置类
                 * 可以设置是否显示底部布局，闪光灯，相册，
                 * 是否播放提示音  震动
                 * 设置扫描框颜色等
                 * 也可以不传这个参数
                 * */
                ZxingConfig config = new ZxingConfig();
                config.setDecodeBarCode(false);//是否扫描条形码 默认为true
                config.setFullScreenScan(false);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
                intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
            }

            @Override
            public void onDenied(List<String> deniedPermission) {
                Toasts.showShort(LoginActivity.this, "授权失败");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码返回值
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {

                String content = data.getStringExtra(Constant.CODED_CONTENT);
                Log.e("data", content);
                try {
                    JSONObject jsonObject = new JSONObject(content);
                    String url = jsonObject.getString("ServerAddr");
//                    String db = jsonObject.getString("sCompanyCode");
                    preferencesHelper.put("url", url + "/MobileServerNew/");
//                    preferencesHelper.put("db", db);
                    Log.e(TAG, (String) preferencesHelper.getSharedPreference("url", ""));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }

}
