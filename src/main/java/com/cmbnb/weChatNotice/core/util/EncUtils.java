package com.cmbnb.weChatNotice.core.util;

import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EncUtils {

    @Value("${ENCRYPTOR_PASSWORD:gxxd}")
    private String encSalt;



    public static String getEncStr(String value) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        //加密所需的salt(盐)
        textEncryptor.setPassword("gxxd");
        //要加密的数据（数据库的用户名或密码）
        String result = textEncryptor.encrypt(value);

        return result;
    }

    public static String getDecStr(String value) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword("gxxd");
        String result = textEncryptor.decrypt(value);
        return result;
    }


//    public static void main(String[] args) {
//        System.out.println(getEncStr("J2m7qa#e"));
//    }
}
