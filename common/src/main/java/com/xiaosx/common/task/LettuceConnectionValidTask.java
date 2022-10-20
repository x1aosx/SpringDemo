package com.xiaosx.security.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author x1aosx
 * @ClassName LettuceConnectionValidTask.java
 * @Description 每隔2秒校验异常lettuce连接是否正常，解决长期空闲lettuce连接关闭但是netty不能及时监控到的问题。
 * @createTime 2022年09月19日 17:21:00
 */
@Component
@Slf4j
public class LettuceConnectionValidTask {
    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    @Scheduled(cron = "0/2 * * * * ?")
    public void task() {
        if (redisConnectionFactory instanceof LettuceConnectionFactory) {
            LettuceConnectionFactory c = (LettuceConnectionFactory) redisConnectionFactory;
            c.validateConnection();
        }
    }
}