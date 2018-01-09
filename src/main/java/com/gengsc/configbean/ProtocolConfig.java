package com.gengsc.configbean;

import com.gengsc.netty.NettyUtil;
import com.gengsc.rmi.RmiUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description
 * @Author shichaogeng
 * @Create 2018-01-02 15:13
 */
public class ProtocolConfig implements Serializable, InitializingBean, ApplicationListener<ContextRefreshedEvent> {

    private String name;

    private String port;

    private String host;

    private String contextPath;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //根据配置启动服务器，此方法在protocolConfig 实例化后调用
        if ("rmi".equals(this.name)) {
            RmiUtil.startRmiServer(host, port, "soaofrmi");
        }
    }

    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();

    /**
     * ContextRefreshedEvent是在spring启动完成后出发的事件，onApplicationEvent对事件进行监听
     *
     * @param event
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (!(ContextRefreshedEvent.class == event.getClass())) {
            return;
        }

        if ("netty".equals(this.name)) {
            //启动netty服务端代码会一直阻塞等待连接完成，所以启动一个线程来完成此工作，不然tomcat会一直阻塞在这里
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        NettyUtil.startServer(host, port);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }
}
