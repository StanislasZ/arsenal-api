package com.cmbnb.weChatNotice.modules.cbs.xmlModel.ercrcqry51;


import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Dcdrcseqz {

    @XmlElement(name = "SQRNB1")
    private String sqrNb1;


    /**
     * 当日历史标记
     */
    @XmlElement(name = "CNTFLG")
    private String cntFlg;
}
