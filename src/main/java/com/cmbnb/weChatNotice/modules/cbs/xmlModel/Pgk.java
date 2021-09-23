package com.cmbnb.weChatNotice.modules.cbs.xmlModel;

import com.cmbnb.weChatNotice.modules.cbs.util.CDATAXmlAdapter;
import com.cmbnb.weChatNotice.modules.cbs.util.XmlUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.lang.reflect.GenericDeclaration;

/**

 <?xml version="1.0" encoding="GBK"?>
 <PGK>
     <DATA>
     <![CDATA[
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
     ]]>
     </DATA>
     <CHECKCODE>Z6B68B4BC</CHECKCODE>
 </PGK>


 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "PGK")
@XmlType(name = "PGK", propOrder = {"data", "checkCode"})
public class Pgk {

    @XmlJavaTypeAdapter(CDATAXmlAdapter.class)
    @XmlElement(name = "DATA" )
    private String data;

    @XmlElement(name = "CHECKCODE")
    private String checkCode;

    /**
     *
     * @param clazz
     * @param <T>
     * @return
     */
     public <T> T getDataByType(Class<T> clazz) {
         Assert.isTrue(StringUtils.isNotBlank(data), "data 字符串不能为空！");
         String xmlData = this.data;
         return XmlUtil.convert2JavaBean(xmlData, clazz);
    }
    public <T> void setDataByType(T t, String encoding) {
         String xmlData = XmlUtil.convert2Xml(t, encoding);
         this.data = xmlData;
    }




}
