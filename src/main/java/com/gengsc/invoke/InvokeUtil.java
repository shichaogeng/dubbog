package com.gengsc.invoke;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gengsc.configbean.ReferenceBean;
import com.gengsc.configbean.ServiceBean;
import com.gengsc.loadbalance.LoadBalance;
import com.gengsc.loadbalance.NodeInfo;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author shichaogeng
 * @Create 2018-01-05 15:57
 */
public class InvokeUtil {

    public static Map<String, Object> getSendMessage(Invocation invocation) {
        //生产者列表，从注册中心获得
        ReferenceBean referenceBean = invocation.getReferenceBean();
        List<String> registries = referenceBean.getRegistries();

        //负载均衡，从生产和列表中选择
        LoadBalance loadBalance = ReferenceBean.loadBalances.get(referenceBean.getLoadbalance());
        NodeInfo nodeInfo = loadBalance.doSelect(registries);

        /**
         * 怎么生成代理
         */
        //通过json串(id,method)来确定spring中的实例来调用远程的方法
        JSONObject sendParams = new JSONObject();
        sendParams.put("methodName", invocation.getMethod().getName());
        sendParams.put("methodParams", invocation.getArgs());
        sendParams.put("serviceId", referenceBean.getId());
        sendParams.put("methodTypes", invocation.getMethod().getParameterTypes());

        Map<String, Object> result = new HashMap<>();
        result.put("nodeInfo", nodeInfo);
        result.put("sendParams", sendParams);

        return result;
    }

    public static String invokeServiceMethod(String param) throws Exception{

        JSONObject requestparam = JSONObject.parseObject(param);

        //要从远程的生产者的spring容器中拿到对应的serviceid实例
        String serviceId = requestparam.getString("serviceId");
        String methodName = requestparam.getString("methodName");
        JSONArray paramTypes = requestparam.getJSONArray("methodTypes");
        JSONArray methodParams = requestparam.getJSONArray("methodParams");

        //这个就是反射的参数
        Object[] methodParamObjs = null;
        if (methodParams != null) {
            methodParamObjs = new Object[methodParams.size()];
            int i = 0;
            for (Object o : methodParams) {
                methodParamObjs[i++] = o;
            }
        }

        Class[] paramTypeObjs = null;
        if (paramTypes != null) {
            paramTypeObjs = new Class[paramTypes.size()];
            int i = 0;
            for (Object o : paramTypes) {
                paramTypeObjs[i++] = Class.forName((String)o);
            }
        }

        //spring上下文
        ApplicationContext applicationContext = ServiceBean.applicationContext;
        Object serviceBean = applicationContext.getBean(serviceId);

        //这个方法的获取
        try {
            Method method = serviceBean.getClass().getMethod(methodName, paramTypeObjs);
            if (method != null) {
                Object result = method.invoke(serviceBean, methodParamObjs);
                return JSONObject.toJSONString(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}

