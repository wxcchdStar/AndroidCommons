package wxc.android.commons.utils;

import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;

public class ViewUtils {

    public static void setOnGlobalLayoutListener(final View view, final OnGlobalLayoutListener listener) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                listener.onGlobalLayout(view);
                removeGlobalOnLayoutListener(view, this);
            }
        });
    }

    public static void removeGlobalOnLayoutListener(View view, ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        } else {
            //noinspection deprecation
            view.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
        }
    }

    public interface OnGlobalLayoutListener {
        void onGlobalLayout(View view);
    }
}
