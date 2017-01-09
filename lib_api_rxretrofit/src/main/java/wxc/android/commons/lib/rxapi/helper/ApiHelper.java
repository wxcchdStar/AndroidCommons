package wxc.android.commons.lib.rxapi.helper;

import android.content.Context;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ApiHelper {

    public static <T> Map<String, Object> convertToMap(T bean) {
        Map<String, Object> returnMap = new HashMap<>();
        Class type = bean.getClass();
        Field[] fields = type.getDeclaredFields();
        if (fields != null) {
            for (Field field : fields) {
                String key = field.getName();
                Object value = null;
                try {
                    value = field.get(bean);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (value != null) {
                    returnMap.put(key, value);
                }
            }
        }
        return returnMap;
    }

    // 不确定文件类型时调用
    public static MultipartBody.Part convertToPartBody(String key, File file) {
        return convertToPartBody(key, "application/otcet-stream", file);
    }

    public static MultipartBody.Part convertToPartBody(String key, String mediaType, File file) {
        RequestBody requestFile = RequestBody.create(MediaType.parse(mediaType), file);
        return MultipartBody.Part.createFormData(key, file.getName(), requestFile);
    }

    public static void toastError(Context context, String message, Throwable e) {
        if (e != null && e.getCause() instanceof ApiResultException) {
            if (message == null) {
                String str = ((ApiResultException) e.getCause()).mMessage;
//                ToastUtils.showShort(context, str);
            } else {
//                ToastUtils.showShort(context, message);
            }
        } else {
//            ToastUtils.showShort(context, context.getString(R.string.common_connection_error));
        }
    }
}
