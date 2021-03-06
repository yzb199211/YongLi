package com.yyy.yongli.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.yyy.yongli.R;
import com.yyy.yongli.util.StringUtil;


public class EditDialog extends Dialog implements View.OnClickListener, View.OnKeyListener {
    Context context;
    private String title;
    private int max = 0;
    private String positiveName;
    private String negativeName;
    private TextView tvTitle;
    private EditText etContent;
    private TextView tvCancle;
    private TextView tvSubmit;
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
        void onClick(boolean confirm, @NonNull String data);
    }

    public void setOnCloseListener(OnCloseListener onCloseListener) {
        this.onCloseListener = onCloseListener;
    }

    /**
     * 构造方法
     *
     * @param context
     */
    public EditDialog(@NonNull Context context) {
        super(context, R.style.JudgeDialog);
        this.context = context;
    }

    /**
     * 构造方法
     *
     * @param context
     * @param themeResId dialog样式
     */
    public EditDialog(Context context, int themeResId, int max) {
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
    public EditDialog(Context context, int themeResId, int max, OnCloseListener listener) {
        super(context, themeResId);
        this.context = context;
        this.max = max;
        this.onCloseListener = listener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_edit);
        setCanceledOnTouchOutside(false);
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        tvTitle = findViewById(R.id.tv_title);
        etContent = findViewById(R.id.et_content);
        tvCancle = findViewById(R.id.tv_cancel);
        tvSubmit = findViewById(R.id.tv_submit);

        etContent.setText(String.valueOf(max) + "");
        if (StringUtil.isNotEmpty(title))
            tvTitle.setText(title);
        if (StringUtil.isNotEmpty(positiveName))
            tvSubmit.setText(positiveName);
        if (StringUtil.isNotEmpty(negativeName))
            tvCancle.setText(negativeName);
        tvCancle.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
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
                    onCloseListener.onClick(true, etContent.getText().toString());
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
    public EditDialog title(String title) {
        this.title = title;
        return this;
    }

    /**
     * 设置提示内容
     *
     * @return
     */
    public EditDialog max(int max) {
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

    /**
     * 设置确定按钮内容
     *
     * @param positiveName
     * @return
     */
    public EditDialog setPositiveName(String positiveName) {
        this.positiveName = positiveName;
        return this;
    }

    /**
     * 设置取消按钮内容
     *
     * @param negativeName
     * @return
     */
    public EditDialog setNegativeName(String negativeName) {
        this.negativeName = negativeName;
        return this;
    }

}
