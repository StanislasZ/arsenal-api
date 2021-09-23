package com.cmbnb.weChatNotice.modules.cbs.xmlModel.erothclt78;

import com.cmbnb.weChatNotice.modules.cbs.xmlModel.InfoReq;
import lombok.Data;

import javax.xml.bind.annotation.*;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "CBSERPPGK")
@XmlType(name = "CBSERPPGK")
public class ErothcltDataReq78 {

    @XmlElement(name = "INFO")
    private InfoReq info;

    @XmlElement(name = "CIOTHSEQY")
    private Ciothseqy ciothseqy;

}
