package com.gengsc.cluster;

import com.gengsc.configbean.ReferenceBean;
import com.gengsc.invoke.Invocation;
import com.gengsc.invoke.Invoke;

/**
 * @Description
 * 如果调用失败自动切换到其他集群节点，重试其他服务器
 * @Author shichaogeng
 * @Create 2018-01-10 16:01
 */
public class FailoverCluster implements Cluster {

    @Override
    public Object invoke(Invocation invocation) throws Exception {

        ReferenceBean referenceBean = invocation.getReferenceBean();
        //通过配置取得重试次数
        Integer retries = Integer.parseInt(referenceBean.getRetries());

        Invoke invoke = invocation.getInvoke();
        for (int i = 0; i < retries; i++) {
            try {
                return invoke.invoke(invocation);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

        }

        throw new RuntimeException("retries" + retries + "次全部失败!!!!");
    }
}
