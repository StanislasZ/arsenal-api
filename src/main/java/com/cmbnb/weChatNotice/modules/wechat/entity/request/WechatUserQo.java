package com.cmbnb.weChatNotice.modules.wechat.entity.request;


import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.cmbnb.weChatNotice.core.entity.Page;
import com.cmbnb.weChatNotice.modules.wechat.entity.WechatUser;
import lombok.Data;

@Data
public class WechatUserQo extends Page {


    private String name;

    private String wechatId;


}
