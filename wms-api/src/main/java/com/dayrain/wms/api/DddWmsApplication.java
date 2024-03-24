package com.dayrain.wms.api;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.dayrain.**.dao")
@SpringBootApplication
public class DddWmsApplication {


    public static void main(String[] args) {
        SpringApplication.run(DddWmsApplication.class, args);
    }

}
