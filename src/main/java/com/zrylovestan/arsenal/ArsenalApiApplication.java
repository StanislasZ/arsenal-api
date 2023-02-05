package com.zrylovestan.arsenal;



import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan(basePackages="com.zrylovestan.arsenal")
@EnableAsync
@EnableSwagger2
public class ArsenalApiApplication {


    public static void main(String[] args) {
        SpringApplication.run(ArsenalApiApplication.class, args);

    }

}
