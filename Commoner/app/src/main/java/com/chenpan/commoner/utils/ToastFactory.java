package com.chenpan.commoner.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast工具类
 *
 * @author adison
 */
public class ToastFactory {
    private static Context context = null;
    private static Toast toast = null;

    public static Toast getToast(Context context, String text) {
        if (ToastFactory.context == context) {
            // toast.cancel();
            toast.setText(text);
            toast.setDuration(Toast.LENGTH_SHORT);

        } else {

            ToastFactory.context = context;
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        }
        return toast;
    }

    public static void cancelToast() {
        if (toast != null) {
            toast.cancel();
        }
    }

    private static Context sContext;

    public static void init(Context context) {
        sContext = context.getApplicationContext();

    }

    public static void show(int resId) {
        if (ToastFactory.toast == null) {
            toast = Toast.makeText(sContext, resId, Toast.LENGTH_SHORT);

        } else {
            toast.setText(resId);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    public static void show(String text) {
        if (ToastFactory.toast == null) {
            toast = Toast.makeText(sContext, text, Toast.LENGTH_SHORT);

        } else {
            toast.setText(text);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }

}
