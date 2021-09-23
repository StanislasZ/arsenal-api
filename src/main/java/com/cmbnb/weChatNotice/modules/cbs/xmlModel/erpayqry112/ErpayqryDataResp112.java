package com.cmbnb.weChatNotice.modules.cbs.xmlModel.erpayqry112;


import com.cmbnb.weChatNotice.modules.cbs.xmlModel.InfoResp;
import com.cmbnb.weChatNotice.modules.cbs.xmlModel.SycomretzResp;
import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * 1.12 导出支付结算数据(ERPAYQRY) 返回报文 data部分
 *
 *
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "CBSERPPGK")
@XmlType(name = "CBSERPPGK")
public class ErpayqryDataResp112 {

    @XmlElement(name = "INFO")
    private InfoResp infoResp;

    @XmlElement(name = "APPAYINFZ")
    List<Appayinfz> appayinfzList;

    @XmlElement(name = "SYCOMRETZ")
    private SycomretzResp sycomretzResp;

}
