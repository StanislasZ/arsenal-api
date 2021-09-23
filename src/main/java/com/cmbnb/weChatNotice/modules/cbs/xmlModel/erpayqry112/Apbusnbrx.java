package com.cmbnb.weChatNotice.modules.cbs.xmlModel.erpayqry112;


import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * 1.12 接口 请求之一
 */
@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Apbusnbrx {

    @XmlElement(name = "BUSNBR")
    private String busNbr;   //业务流水号 = 2.8返回的PAYNBR支付流水号

    @XmlElement(name = "OPTSTU")
    private String optStu; //业务状态  设置为 DP (已支付)
}
