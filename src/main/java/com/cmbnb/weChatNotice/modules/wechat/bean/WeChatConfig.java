package com.cmbnb.weChatNotice.modules.wechat.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 企业微信 配置参数
 */
@Component
@ConfigurationProperties(prefix = "wechat")
@Data
public class WeChatConfig {

    //企业id
    private String corpid;

    //应用id
    private Integer agentId;

    //要使用的应用的密钥
    private String corpsecret;


    //请求access_token的接口url
    private String accessTokenUrl;

    //请求消息发送接口的url
    private String msgSendUrl;

    //群聊中 发送消息 接口  url
    private String msgSendToGroupUrl;

    //素材上传接口 url
    private String mediaUploadUrl;

    //收款- 发送文件到 群聊，这个群聊的 chatid
    private String skGroupChatId;



}
