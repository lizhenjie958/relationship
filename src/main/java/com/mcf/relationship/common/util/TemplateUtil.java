package com.mcf.relationship.common.util;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import org.springframework.util.PropertyPlaceholderHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author ZhuPo
 * @date 2026/2/7 20:29
 */
public final class TemplateUtil {
    private static final PropertyPlaceholderHelper HELPER =
            new PropertyPlaceholderHelper("${", "}");
    public static <T> String replace(String text, T t) {
        Map<String,String> map =  JSONObject.parseObject(JSONObject.toJSONString(t), new TypeReference<HashMap<String,String>>(){});
        System.err.println(JSONObject.toJSONString( map));
        return HELPER.replacePlaceholders(text, map::get);
    }
}
