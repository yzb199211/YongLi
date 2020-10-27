package com.yyy.yongli.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.wang.avi.AVLoadingIndicatorView;
import com.yyy.yongli.R;


import java.lang.ref.WeakReference;


public class LoadingDialog {
    private static AVLoadingIndicatorView avi;
    /**
     * 加载数据对话框
     */
    public static Dialog mLoadingDialog;
    public static boolean isShow = false;

    /**
     * 显示加载对话框
     *
     * @param context    上下文
     * @param msg        对话框显示内容
     * @param cancelable 对话框是否可以取消
     */
    public static Dialog showDialogForLoading(Context context, String msg, boolean cancelable) {
        WeakReference<Context> wr = new WeakReference<>(context);
        if (isShow == false) {
            isShow = true;
            View view = LayoutInflater.from(wr.get()).inflate(R.layout.dialog_loading, null);
            avi = view.findViewById(R.id.avi);
            mLoadingDialog = new Dialog(wr.get(), R.style.LoadingDialogStyle);
            mLoadingDialog.setCancelable(cancelable);
            mLoadingDialog.setCanceledOnTouchOutside(false);
            mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            mLoadingDialog.show();
            avi.show();
        }
        return mLoadingDialog;
    }

    public static Dialog showDialogForLoading(Context context) {
        if (isShow == false) {
            isShow = true;
            WeakReference<Context> wr = new WeakReference<>(context);
            View view = LayoutInflater.from(wr.get()).inflate(R.layout.dialog_loading, null);
            avi = view.findViewById(R.id.avi);
            mLoadingDialog = new Dialog(wr.get(), R.style.LoadingDialogStyle);
            mLoadingDialog.setCancelable(false);
            mLoadingDialog.setCanceledOnTouchOutside(false);
            mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            mLoadingDialog.show();
            avi.show();
        }
        return mLoadingDialog;
    }

    /**
     * 关闭加载对话框
     */
    public static void cancelDialogForLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.cancel();
            avi.hide();
            isShow = false;
        }
    }

}
