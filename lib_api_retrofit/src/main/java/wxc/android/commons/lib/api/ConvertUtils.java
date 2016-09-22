package wxc.android.commons.lib.api;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ConvertUtils {

    public static <T extends ApiParams> Map<String, Object> convertToMap(T bean) {
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
}
