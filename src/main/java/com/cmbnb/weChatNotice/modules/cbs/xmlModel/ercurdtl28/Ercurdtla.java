package com.cmbnb.weChatNotice.modules.cbs.xmlModel.ercurdtl28;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Ercurdtla {

    @XmlElement(name = "ITMDIR")
    private String itmDir;

    @XmlElement(name = "VTLFLG")
    private String vtlFlg;
}
