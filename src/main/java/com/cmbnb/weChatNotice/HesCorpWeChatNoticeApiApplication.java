package com.cmbnb.weChatNotice;


//import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages="com.cmbnb.weChatNotice")
public class HesCorpWeChatNoticeApiApplication {




    public static void main(String[] args) {
        SpringApplication.run(HesCorpWeChatNoticeApiApplication.class, args);



    }

}
