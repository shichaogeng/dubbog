package com.gengsc.configbean;

import com.gengsc.cluster.Cluster;
import com.gengsc.cluster.FailfastCluster;
import com.gengsc.cluster.FailoverCluster;
import com.gengsc.cluster.FailsafeCluster;
import com.gengsc.invoke.*;
import com.gengsc.loadbalance.LoadBalance;
import com.gengsc.loadbalance.RandomLoadBalance;
import com.gengsc.loadbalance.RoundRobinLoadBalance;
import com.gengsc.registry.BaseRegistryDelegate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description
 * @Author shichaogeng
 * @Create 2018-01-02 15:13
 */
public class ReferenceBean implements Serializable, FactoryBean, ApplicationContextAware, InitializingBean {

    public ReferenceBean() {
        System.out.println("ReferenceBean实例");
    }

    private String id;

    private String intf;

    private String loadbalance;

    private String protocol;

    private String retries;

    private String cluster;

    public String getRetries() {
        return retries;
    }

    public void setRetries(String retries) {
        this.retries = retries;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIntf() {
        return intf;
    }

    public void setIntf(String intf) {
        this.intf = intf;
    }

    public String getLoadbalance() {
        return loadbalance;
    }

    public void setLoadbalance(String loadbalance) {
        this.loadbalance = loadbalance;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private static Map<String, Invoke> invokes = new HashMap<>();
    public static Map<String, LoadBalance> loadBalances = new HashMap<>();
    public static Map<String, Cluster> clusters = new HashMap<>();

    /**
     * 返回一个实例，在spring初始化时由getBean方法调用
     * applicationcontext.getBean();//DefaultSingletonBeanRegistry.getSingleton();
     * getObject的返回值交由spring进行管理
     * 在此方法里，返回的是intf接口的代理对象
     *
     * @return
     * @throws Exception
     */
    @Override
    public Object getObject() throws Exception {

        System.out.println("返回intf的代理对象...");

        Invoke invoke = null;
        if (!StringUtils.isEmpty(protocol)) {//配置了协议
            invoke = invokes.get(protocol);
        } else {//未配置协议，取配置文件中protocol标签配置的协议
            ProtocolConfig protocolConfig = applicationContext.getBean(ProtocolConfig.class);
            if (protocolConfig != null) {
                invoke = invokes.get(protocolConfig.getName());
            } else {
                invoke = invokes.get("http");
            }
        }
        Object proxy = Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class<?>[]{this.getObjectType()}, new InvokeInvocationHandler(invoke, this));
        return proxy;
    }

    @Override
    public Class<?> getObjectType() {
        Class clazz = null;
        if (!StringUtils.isEmpty(intf)) {
            try {
                clazz = Class.forName(intf);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return clazz;
    }

    static {
        /**
         * 注册协议
         */
        invokes.put("http", new HttpInvoke());
        invokes.put("rmi", new RmiInvoke());
        invokes.put("netty", new NettyInvoke());

        /**
         * 注册协议
         */
        loadBalances.put("random", new RandomLoadBalance());
        loadBalances.put("round", new RoundRobinLoadBalance());

        /**
         * 集群容错方式
         */
        clusters.put("failover", new FailoverCluster());
        clusters.put("failfast", new FailfastCluster());
        clusters.put("failsafe", new FailsafeCluster());
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    //生产者服务列表
    private List<String> registries;

    public List<String> getRegistries() {
        return registries;
    }

    public void setRegistries(List<String> registries) {
        this.registries = registries;
    }

    private static ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    public void afterPropertiesSet() throws Exception {
        registries = BaseRegistryDelegate.getRegistries(id, applicationContext);
        //redis订阅消息
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                RedisApi.subscribe(new RedisPubSubListener(), "channel:"+id);
            }
        }).start();*/

    }

}
