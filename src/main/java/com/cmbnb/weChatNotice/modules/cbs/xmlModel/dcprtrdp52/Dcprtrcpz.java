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
public class Dcprtrcpz {

    @XmlElement(name = "IMPATH")
    private String imPath;

    @XmlElement(name = "ISTNBR")
    private String istNbr;

}
