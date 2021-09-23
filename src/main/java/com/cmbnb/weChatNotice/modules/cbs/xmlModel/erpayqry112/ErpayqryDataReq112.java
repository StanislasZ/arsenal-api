package com.cmbnb.weChatNotice.modules.cbs.xmlModel.erpayqry112;


import com.cmbnb.weChatNotice.modules.cbs.xmlModel.InfoReq;
import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 *  1.12 导出支付结算数据(ERPAYQRY) 请求报文 data部分
 *
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "CBSERPPGK")
@XmlType(name = "CBSERPPGK")
public class ErpayqryDataReq112 {

    @XmlElement(name = "INFO")
    private InfoReq info;

    @XmlElement(name = "APBUSNBRX")
    private List<Apbusnbrx> apbusnbrxList;


}
