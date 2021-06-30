package com.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ServletComponentScan
@ComponentScan(basePackages={"com.admin","com.common"})//指定根路径扫描
@MapperScan(basePackages={"com.admin.**.dao","com.common.**.dao"})//指定dao扫描
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}


/*@SpringBootApplication
@ServletComponentScan
@ComponentScan(basePackages={"com.admin","com.common"})//指定根路径扫描
@MapperScan(basePackages={"com.admin.**.dao","com.common.**.dao"})//指定dao扫描
public class AdminApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(AdminApplication.class);
    }


    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}*/
