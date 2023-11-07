package com.qingmeng;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description $INDEX
 * @createTime 2023年11月07日 09:04:00
 */
@SpringBootApplication(scanBasePackages = {"com.qingmeng"})
@MapperScan({"com.qingmeng.mapper"})
public class TalkTimeClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(TalkTimeClientApplication.class,args);
    }
}