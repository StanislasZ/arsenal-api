package com.cmbnb.weChatNotice.modules.cbs.util;



import com.cmbnb.weChatNotice.modules.task.application.SchedulingRunnable;
import com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * @author 01178704
 */
public class XmlUtil {

    private static final Logger logger = LoggerFactory.getLogger(XmlUtil.class);
    /**
     * JavaBean装换成xml
     * 默认编码UTF-8
     * @param obj
     * @return
     */
    public static String convert2Xml(Object obj) {
        return convert2Xml(obj,"UTF-8");
    }
    /**
     * JavaBean装换成xml
     * @param obj
     * @param encoding
     * @return
     */
    public static String convert2Xml(Object obj, String encoding) {
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true); //
            marshaller.setProperty(Marshaller.JAXB_ENCODING,encoding);
            // 不转义字符
            marshaller.setProperty(CharacterEscapeHandler.class.getName(), (CharacterEscapeHandler) (chars, start, length, isAttVal, writer) -> writer.write(chars, start, length));
            StringWriter writer = new StringWriter();
            marshaller.marshal(obj,writer);
            // <?xml version="1.0" encoding="UTF-8" standalone="yes"?> 把 standalone="yes" 替换掉
            String result =  writer.toString()
                    .replace("standalone=\"yes\"", "")
                    .replace("standalone=\"no\"", "");
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            throw  new  RuntimeException(e);
        }
    }


    /**
     * xml装换成JavaBean
     * @param xml
     * @param c
     * @return
     */
    @SuppressWarnings("unchecked")
    public static<T> T convert2JavaBean(String xml,Class<T> c){
        try {
            JAXBContext context = JAXBContext.newInstance(c);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return  (T)unmarshaller.unmarshal(new StringReader(xml));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("convert2JavaBean时 出错");
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }



}
