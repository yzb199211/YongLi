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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        preferencesHelper = new SharedPreferencesHelper(this, getString(R.string.preferenceCache));
        initView();
    }

    private void initView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) ivLoginHeader.getLayoutParams();
        params.height = PxUtil.getHeight(this);
        ivLoginHeader.setLayoutParams(params);
        String name = (String) preferencesHelper.getSharedPreference("userid", "");
        etUser.setText(name);
        etUser.setSelection(name.length());
    }


    @OnClick({R.id.btn_login, R.id.btn_sweep})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_login:
                try {
                    isNone();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_sweep:
//                intent.setClass(LoginActivity.this, MainActivity.class);
////                intent.setClass(LoginActivity.this, OutputListActivity.class);
//                startActivity(intent);
                permission(intent);
                break;
        }
    }


    /**
     * 判断url,用户名和密码是否为空
     */
    private void isNone() throws Exception {
        String head = (String) preferencesHelper.getSharedPreference("url", "");
        if (TextUtils.isEmpty(head)) {
            Toasts.showShort(LoginActivity.this, "请扫描二维码");
            return;
        }

        if (StringUtil.isNotEmpty(head)) {
            url = head + "/" + NetConfig.Login_Method;
        }
        Log.e(TAG, url);

        userid = etUser.getText().toString();
        password = etPwd.getText().toString();

        if (TextUtils.isEmpty(userid)) {
            Toasts.showShort(LoginActivity.this, "请输入用户名");
            return;
        }
//        if (TextUtils.isEmpty(password)) {
//            Toasts.showShort(LoginActivity.this, "请输入密码");
//            return;
//        }
        getContact();
//        NetRxUtil.doRequestByRetrofit(NetRequestBody.getMultipartBody(getParams()));
    }

    /**
     * 获取数据
     */
    private void getContact() {
        LoadingDialog.showDialogForLoading(this);
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
                            Toasts.showShort(LoginActivity.this, "无法连接服务器");
                            LoadingDialog.cancelDialogForLoading();
                        }
                    });
                }
            }

            @Override
            public void onFail(IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadingDialog.cancelDialogForLoading();
                        Toasts.showLong(LoginActivity.this, e.getMessage());
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
        list.add(new NetParams("sCompanyCode", (String) preferencesHelper.getSharedPreference("db", "")));
        return list;
    }

    /**
     * 返回数据处理
     *
     * @param response
     */
    private void initData(String response) throws Exception {

        Gson gson = new Gson();
        Log.d(TAG, response);
        LoginBean model = gson.fromJson(response, LoginBean.class);
        if (model.isSuccess()) {
            preferencesHelper.put("userid", userid);
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this, MainActivity.class);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LoadingDialog.cancelDialogForLoading();
                    startActivity(intent);
                    finish();
                }
            });


        } else
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, model.getMessage());
                    LoadingDialog.cancelDialogForLoading();
                    Toasts.showLong(LoginActivity.this, model.getMessage());
                }
            });

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
                // config.setPlayBeep(false);//是否播放扫描声音 默认为true
                // config.setShake(false);//是否震动  默认为true
                config.setDecodeBarCode(false);//是否扫描条形码 默认为true
                // config.setReactColor(R.color.colorAccent);//设置扫描框四个角的颜色 默认为白色
                // config.setFrameLineColor(R.color.colorAccent);//设置扫描框边框颜色 默认无色
                // config.setScanLineColor(R.color.colorAccent);//设置扫描线的颜色 默认白色
                config.setFullScreenScan(false);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
                intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
            }

            @Override
            public void onDenied(List<String> deniedPermission) {
//                Toast.makeText(LoginActivity.this, "授权失败", Toast.LENGTH_LONG).show();
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
//                Toast.makeText(this, "扫描结果为：" + content, Toast.LENGTH_LONG);
//                btnSweep.setText("扫描结果为：" + content);
                try {
                    JSONObject jsonObject = new JSONObject(content);
                    String url = jsonObject.getString("ServerAddr");
                    String db = jsonObject.getString("sCompanyCode");
                    preferencesHelper.put("url", url + "/MobileServer");
                    preferencesHelper.put("db", db);
                    Log.e(TAG, (String) preferencesHelper.getSharedPreference("url", "") + "," + db);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }

}
