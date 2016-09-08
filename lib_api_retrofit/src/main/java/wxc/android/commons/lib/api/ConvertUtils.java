package wxc.android.commons.lib.api;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

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
}
