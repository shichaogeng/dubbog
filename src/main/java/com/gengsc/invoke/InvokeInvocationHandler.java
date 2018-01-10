package com.gengsc.invoke;

import com.gengsc.cluster.Cluster;
import com.gengsc.configbean.ReferenceBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Description 在这里进行rpc的远程调用
 * rpc:http、rmi、netty
 * @Author shichaogeng
 * @Create 2018-01-02 22:09
 */
public class InvokeInvocationHandler implements InvocationHandler {

    private Invoke invoke;

    private ReferenceBean referenceBean;

    public InvokeInvocationHandler(Invoke invoke, ReferenceBean referenceBean) {
        this.invoke = invoke;
        this.referenceBean = referenceBean;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("代理实例，调用了invoke.invoke()");

        //最终调用远程provider
        Invocation invocation = new Invocation();
        invocation.setMethod(method);
        invocation.setArgs(args);
        invocation.setReferenceBean(this.referenceBean);
        invocation.setInvoke(invoke);
//        Object result = invoke.invoke(invocation);
        Cluster cluster = ReferenceBean.clusters.get(referenceBean.getCluster());
        Object result = cluster.invoke(invocation);
        return result;
    }
}
