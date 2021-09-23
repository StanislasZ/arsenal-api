package com.cmbnb.weChatNotice.modules.cbs.xmlModel.ercrcqry51;


import lombok.Data;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Dcdrqryz1 {

    /**
     * 作为 5.2 输入 DCPRTRCPX中的 ACCNBR
     */
    @XmlElement(name = "ACCNBR")
    private String accNbr;   //本方账号


    @XmlElement(name = "SQRNB1")
    private String sqrNb1; //序号， 作为5.2输入参数之一

    @XmlElement(name = "ITMDIR")
    private String itmDir;  //借贷标志, 可能用不上， 测试环境该字段返回为空

    @XmlElement(name = "BNKFLW")
    private String bnkFlw;   //银行流水号， 用这个匹配

    @XmlElement(name = "ISTNBR")
    private String istNbr;  //打印实例号


    @XmlElement(name = "RCVEAN")
    private String rcvEan;  //对方账户名



}
