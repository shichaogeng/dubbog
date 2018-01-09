package com.gengsc.registry;

import com.alibaba.fastjson.JSONObject;
import com.gengsc.configbean.ProtocolConfig;
import com.gengsc.configbean.RegistryConfig;
import com.gengsc.configbean.ServiceBean;
import com.gengsc.redis.RedisApi;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author shichaogeng
 * @Create 2018-01-03 17:17
 */
public class RedisRegistry implements BaseRegistry {

    @Override
    public boolean register(String ref, ApplicationContext applicationContext) {
        //获取协议实例
        ProtocolConfig protocolConfig = applicationContext.getBean(ProtocolConfig.class);

        //放到注册中心中,要拿到注册中心地址信息
        RegistryConfig registryConfig = applicationContext.getBean(RegistryConfig.class);
        RedisApi.createJedisPool(registryConfig.getAddress());

        //获取所有提供的接口
        Map<String, ServiceBean> serviceBeans = applicationContext.getBeansOfType(ServiceBean.class);
        for (String key : serviceBeans.keySet()) {
            if (ref.equals(serviceBeans.get(key).getRef())) {
                JSONObject jsonIn = new JSONObject();
                jsonIn.put("protocolConfig", JSONObject.toJSONString(protocolConfig));
                jsonIn.put("serviceBean", JSONObject.toJSONString(serviceBeans.get(key)));

                JSONObject hostAndPort = new JSONObject();
                String hostAndPortStr = protocolConfig.getHost() + ":" + protocolConfig.getPort();
                hostAndPort.put(hostAndPortStr, jsonIn);
                this.lpush(ref, hostAndPortStr, hostAndPort);
            }
        }

        return true;
    }

    /**
     * 去重复
     * @param key
     * @param hostAndPort
     */
    private void lpush(String key, String hostAndPortStr, JSONObject hostAndPort) {

        if (RedisApi.exists(key)) {//存在key

            //redis中存在的
            List<String> registryInfo = RedisApi.lrange(key);

            //如果存在就直接返回了
            for (String node : registryInfo) {
                JSONObject NodeObject = JSONObject.parseObject(node);
                if (NodeObject.containsKey(hostAndPortStr)) {
                    return;
                }
            }

        }

        RedisApi.lpush(key, hostAndPort.toJSONString());

    }

    @Override
    public List<String> getRegistry(String id, ApplicationContext applicationContext) {

        //放到注册中心中,要拿到注册中心地址信息
        RegistryConfig registryConfig = applicationContext.getBean(RegistryConfig.class);
        RedisApi.createJedisPool(registryConfig.getAddress());

        if (RedisApi.exists(id)) {
            return RedisApi.lrange(id);
        }

        return null;
    }
}
