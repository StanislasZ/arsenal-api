package com.cmbnb.weChatNotice.modules.cbs.bean;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "cbs")
@Data
public class CbsConfig {

    private String requestHttpUrl;

    private Integer port;

    private String bwKey;

    private String CRC32_PASSWORD;

    private String CRC32_PREFIX;


    private String actNbr; //银行交易账号，用逗号分隔
}
