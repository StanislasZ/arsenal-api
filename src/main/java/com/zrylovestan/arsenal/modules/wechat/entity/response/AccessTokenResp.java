package com.zrylovestan.arsenal.modules.wechat.entity.response;

import lombok.Data;

/**
 * 调用 请求accessToken接口后 ，返回类
 */
@Data
public class AccessTokenResp {

    private Integer errcode;

    private String errmsg;


    private String access_token;



    private Integer expires_in;
}
