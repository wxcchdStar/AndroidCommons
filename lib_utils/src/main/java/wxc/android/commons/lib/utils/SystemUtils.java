package wxc.android.commons.lib.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.WindowManager;

import java.util.Locale;

public class SystemUtils {

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        Resources resources = context.getResources();
        try {
            int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = resources.getDimensionPixelSize(resourceId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result == 0) {
            result = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, resources.getDisplayMetrics());
        }
        return result;
    }

    public static void setScreenBrightness(Activity activity, float screenBrightness) {
        WindowManager.LayoutParams layout = activity.getWindow().getAttributes();
        layout.screenBrightness = screenBrightness;
        activity.getWindow().setAttributes(layout);
    }

    /**
     * 获取语言和国家地区的方法 格式:zh-CN
     *
     * @return 当前国家区域及语言
     */
    public static String getLanguage() {
        String ret = null;
        try {
            ret = String.format(Locale.ENGLISH, "%s-%s",
                    Locale.getDefault().getLanguage(),
                    Locale.getDefault().getCountry());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret == null ? "error" : ret;
    }

}
