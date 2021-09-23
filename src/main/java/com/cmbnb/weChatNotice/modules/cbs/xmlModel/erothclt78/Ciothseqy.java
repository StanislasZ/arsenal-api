package com.cmbnb.weChatNotice.modules.cbs.xmlModel.erothclt78;


import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Ciothseqy {

    @XmlElement(name = "OTHNBR")
    private String othNbr;    //续传流水号


}
