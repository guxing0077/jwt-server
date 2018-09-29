package com.lee.utils;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by lee on 17/9/4.
 */
public class JsonUtil {

    public static <T> T jsonToObject(String objStr, Class<T> c) {
        if (objStr != null && !"".equals(objStr)) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(objStr, c);
            } catch (Exception var3) {
                var3.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    public static <T> List<T> jsonToList(String objStr, Class<T> c) {
        if (objStr != null && !"".equals(objStr)) {
            try {
                List<T> list;
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
                JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, c);
                list = mapper.readValue(objStr, javaType);
                return list;
            } catch (Exception var3) {
                var3.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    public static String objectToJson(Object o) {
        return objectToJson(o, false);
    }

    public static String objectToJson(Object o, boolean escapeNonAscii) {
        StringWriter str = new StringWriter();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            if (escapeNonAscii) {
                objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
            }
            objectMapper.writeValue(str, o);
            return str.toString();
        } catch (Exception var3) {
            return null;
        }
    }
}
