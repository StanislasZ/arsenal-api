package com.cmbnb.weChatNotice.modules.cbs.xmlModel.ercrcqry51;


import com.cmbnb.weChatNotice.modules.cbs.xmlModel.InfoReq;
import lombok.Data;

import javax.xml.bind.annotation.*;


/**
 *
 * 5.1 ERP导出当日电子回单数据(ERCRCQRY) 请求报文 data部分
 *
 *
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "CBSERPPGK")
@XmlType(name = "CBSERPPGK")
public class ErcrcqryDataReq51 {

    @XmlElement(name = "INFO")
    private InfoReq info;

    @XmlElement(name = "DCDRCQRYA")
    private Dcdrcqrya dcdrcqrya;

    @XmlElement(name = "DCDRCQRYX")
    private Dcdrcqryx dcdrcqryx;

    @XmlElement(name = "DCDRCSEQZ")
    private Dcdrcseqz dcdrcseqz;


}
