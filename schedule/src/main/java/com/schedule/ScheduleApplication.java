package com.schedule;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ServletComponentScan
@ComponentScan(basePackages={"com.schedule","com.common"})//指定根路径扫描
@MapperScan(basePackages={"com.schedule.**.dao","com.common.**.dao"})//指定dao扫描
@EnableScheduling
public class ScheduleApplication {
    public static void main(String[] args) {
        SpringApplication.run(ScheduleApplication.class,args);
    }
}


/*@SpringBootApplication
@ServletComponentScan
@ComponentScan(basePackages={"com.schedule","com.common"})//指定根路径扫描
@MapperScan(basePackages={"com.schedule.**.dao","com.common.**.dao"})//指定dao扫描
@EnableScheduling
public class ScheduleApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ScheduleApplication.class);
    }


    public static void main(String[] args) {
        SpringApplication.run(ScheduleApplication.class, args);
    }
}*/
