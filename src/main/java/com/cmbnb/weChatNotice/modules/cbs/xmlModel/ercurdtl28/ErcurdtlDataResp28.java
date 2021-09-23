package com.cmbnb.weChatNotice.modules.cbs.xmlModel.ercurdtl28;


import com.cmbnb.weChatNotice.modules.cbs.xmlModel.InfoResp;
import com.cmbnb.weChatNotice.modules.cbs.xmlModel.SycomretzResp;
import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 *  2.8 当日明细数据对接(ERCURDTL) 返回 报文中
 *  用于生成DATA部分
 *
 *
 *
 *
 *
 *
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "CBSERPPGK")
@XmlType(name = "CBSERPPGK")
public class ErcurdtlDataResp28 {

    @XmlElement(name = "INFO")
    private InfoResp infoResp;


    @XmlElement(name = "ERCURDTLZ")
    private List<Ercurdtlz> ercurdtlzList;


    @XmlElement(name = "SYCOMRETZ")
    private SycomretzResp sycomretzResp;


    @XmlElement(name = "ERDTLSEQZ")
    private Erdtlseqz erdtlseqz;




}
