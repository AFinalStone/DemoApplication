package com.shi.androidstudy.widget.loading;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shi.androidstudy.tool.R;


/**
 * description:弹窗浮动加载进度条
 * Created by ysl
 */
public class LoadingDialog {
    /** 加载数据对话框 */
    private static LoadingDialog instance;
    private static Dialog mLoadingDialog;

    public static synchronized LoadingDialog getInstance(){
        if(instance == null){
            instance = new LoadingDialog();
        }
        if(mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.cancel();
            mLoadingDialog = null;
        }
        return instance;
    }
    /**
     * 显示加载对话框
     * @param msg 对话框显示内容
     * @param cancelable 对话框是否可以取消
     */
    public  Dialog showDialogForLoading(Activity context, String msg, boolean cancelable) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_loading_dialog, null);
        TextView loadingText = (TextView)view.findViewById(R.id.tv_loadingMsg);
        loadingText.setText(msg);

        mLoadingDialog = new Dialog(context, R.style.CustomProgressDialog);
        mLoadingDialog.setCancelable(cancelable);
        mLoadingDialog.setCanceledOnTouchOutside(false);
        mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        mLoadingDialog.show();
        return  mLoadingDialog;
    }

    public Dialog showDialogForLoading(Activity context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_loading_dialog, null);
        TextView loadingText = (TextView)view.findViewById(R.id.tv_loadingMsg);
        loadingText.setText("加载中...");

        mLoadingDialog = new Dialog(context, R.style.CustomProgressDialog);
        mLoadingDialog.setCancelable(true);
        mLoadingDialog.setCanceledOnTouchOutside(false);
        mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        mLoadingDialog.show();
        return  mLoadingDialog;
    }

    /**
     * 关闭加载对话框
     */
    public void cancelDialogForLoading() {
        if(mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.cancel();
            mLoadingDialog = null;
            Log.e("LoadingDialog","取消加載框被執行");
        }else{
            Log.e("LoadingDialog","取消加載框沒有被執行");
        }
    }
}
