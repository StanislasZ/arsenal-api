package com.cmbnb.weChatNotice.modules.cbs.xmlModel.erothclt78;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


/**
 * 客商信息
 */
@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Cicltothy {

    private Integer id;

    @XmlElement(name = "OTHNBR")
    private String othNbr;   //客商账户id

    @XmlElement(name = "OTHNAM")
    private String othNam;   //客商账户名称

    @XmlElement(name = "BMINBR")
    private String bmiNbr;  //客商账户编号


    @XmlElement(name = "RM1INF")
    private String rm1Inf;   //备注1

    @XmlElement(name = "RM2INF")
    private String rm2Inf;   //备注2


}
