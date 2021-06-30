//package com.common.config.redis;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import javax.servlet.http.HttpSessionEvent;
//import javax.servlet.http.HttpSessionListener;
//
///**
// * @Author HeiZi
// * @ClassName com.common.config.redis
// * @Description Redis Session 监听
// * @Date 2018/7/27 16:00
// * @Version 1.0
// */
//public class RedisSessionListener implements HttpSessionListener {
//
//    private static Logger logger = LogManager.getLogger(RedisSessionListener.class);
//
//    @Override
//    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
//        logger.info("session 创建");
//        logger.info(httpSessionEvent.getSession().getMaxInactiveInterval());
//    }
//
//    @Override
//    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
//        logger.info("session 销毁");
//        logger.info(httpSessionEvent.getSession().getMaxInactiveInterval());
//    }
//
//}
