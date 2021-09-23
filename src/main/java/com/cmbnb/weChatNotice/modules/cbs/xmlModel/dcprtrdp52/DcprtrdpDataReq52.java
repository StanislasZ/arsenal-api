package com.cmbnb.weChatNotice.modules.cbs.xmlModel.dcprtrdp52;


import com.cmbnb.weChatNotice.modules.cbs.xmlModel.InfoReq;
import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 *  5.2 电子回单打印(DCPRTRDP) 请求报文 data部分
 *
 *  DCPRTRCPX列表长度不能超过500
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "CBSERPPGK")
@XmlType(name = "CBSERPPGK")
public class DcprtrdpDataReq52 {

    @XmlElement(name = "INFO")
    private InfoReq info;

    @XmlElement(name = "DCPRTRCPX")
    private List<Dcprtrcpx> dcprtrcpxList;
}
