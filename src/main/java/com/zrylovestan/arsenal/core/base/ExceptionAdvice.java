package com.zrylovestan.arsenal.core.base;


import com.alibaba.fastjson.JSONObject;
import com.zrylovestan.arsenal.core.util.CommonUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 统一处理所有的异常信息.
 */
@RestControllerAdvice
public class ExceptionAdvice {

    private final static Logger LOGGER =  LoggerFactory.getLogger(ExceptionAdvice.class);





    @ExceptionHandler(CommonJsonException.class)
    public JSONObject handleCommonJsonException(CommonJsonException cje){
        cje.printStackTrace();
        LOGGER.error(cje.getMessage());
        return cje.getResultJson();
    }

    @ExceptionHandler(Exception.class)
    public JSONObject errorJson(Exception ex){
        ex.printStackTrace();
        LOGGER.error(ex.getMessage());
        return CommonUtils.errorJson(ex);
    }



}
