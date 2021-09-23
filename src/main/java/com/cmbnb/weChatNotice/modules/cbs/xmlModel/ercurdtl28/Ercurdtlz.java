package com.cmbnb.weChatNotice.modules.cbs.xmlModel.ercurdtl28;


import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Ercurdtlz {

    @XmlElement(name = "ACTBAL")
    private BigDecimal actBal;  //余额

    @XmlElement(name = "ACTNAM")
    private String actNam;  //账户名称


    @XmlElement(name = "ACTNBR")
    private String actNbr; //银行交易账号


    /**
     * 银行流水号
     * 到5.1 导出当日电子回单数据
     */
    @XmlElement(name = "BNKFLW")
    private String bnkFlw;  //银行流水号

    /**
     * 银行交易日期时间， 过滤时， 该字段值必须为当日
     */
    @XmlElement(name = "BNKTIM")
    private String bnkTim;


    @XmlElement(name = "DTLSEQ")
    private String dtlSeq;  //交易流水号

    /**
     * 用这个作为 1.12接口的输入（对应1.12的BUSNBR业务流水号）
     */
    @XmlElement(name = "PAYNBR")
    private String payNbr;  //支付流水号，  一般以 AP开头


    /**
     * 用这个区分 收 还是 付
     */
    @XmlElement(name = "ITMDIR")
    private String itmDir;  //借贷方向，  1借  2贷

    @XmlElement(name = "BMINBR")
    private String bmiNbr;  //客商编号

    @XmlElement(name = "SPCRM1")
    private String spcRm1;  //备注1

    @XmlElement(name = "SPCRM2")
    private String spcRm2;  //备注2

    @XmlElement(name = "CLTSEQ")
    private String cltSeq;  //32位客户号

    @XmlElement(name = "BNKNBR")
    private String bnkNbr;  //银行号
}
