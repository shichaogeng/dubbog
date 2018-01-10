package com.gengsc.cluster;

import com.gengsc.invoke.Invocation;
import com.gengsc.invoke.Invoke;

/**
 * @Description
 * 失败直接忽略，用于日志啥的
 * @Author shichaogeng
 * @Create 2018-01-10 16:02
 */
public class FailsafeCluster implements Cluster{
    @Override
    public Object invoke(Invocation invocation) throws Exception {

        Invoke invoke = invocation.getInvoke();

        Object result = null;
        try {
            result = invoke.invoke(invocation);
        } catch (Exception e) {
            e.printStackTrace();
            return "忽略";
        }

        return result;
    }
}
