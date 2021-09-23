package com.cmbnb.weChatNotice.modules.cbs.xmlModel.dcprtrdp52;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * 5.2 返回报文 接口之一
 */
@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Ntprtmsgy {

    @XmlElement(name = "ISTNBR")
    private String istNbr;   //打印实例号

    /**
     * 用这个路径，拿到pdf文件， 调微信接口发出去
     */
    @XmlElement(name = "IMPATH")
    private String imPath;   //图片路径

    @XmlElement(name = "ERRCOD")
    private String errCod;  //错误码

    @XmlElement(name = "ERRMSG")
    private String errMsg;  //错误信息

    @XmlElement(name = "SQRNB1")
    private String sqrNb1;  //流水号


}
