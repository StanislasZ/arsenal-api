package com.cmbnb.weChatNotice.modules.cbs.xmlModel;


import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class InfoResp {

    @XmlElement(name = "ERPTYP")
    private String erpTyp;

    @XmlElement(name = "ERRMSG")
    private String errMsg;

    @XmlElement(name = "FUNNAM")
    private String funNam;

    @XmlElement(name = "RETCOD")
    private String retCod;

}
