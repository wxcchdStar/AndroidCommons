package wxc.android.commons.lib.utils.special;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

// 检测设备是否支持WebP
public class WebPUtils {
    public static boolean sIsWebpSupported = false;

    //states
    private static final int NOT_INITIALIZED = -1;
    private static final int SUPPORTED = 1;
    private static final int NOT_SUPPORTED = 0;

    //why not boolean? we need more states for result caching
    private static int sIsWebPSupportedCache = NOT_INITIALIZED;

    public static boolean isWebpSupported() {
        if (!isWebpShouldSupported()) return false;

        // did we already try to check?
        if (sIsWebPSupportedCache == NOT_INITIALIZED) {
            //no - trying to decode
            //webp 1x1 transparent pixel with lossless
            final byte[] webp1x1 = new byte[] {
                0x52, 0x49, 0x46, 0x46, 0x1A, 0x00, 0x00, 0x00,
                0x57, 0x45, 0x42, 0x50, 0x56, 0x50, 0x38, 0x4C,
                0x0D, 0x00, 0x00, 0x00, 0x2F, 0x00, 0x00, 0x00,
                0x10, 0x07, 0x10, 0x11, 0x11, (byte) 0x88, (byte) 0x88, (byte) 0xFE,
                0x07, 0x00
            };
            try {
                final Bitmap bitmap = BitmapFactory.decodeByteArray(webp1x1, 0, webp1x1.length);
                if (bitmap != null) {
                    //webp supported
                    sIsWebPSupportedCache = SUPPORTED;
                    //don't forget to recycle!
                    bitmap.recycle();
                } else {
                    //bitmap is null = not supported
                    sIsWebPSupportedCache = NOT_SUPPORTED;
                }
            } catch (Exception ex) {
                //we got some exception = not supported
                sIsWebPSupportedCache = NOT_SUPPORTED;
                ex.printStackTrace();
            }
        }
        return sIsWebPSupportedCache == SUPPORTED;
    }

    private static boolean isWebpShouldSupported() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }
}