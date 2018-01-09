package com.gengsc.loadbalance;

import java.util.List;

/**
 * @Description
 * @Author shichaogeng
 * @Create 2018-01-04 16:34
 */
public class RoundRobinLoadBalance implements LoadBalance {

    private static Integer index = 0;

    @Override
    public NodeInfo doSelect(List<String> list) {

        String jsonStr = null;
        synchronized (index) {
            if (index >= list.size()) {
                index = 0;
            }
            jsonStr = list.get(index);
            index++;
        }

        if (jsonStr != null) {
            return NodeInfoUtil.getNodeInfo(jsonStr);
        }
        return null;
    }
}
