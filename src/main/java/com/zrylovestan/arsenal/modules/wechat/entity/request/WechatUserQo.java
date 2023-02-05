package com.zrylovestan.arsenal.modules.wechat.entity.request;


import com.zrylovestan.arsenal.core.entity.Page;
import lombok.Data;

@Data
public class WechatUserQo extends Page {


    private String name;

    private String wechatId;


}
