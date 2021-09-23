package com.cmbnb.weChatNotice.modules.cbs.xmlModel.ercrcqry51;


import com.cmbnb.weChatNotice.modules.cbs.xmlModel.InfoResp;
import com.cmbnb.weChatNotice.modules.cbs.xmlModel.SycomretzResp;
import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;


/**
 *
 * 5.1 ERP导出当日电子回单数据(ERCRCQRY) 返回报文 data部分
 *
 *
 *
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "CBSERPPGK")
@XmlType(name = "CBSERPPGK")
public class ErcrcqryDataResp51 {

    @XmlElement(name = "INFO")
    private InfoResp infoResp;

    @XmlElement(name = "DCDRQRYZ1")
    private List<Dcdrqryz1> dcdrqryz1List;

    @XmlElement(name = "SYCOMRETZ")
    private SycomretzResp sycomretzResp;

    @XmlElement(name = "DCDRCSEQZ")
    private Dcdrcseqz dcdrcseqz;

}
