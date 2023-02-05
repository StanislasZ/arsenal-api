package com.zrylovestan.arsenal.core.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @Description:
 * @Author: 周贝贝
 * @CreateDate: 2020/5/29 10:58
 * @Version: 1.0
 */
@Slf4j
public class JsonUtils {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final ObjectMapper MAPPER2 = new ObjectMapper();
    private static final ObjectMapper MAPPER3 = new ObjectMapper();

    static {
        MAPPER2.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        MAPPER3.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 将对象转换成json字符串
     *
     * @param o 转换对象
     * @return
     */
    public static String obj2Json(Object o) {
        return obj2Json(o, false);
    }

    /**
     * 对象转序列化是否将null转换
     *
     * @param o       序列化对象
     * @param nonNull 是否空值进行序列化
     * @return
     */
    public static String obj2Json(Object o, boolean nonNull) {
        if (nonNull) {
            return obj2Json(o, MAPPER2);
        }
        return obj2Json(o, MAPPER);
    }

    /**
     * 根据序列化规则转换
     *
     * @param o      转换对象
     * @param mapper 序列化规则
     * @return
     */
    private static String obj2Json(Object o, ObjectMapper mapper) {
        String jsonStr = null;
        try {
            if (o instanceof Serializable) {
                jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
            } else {
                log.info("不对 {} 进行json转换", o);
            }
        } catch (JsonProcessingException e) {
            log.error("对{}转换json失败", o);
            log.error(e.getMessage(), e);
        }
        return jsonStr;
    }

    /**
     * json字符串转对象
     *
     * @param str json字符串
     * @param t   对象类型
     * @param <T>
     * @return
     */
    public static <T> T jsonStr2Obj(String str, Class<T> t) {
        try {
            MAPPER.readValue(str, t);
            return MAPPER.readValue(str, t);
        } catch (Exception e) {
            log.error("{}\n转换成对象失败", str);
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * json字符串转对象
     *
     * @param str            JSON字符串
     * @param t              转换对象
     * @param ignoreProperty 是否忽略不存在的属性
     * @param <T>            泛型
     * @return
     */
    public static <T> T jsonStr2Obj(String str, Class<T> t, boolean ignoreProperty) {
        try {
            ObjectMapper objectMapper = ignoreProperty ? MAPPER3 : MAPPER;
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.readValue(str, t);
            return objectMapper.readValue(str, t);
        } catch (Exception e) {
            log.error("{}\n转换成对象失败", str);
            log.error(e.getMessage(), e);
        }
        return null;
    }


    /**
     * 根据引用类型转换
     *
     * @param str json字符串
     * @param t   引用类型
     * @param <T> 返回对象
     * @return
     */
    public static <T> T jsonStr2Obj(String str, TypeReference<T> t) {
        try {
            MAPPER.readValue(str, t);
            return MAPPER.readValue(str, t);
        } catch (Exception e) {
            log.error("{}\n转换成对象失败", str);
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * json字符串转对象
     *
     * @param str            json字符串
     * @param t              返回对象
     * @param ignoreProperty 是否忽略属性
     * @param <T>            泛型
     * @return
     */
    public static <T> T jsonStr2Obj(String str, TypeReference<T> t, boolean ignoreProperty) {
        try {
            ObjectMapper objectMapper = ignoreProperty ? MAPPER3 : MAPPER;
            objectMapper.readValue(str, t);
            return objectMapper.readValue(str, t);
        } catch (Exception e) {
            log.error("{}\n转换成对象失败", str);
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
