package com.cmbnb.weChatNotice.modules.wechat.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class WechatUser {

    @ExcelIgnore
    private Integer id;

    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("企业微信id")
    private String wechatId;

    @ExcelIgnore
    private Boolean deleteFlag;



}
