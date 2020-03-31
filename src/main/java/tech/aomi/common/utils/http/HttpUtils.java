package tech.aomi.common.utils.http;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import tech.aomi.common.utils.json.Json;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 田尘殇Sean(sean.snow@live.com) createAt 2016/11/29
 */
public class HttpUtils {


    /**
     * 对象转化为url形式的字符串
     * a=1&amp;b=2
     *
     * @param params key，value形式的对象
     * @return url 字符串
     */
    public static String toUrlArgs(Object params) {
        if (null == params) {
            return "";
        }

        JsonNode json = Json.toJson(params);
        Map<String, String> map = Json.fromJson(json.toString(), new TypeReference<LinkedHashMap<String, String>>() {
        });

        StringBuilder args = new StringBuilder();
        map.forEach((key, value) -> args.append(key).append("=").append(value).append("&"));
        args.deleteCharAt(args.length() - 1);

        return args.toString();
    }

}
