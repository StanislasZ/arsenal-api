package com.cmbnb.weChatNotice;

import com.cmbnb.weChatNotice.modules.cbs.util.CheckNumUtil;
import com.cmbnb.weChatNotice.modules.cbs.util.XmlUtil;
import com.cmbnb.weChatNotice.modules.cbs.xmlModel.ercurdtl28.ErcurdtlDataReq28;
import com.cmbnb.weChatNotice.modules.cbs.xmlModel.ercurdtl28.Ercurdtla;
import com.cmbnb.weChatNotice.modules.cbs.xmlModel.InfoReq;
import com.cmbnb.weChatNotice.modules.cbs.xmlModel.Pgk;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class XmlTest {

    @Autowired
    CheckNumUtil checkNumUtil;

    @Test
    public void testGetCheckCode() {
        System.out.println(checkNumUtil.GetCheckSumWithCRC32("a"));
    }

    @Test
    public void testObj2Xml() {

        ErcurdtlDataReq28 ercurdtlDataReq = new ErcurdtlDataReq28();
        InfoReq info = new InfoReq();
        info.setFunNam("ERCURDTL");
        ercurdtlDataReq.setInfo(info);
        Ercurdtla ercurdtla = new Ercurdtla();
        ercurdtla.setItmDir("A");
        ercurdtla.setVtlFlg("0");
        ercurdtlDataReq.setErcurdtla(ercurdtla);

        Pgk pgk = new Pgk();
//        pgk.setCheckCode("Z6B68B4BC");
        //先设置data
        pgk.setDataByType(ercurdtlDataReq, "GBK");
        //再根据data 获取校验码
        pgk.setCheckCode(checkNumUtil.GetCheckSumWithCRC32(pgk.getData()));
        String xml = XmlUtil.convert2Xml(pgk, "GBK");



        System.out.println(xml);

    }


    @Test
    public void testXmlToObj() {
        String xmlStr = "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n" +
                "<PGK>\n" +
                "    <DATA><![CDATA[<?xml version=\"1.0\" encoding=\"GBK\" ?>\n" +
                "<CBSERPPGK>\n" +
                "    <INFO>\n" +
                "        <FUNNAM>TEST</FUNNAM>\n" +
                "    </INFO>\n" +
                "    <ERCURDTLA>\n" +
                "        <ITMDIR>A</ITMDIR>\n" +
                "        <VTLFLG>0</VTLFLG>\n" +
                "    </ERCURDTLA>\n" +
                "</CBSERPPGK>\n" +
                "]]></DATA>\n" +
                "    <CHECKCODE>Z6B68B4BC</CHECKCODE>\n" +
                "</PGK>\n";
        Pgk pgk = XmlUtil.convert2JavaBean(xmlStr, Pgk.class);

        System.out.println(pgk.getDataByType(ErcurdtlDataReq28.class));
        System.out.println("----------------");
        String xmlData = pgk.getData();
        ErcurdtlDataReq28 pgk2 = XmlUtil.convert2JavaBean(xmlData, ErcurdtlDataReq28.class);
        System.out.println(pgk2);


    }



}
