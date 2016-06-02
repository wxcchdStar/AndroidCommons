package wxc.android.commons.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import wxc.android.commons.BaseActivity;

public class AppUtils {

    /**
     * 判断Activity是否可用，常用于耗时操作结束后对UI进行更新时
     *
     * @param activity BaseActivity
     * @return true-可用, false-不可用
     */
    public static boolean isAvailable(BaseActivity activity) {
        return activity != null && !activity.isDestroyed() && !activity.isFinishing();
    }

    public static String getVersionName(Context context) {
        String versionName = "0.0";
        PackageManager pkgManager = context.getPackageManager();
        try {
            PackageInfo pkgInfo = pkgManager.getPackageInfo(context.getPackageName(), 0);
            versionName = pkgInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }
}
