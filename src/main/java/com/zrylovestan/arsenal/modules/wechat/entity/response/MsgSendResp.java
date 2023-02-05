package com.zrylovestan.arsenal.modules.wechat.entity.response;

import lombok.Data;

/**
 * 企业微信 消息发送 - 返回类
 */
@Data
public class MsgSendResp {

    private Integer errcode;

    private String errmsg;

    private String invaliduser;

    private String invalidparty;

    private String invalidtag;

    private String msgid;

    private String response_code;
}
