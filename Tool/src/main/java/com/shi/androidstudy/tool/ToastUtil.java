package com.shi.androidstudy.tool;


import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * ToastUtil
 * @author SHI
 */
public class ToastUtil {

    private static Toast toast;
    public static Context mContext;

    private ToastUtil() {
        throw new AssertionError();
    }

    public static void register(Context context) {
        mContext = context;
    }


    private static void check() {
        if (mContext == null) {
            throw new NullPointerException(
                    "Must initial call ToastUtils.register(Context context) in your " +
                            "<? " +
                            "extends Application class>");
        }
    }

    public static void show(int resId) {
        check();
        show(mContext.getResources().getText(resId), Toast.LENGTH_SHORT);
    }

    public static void show(int resId, int duration) {
        check();
        show(mContext.getResources().getText(resId), duration);
    }

    public static void show(CharSequence text) {
        check();
        show(text, Toast.LENGTH_SHORT);
    }

    public static void show(CharSequence text, int duration) {
        check();
        Toast.makeText(mContext, text, duration).show();
    }

    public static void show( int resId, Object... args) {
        check();
        show(String.format(mContext.getResources().getString(resId), args), Toast.LENGTH_SHORT);
    }

    public static void show(String format, Object... args) {
        check();
        show(String.format(format, args), Toast.LENGTH_SHORT);
    }

    public static void show(int resId, int duration, Object... args) {
        check();
        show(String.format(mContext.getResources().getString(resId), args), duration);
    }

    public static void show( String format, int duration, Object... args) {
        check();
        show(String.format(format, args), duration);
    }

    /**
     * 显示有image的toast
     *
     * @param tvStr
     * @param imageResource
     * @return
     */
    public static Toast showToastWithImg(final String tvStr, final int imageResource) {
        if (toast == null) {
            toast = new Toast(mContext);
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.toast_custom, null);
        TextView tv = (TextView) view.findViewById(R.id.toast_custom_tv);
        tv.setText(TextUtils.isEmpty(tvStr) ? "" : tvStr);
        ImageView iv = (ImageView) view.findViewById(R.id.toast_custom_iv);
        if (imageResource > 0) {
            iv.setVisibility(View.VISIBLE);
            iv.setImageResource(imageResource);
        } else {
            iv.setVisibility(View.GONE);
        }
        toast.setView(view);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        return toast;

    }

    /**
     * 显示有image的toast
     *
     * @param tvStr
     * @param isSuccess  是不是加载成功图
     * @return
     */
    public static Toast showStatusView(final String tvStr, boolean isSuccess) {
        if (toast == null) {
            toast = new Toast(mContext);
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.toast_success_view, null);
        TextView tv = (TextView) view.findViewById(R.id.id_tv_loading_dialog_text);
        tv.setText(TextUtils.isEmpty(tvStr) ? "" : tvStr);
        ImageView iv = (ImageView) view.findViewById(R.id.iv_toast_icon);

        if (isSuccess) {
            iv.setImageResource(R.mipmap.toast_show_success);
        } else {
            iv.setImageResource(R.mipmap.toast_show_error);
        }
        toast.setView(view);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
        return toast;

    }
}
