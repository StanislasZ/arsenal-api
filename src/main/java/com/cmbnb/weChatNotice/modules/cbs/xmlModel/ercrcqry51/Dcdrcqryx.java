package com.cmbnb.weChatNotice.modules.cbs.xmlModel.ercrcqry51;


import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Dcdrcqryx {

    @XmlElement(name = "STADAT")
    private String staDat;

    @XmlElement(name = "ENDDAT")
    private String endDat;
}
