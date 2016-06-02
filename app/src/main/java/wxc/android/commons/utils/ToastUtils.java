package wxc.android.commons.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import wxc.android.commons.R;

public class ToastUtils {
    private static Toast sToast;
    private static TextView sMessageTv;

    private static void initToast(Context context) {
        if (sToast == null) {
            sToast = new Toast(context);

            @SuppressLint("InflateParams")
            View view = LayoutInflater.from(context).inflate(R.layout.toast, null);
            sMessageTv = V.f(view, android.R.id.message);

            sToast.setView(view);
            sToast.setGravity(Gravity.CENTER, 0, 0);
        }
    }

    public static void showShort(Context context, CharSequence message) {
        show(context, message, Toast.LENGTH_SHORT);
    }

    public static void showShort(Context context, int messageResId) {
        show(context, context.getString(messageResId), Toast.LENGTH_SHORT);
    }

    public static void showLong(Context context, CharSequence message) {
        show(context, message, Toast.LENGTH_LONG);
    }

    public static void showLong(Context context, int messageResId) {
        show(context, context.getString(messageResId), Toast.LENGTH_LONG);
    }

    private static void show(Context context, CharSequence message, int duration) {
        if (sToast != null) {
            sToast.cancel();
            sToast = null;
        }
        initToast(context);
        sMessageTv.setText(message);
        sToast.setDuration(duration);
        sToast.show();
    }
}
