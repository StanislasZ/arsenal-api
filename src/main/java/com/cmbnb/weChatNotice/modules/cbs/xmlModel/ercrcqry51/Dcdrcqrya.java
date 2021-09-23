package com.cmbnb.weChatNotice.modules.cbs.xmlModel.ercrcqry51;


import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Dcdrcqrya {


    /**
     * 账号， 和2.8的actNbr相同
     */
    @XmlElement(name = "ACCNBR")
    private String accNbr;


}
