package com.shi.androidstudy.tool;


import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;


/**
 * SnackBarUtil
 * @author SHI
 * 2016-12-28 16:01:40
 */
public class SnackBarUtil {

    private SnackBarUtil() {
        throw new AssertionError();
    }


    public static void show(View view, String msg) {
        show(view, msg, Snackbar.LENGTH_SHORT);
    }

    public static void showLong(View view, String msg) {
        show(view, msg, Snackbar.LENGTH_LONG);
    }

        public static void show(View view, CharSequence text, int duration) {
        Snackbar snackbar = Snackbar.make(view, text, duration);
        ((TextView)snackbar.getView().findViewById(R.id.snackbar_text)).setTextColor(Color.argb(0xff, 0xff, 0xff, 0xff));
        snackbar.show();
    }

}
