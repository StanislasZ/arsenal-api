package com.cmbnb.weChatNotice.modules.cbs.util;

import com.cmbnb.weChatNotice.modules.cbs.bean.CbsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.zip.CRC32;


@Component
public class CheckNumUtil {


    @Autowired
    CbsConfig cbsConfig;

    public CheckNumUtil() {
    }

    public String GetCheckSumWithCRC32(String xmlData) {
        CRC32 crc32 = new CRC32();
        String str = cbsConfig.getCRC32_PASSWORD() + cbsConfig.getBwKey() + xmlData.replaceAll("\n", "").replaceAll("\r", "");
        crc32.update(str.getBytes());
        String result = Long.toHexString(crc32.getValue()).toUpperCase();
        String pattern = "00000000";
        return cbsConfig.getCRC32_PREFIX() + pattern.substring(0, pattern.length() - result.length()) + result;
    }


}