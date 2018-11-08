import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

public class RefectUtil {

    private static Map<Class<?>, Map<String, Fo>> cache = new HashMap<>();

    private static Map<String, Fo> getTypeCache(Class<?> t) {
        if (cache.containsKey(t)) {
            return cache.get(t);
        } else {
            Map<String, Fo> result = getConstructParamsAnnotation(t);
            cache.put(t, result);
            return result;
        }
    }

    private static Map<String, Fo> getConstructParamsAnnotation(Class<?> t) {

        Map<String, Fo> fields = new HashMap<>();
        for (Parameter parameter : t.getConstructors()[0].getParameters()) {
            Fo ano = parameter.getAnnotation(Fo.class);
            if (ano != null) {
                fields.put(ano.f(), ano);
            }
        }
        return fields;

    }

    public static void initCache(Class<?>... types) {
        for (Class<?> type : types) {
            getTypeCache(type);
        }
    }


    public static <T> T getObj(Class<T> type, Map<String, String[]> input) {
        JSONObject jsonObject = new JSONObject();
        Map<String, Fo> fields = getTypeCache(type);
        for (Map.Entry<String, Fo> entry :
                fields.entrySet()) {
            Fo dec = entry.getValue();
            jsonObject.put(entry.getKey(), input.get(dec.s())[dec.o()]);
        }
        return jsonObject.toJavaObject(type);
    }
}
