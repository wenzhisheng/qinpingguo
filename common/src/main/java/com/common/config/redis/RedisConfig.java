//package com.common.config.redis;
//
//import org.apache.commons.lang.StringUtils;
//import org.redisson.Redisson;
//import org.redisson.api.RedissonClient;
//import org.redisson.config.ClusterServersConfig;
//import org.redisson.config.Config;
//import org.redisson.spring.session.config.EnableRedissonHttpSession;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @Author HeiZi
// * @ClassName com.merchant.config.redis
// * @Description Redis配置
// * @Date 2018/7/27 15:30
// * @Version 1.0
// */
//@Configuration
//@EnableRedissonHttpSession(maxInactiveIntervalInSeconds = 60)
//public class RedisConfig {
//    //超时时间
//    @Value("${cluster-servers-config.timeout}")
//    private int timeout;
//    //密码
//    @Value("${cluster-servers-config.password}")
//    private String password;
//    //从连接池大小
//    @Value("${cluster-servers-config.slave-connection-pool-size}")
//    private int slaveConnectionPoolSize;
//    //主连接池大小
//    @Value("${cluster-servers-config.master-connection-pool-size}")
//    private int masterConnectionPoolSize;
//    //连接失败尝试次数
//    @Value("${cluster-servers-config.failed-attempts}")
//    private int failedAttempts;
//    //命令失败重试次数
//    @Value("${cluster-servers-config.retry-attempts}")
//    private int retryAttempts;
//    //哨兵模式数组
//    @Value("${cluster-servers-config.cluster-addresses}")
//    private String[] clusterAddresses;
//
//    @Bean(name="redissonClient")
//    public RedissonClient getRedis(){
//        Config config = new Config();
//        ClusterServersConfig clusterServersConfig = config.useClusterServers()
//                .addNodeAddress(this.clusterAddresses)
//                .setFailedAttempts(this.failedAttempts)
//                .setRetryAttempts(this.retryAttempts)
//                .setTimeout(this.timeout)
//                .setMasterConnectionPoolSize(this.masterConnectionPoolSize)
//                .setSlaveConnectionPoolSize(this.slaveConnectionPoolSize);
//
//        if (StringUtils.isNotBlank(this.password)) {
//            clusterServersConfig.setPassword(this.password);
//        }
//        clusterServersConfig.setScanInterval(60000);
//        return Redisson.create(config);
//    }
//}
