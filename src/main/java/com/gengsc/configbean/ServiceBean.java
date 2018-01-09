package com.gengsc.configbean;

import com.gengsc.registry.BaseRegistryDelegate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @Description
 * @Author shichaogeng
 * @Create 2018-01-02 15:13
 */
public class ServiceBean implements InitializingBean, ApplicationContextAware{

    private String intf;

    private String ref;

    private String protocol;

    public String getIntf() {
        return intf;
    }

    public void setIntf(String intf) {
        this.intf = intf;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        //注册
        BaseRegistryDelegate.register(ref, applicationContext);

        //redis发布消息
//        RedisApi.publish("channel:"+ref, ref);
    }

    public static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
