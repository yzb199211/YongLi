package com.yyy.yongli.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.yyy.yongli.R;
import com.yyy.yongli.model.StorageScanBean;
import com.yyy.yongli.util.StringUtil;


public class EditDialog2 extends Dialog implements View.OnClickListener, View.OnKeyListener {
    Context context;
    private String title;
    private int max = 0;
    private String positiveName;
    private String negativeName;
    private TextView tvTitle;
    private EditText etContent;
    private TextView tvCancle;
    private TextView tvSubmit;
    private EditText etTip;
    private TextView tvTotal;
    private TextView tvQty;
    private StorageScanBean barcode;
    OnCloseListener onCloseListener;

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event) {
        if ((keyCode == EditorInfo.IME_ACTION_SEND
                || keyCode == EditorInfo.IME_ACTION_DONE || keyCode == KeyEvent.KEYCODE_ENTER) && event.getAction() == KeyEvent.ACTION_DOWN) {
            closeKeybord();
            view.requestFocus();
            return true;
        }
        return false;
    }

    public void closeKeybord() {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    public interface OnCloseListener {
        void onClick(boolean confirm, @NonNull StorageScanBean data);
    }

    public void setOnCloseListener(OnCloseListener onCloseListener) {
        this.onCloseListener = onCloseListener;
    }

    /**
     * 构造方法
     *
     * @param context
     * @param storageScanBean
     */
    public EditDialog2(@NonNull Context context, StorageScanBean storageScanBean) {
        super(context, R.style.JudgeDialog);
        barcode = storageScanBean;
        this.context = context;
    }

    /**
     * 构造方法
     *
     * @param context
     * @param themeResId dialog样式
     */
    public EditDialog2(Context context, int themeResId, int max) {
        super(context, themeResId);
        this.context = context;
        this.max = max;
    }

    /**
     * 构造方法
     *
     * @param context
     * @param themeResId dialog样式
     * @param max        设置内容
     * @param listener   设置监听
     */
    public EditDialog2(Context context, int themeResId, int max, OnCloseListener listener) {
        super(context, themeResId);
        this.context = context;
        this.max = max;
        this.onCloseListener = listener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_edit2);
        setCanceledOnTouchOutside(false);
        initView();
        setData(barcode);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        tvTitle = findViewById(R.id.tv_title);
        etTip = findViewById(R.id.et_tip);
        tvTotal = findViewById(R.id.tv_total);
        tvQty = findViewById(R.id.tv_qty);
        etContent = findViewById(R.id.et_content);
        tvCancle = findViewById(R.id.tv_cancel);
        tvSubmit = findViewById(R.id.tv_submit);
        etTip.setOnKeyListener(this);
        etContent.setOnKeyListener(this);
//        tvQty.setText(max + "");
        if (StringUtil.isNotEmpty(title))
            tvTitle.setText(title);
        if (StringUtil.isNotEmpty(positiveName))
            tvSubmit.setText(positiveName);
        if (StringUtil.isNotEmpty(negativeName))
            tvCancle.setText(negativeName);
        tvCancle.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
        initEdit();
    }

    private void initEdit() {
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                barcode.setiBox(TextUtils.isEmpty(editable.toString()) ? 0 : Integer.parseInt(editable.toString()));
                barcode.setiQty(barcode.getiBoxQty() * barcode.getiBox() + barcode.getiTip());
                setCount();
            }
        });
        etTip.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                barcode.setiTip(TextUtils.isEmpty(editable.toString()) ? 0 : Integer.parseInt(editable.toString()));
                barcode.setiQty(barcode.getiBoxQty() * barcode.getiBox() + barcode.getiTip());
                setCount();
            }
        });
    }

    @Override
    public void onClick(View v) {
        this.dismiss();
        switch (v.getId()) {
            case R.id.tv_cancel:
//                hintKeyBoard(context);
                if (onCloseListener != null) {
                    onCloseListener.onClick(false, null);
                }
                break;
            case R.id.tv_submit:
//                hintKeyBoard(context);
                if (onCloseListener != null) {
                    onCloseListener.onClick(true, barcode);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void dismiss() {
        View view = getCurrentFocus();
        if (view instanceof TextView) {
            InputMethodManager mInputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }

        super.dismiss();
    }

    /**
     * 设置标题
     *
     * @param title
     * @return
     */
    public EditDialog2 title(String title) {
        this.title = title;
        return this;
    }

    /**
     * 设置提示内容
     *
     * @return
     */
    public EditDialog2 max(int max) {
        this.max = max;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
        tvTitle.setText(title);
    }

    public void setMax(int max) {
        this.max = max;
        etContent.setText(max + "");
    }

    public void setData(StorageScanBean data) {

        barcode = data;
        if (tvTitle != null) tvTitle.setText(data.getsInBarCode());
        if (etContent != null)
            etContent.setText(barcode.getiBox() + "");
        if (etTip != null)
            etTip.setText(barcode.getiTip() == 0 ? "" : barcode.getiTip() + "");
        setCount();
    }

    private void setCount() {
        if (tvQty != null)
            tvQty.setText("每箱：" + barcode.getiBoxQty() + "");
        if (tvTotal != null)
            tvTotal.setText("总数：" + barcode.getiQty() + "");
    }


    /**
     * 设置确定按钮内容
     *
     * @param positiveName
     * @return
     */
    public EditDialog2 setPositiveName(String positiveName) {
        this.positiveName = positiveName;
        return this;
    }

    /**
     * 设置取消按钮内容
     *
     * @param negativeName
     * @return
     */
    public EditDialog2 setNegativeName(String negativeName) {
        this.negativeName = negativeName;
        return this;
    }

}
