package com.cmbnb.weChatNotice.modules.cbs.xmlModel.dcprtrdp52;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * 5.2 接口 请求之一
 */
@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Dcprtrcpx {

    @XmlElement(name = "SQRNB1")
    private String sqrNb1;  //流水号

    @XmlElement(name = "ISTNBR")
    private String istNbr;  //打印示例号

    @XmlElement(name = "ACCNBR")
    private String accNbr;  //账号

    @XmlElement(name = "PRTFLG")
    private String prtFlg;  //打印标示  填2

}
