package com.cmbnb.weChatNotice.modules.cbs.xmlModel.erpayqry112;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * 5.2 接口 返回之一
 */
@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Appayinfz {

    @XmlElement(name = "BUSNBR")
    private String busNbr;   //业务流水号


    @XmlElement(name = "ERPCM1")
    private String erpCm1;  //ERP备注1  存放企业微信要发给的人的姓名



}
