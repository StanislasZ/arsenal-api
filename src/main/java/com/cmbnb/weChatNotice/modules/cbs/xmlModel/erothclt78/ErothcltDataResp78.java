package com.cmbnb.weChatNotice.modules.cbs.xmlModel.erothclt78;


import com.cmbnb.weChatNotice.modules.cbs.xmlModel.InfoResp;
import com.cmbnb.weChatNotice.modules.cbs.xmlModel.SycomretzResp;
import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * 7.8 导出客商账户资料(EROTHCLT)   返回报文的data部分
 *
 *
 *
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "CBSERPPGK")
@XmlType(name = "CBSERPPGK")
public class ErothcltDataResp78 {


    @XmlElement(name = "INFO")
    private InfoResp infoResp;

    @XmlElement(name = "CICLTOTHY")
    private List<Cicltothy> cicltothyList;

    @XmlElement(name = "CIOTHSEQY")
    private Ciothseqy ciothseqy;

    @XmlElement(name = "SYCOMRETZ")
    private SycomretzResp sycomretzResp;


}
