package com.yl.baiduren.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yl.baiduren.R;


/**
 * 显示toast信息
 *
 * @author 王锋 on 2017/4/21.
 */
public final class ToastUtil {
    private static TextView tvView;
    private static Toast toast = null;

    private ToastUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, CharSequence message) {
        if (toast == null) {
            toast = new Toast(context);
            tvView = (TextView) View.inflate(context,R.layout.loan_toast_layout, null);
            tvView.setText(message);

            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(tvView);
            toast.setGravity(Gravity.CENTER,0,100);
        } else {
            tvView.setText(message);
        }

        toast.show();
    }


    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, int message) {
        if (toast == null) {
            toast = new Toast(context);
            tvView = (TextView) View.inflate(context, R.layout.loan_toast_layout, null);
            tvView.setText(message);

            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(tvView);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            tvView.setText(message);
        }

        toast.show();
    }


}