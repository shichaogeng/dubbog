package com.gengsc.configbean;

import com.gengsc.registry.BaseRegistry;
import com.gengsc.registry.RedisRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author shichaogeng
 * @Create 2018-01-02 15:12
 */
public class RegistryConfig {

    private String protocol;

    private String address;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static Map<String, BaseRegistry> registries = new HashMap<>();

    /**
     * 初始化注册中心
     */
    static {
        registries.put("redis", new RedisRegistry());
        //其他注册方式
    }
}
