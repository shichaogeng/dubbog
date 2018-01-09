package com.gengsc.redis;

import com.gengsc.configbean.ReferenceBean;
import redis.clients.jedis.JedisPubSub;

/**
 * @Description
 * @Author shichaogeng
 * @Create 2018-01-08 17:14
 */
public class RedisPubSubListener extends JedisPubSub {

    private ReferenceBean referenceBean;

    public RedisPubSubListener(ReferenceBean referenceBean) {
        this.referenceBean = referenceBean;
    }

    @Override
    public void onMessage(String channel, String message) {
        super.onMessage(channel, message);
    }
}
