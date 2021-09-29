package com.cmbnb.weChatNotice.modules.clientInfo.entity;


import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;


/**
 * 客户信息表
 */
@Data
public class ClientInfo {

    @ExcelIgnore
    private Integer id;

    @ExcelProperty("客户全称")
    private String name;   //全称

    @ExcelIgnore
    private String addDate;  //添加日期

    @ExcelIgnore
    private Boolean deleteFlag;   //删除标记，  已删除为true




}
