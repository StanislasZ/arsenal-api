package com.cmbnb.weChatNotice.core.base;


import com.alibaba.fastjson.JSONObject;
import com.cmbnb.weChatNotice.core.util.CommonUtils;
import com.cmbnb.weChatNotice.core.util.constants.ErrorEnum;
import com.cmbnt.owk.login.exception.InvalidTokenException;
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



    @ExceptionHandler(InvalidTokenException.class)
    public JSONObject handleInvalidTokenException(InvalidTokenException ite) {
        ite.printStackTrace();
        LOGGER.error(ite.getMessage());
        return CommonUtils.errorJson(ErrorEnum.E_20011);
    }



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
