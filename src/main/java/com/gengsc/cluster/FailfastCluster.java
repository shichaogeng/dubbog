package com.gengsc.cluster;

import com.gengsc.invoke.Invocation;
import com.gengsc.invoke.Invoke;

/**
 * @Description
 * 直接失败，失败立即报错,用于非幂等操作
 * @Author shichaogeng
 * @Create 2018-01-10 16:02
 */
public class FailfastCluster implements Cluster{

    @Override
    public Object invoke(Invocation invocation) throws Exception {

        Invoke invoke = invocation.getInvoke();

        Object result = null;
        try {
            result = invoke.invoke(invocation);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return result;
    }
}
