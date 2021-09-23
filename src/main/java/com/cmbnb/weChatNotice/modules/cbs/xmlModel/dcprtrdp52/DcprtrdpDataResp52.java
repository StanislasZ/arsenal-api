package com.cmbnb.weChatNotice.modules.cbs.xmlModel.dcprtrdp52;


import com.cmbnb.weChatNotice.modules.cbs.xmlModel.InfoResp;
import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * 5.2电子回单打印(DCPRTRDP) 返回报文 data部分
 *
 *
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "CBSERPPGK")
@XmlType(name = "CBSERPPGK")
public class DcprtrdpDataResp52 {

    @XmlElement(name = "INFO")
    private InfoResp infoResp;

    @XmlElement(name = "NTPRTMSGY")
    private List<Ntprtmsgy> ntprtmsgyList;

    @XmlElement(name = "DCPRTRCPZ")
    private List<Dcprtrcpz> dcprtrcpzList;
}
