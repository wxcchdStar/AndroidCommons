package wxc.android.commons.utils;

import android.util.Log;

import wxc.android.commons.BuildConfig;

public class L {

    public static void d(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message);
        }
    }

}
