package wxc.android.commons.lib.utils;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

public class DeviceUtils {

    public static String getDeviceId(Context ctx) {
        return ((TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }

    public static String getDeviceInfo() {
        return Build.MODEL + "," + Build.VERSION.RELEASE + "," + Build.VERSION.SDK_INT;
    }
}
