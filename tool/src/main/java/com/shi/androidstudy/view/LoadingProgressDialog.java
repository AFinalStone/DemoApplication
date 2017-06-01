package com.shi.androidstudy.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;

import com.shi.androidstudy.tool.R;
import com.shi.androidstudy.view.loadingprogress.LoadingProgressView;


/***
 * @action 等待框
 * @author SHI
 * 2016-2-3 09:20:29
 */
public class LoadingProgressDialog extends Dialog {

	private static LoadingProgressDialog loadingProgressDialog = null;

	public LoadingProgressDialog(Context context, int theme) {
		super(context, theme);
	}


	private static LoadingProgressDialog createDialog(Activity mContext) {

		loadingProgressDialog = new LoadingProgressDialog(mContext, R.style.MyProgressDialogStyle);
		loadingProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		loadingProgressDialog.setContentView(R.layout.view_loading);
		LoadingProgressView loadingProgressView = (LoadingProgressView) loadingProgressDialog
												  .findViewById(R.id.loadingProgressView);
//		TypedArray array = mContext.getTheme().obtainStyledAttributes(new int[] {
//				android.R.attr.colorPrimary,
//				android.R.attr.colorPrimaryDark,
//				android.R.attr.colorAccent,
//		});
//		int colorPrimary = array.getColor( 0, 0);
//		loadingProgressView.setIndicatorColor(colorPrimary);
//		loadingProgressView.smoothToShow();
		TypedValue typedValue = new TypedValue();
		mContext.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
		loadingProgressView.setIndicatorColor(typedValue.data);
		loadingProgressView.smoothToShow();
		return loadingProgressDialog;
	}


	public static void start(Activity mContext){
		if( mContext.isFinishing()){
			return;
		}
		loadingProgressDialog = LoadingProgressDialog.createDialog(mContext);
		loadingProgressDialog.show();
	}

	/**如果当前存在进度对话框，则关闭对话框**/
	public static void stop(){
		if(loadingProgressDialog != null){
			((LoadingProgressView) loadingProgressDialog.findViewById(R.id.loadingProgressView)).smoothToHide();
			loadingProgressDialog.dismiss();
			loadingProgressDialog = null;
		}
	}

}
