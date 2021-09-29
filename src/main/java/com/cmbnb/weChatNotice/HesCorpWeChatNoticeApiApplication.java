package com.cmbnb.weChatNotice;


//import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages="com.cmbnb.weChatNotice")
public class HesCorpWeChatNoticeApiApplication  {


//    private static Logger logger = LoggerFactory.getLogger(HesCorpWeChatNoticeApiApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(HesCorpWeChatNoticeApiApplication.class, args);

    }

}
