package com.cmbnb.weChatNotice.modules.cbs.xmlModel.ercurdtl28;


import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Erdtlseqz {

    @XmlElement(name = "DTLSEQ")
    private String dtlSeq;    //续传流水号
}
