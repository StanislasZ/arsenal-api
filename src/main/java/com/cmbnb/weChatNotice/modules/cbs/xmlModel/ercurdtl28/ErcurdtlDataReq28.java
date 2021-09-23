package com.cmbnb.weChatNotice.modules.cbs.xmlModel.ercurdtl28;

import com.cmbnb.weChatNotice.modules.cbs.xmlModel.InfoReq;
import lombok.Data;

import javax.xml.bind.annotation.*;

/**

 2.8 2.8当日明细数据对接(ERCURDTL) 请求 报文中
 用于生成DATA部分



 <?xml version="1.0" encoding="GBK"?>
 <CBSERPPGK>
 <INFO>
 <FUNNAM>ERCURDTL</FUNNAM>
 </INFO>
 <ERCURDTLA>
 <ITMDIR>A</ITMDIR>
 <VTLFLG>0</VTLFLG>
 </ERCURDTLA>
 </CBSERPPGK>
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "CBSERPPGK")
@XmlType(name = "CBSERPPGK", propOrder = {"info", "ercurdtlb", "ercurdtla", "erdtlseqz"})
public class ErcurdtlDataReq28 {

    @XmlElement(name = "INFO")
    private InfoReq info;

    @XmlElement(name = "ERCURDTLB")
    private Ercurdtlb ercurdtlb;

    @XmlElement(name = "ERCURDTLA")
    private Ercurdtla ercurdtla;

    @XmlElement(name = "ERDTLSEQZ")
    private Erdtlseqz erdtlseqz;




}
