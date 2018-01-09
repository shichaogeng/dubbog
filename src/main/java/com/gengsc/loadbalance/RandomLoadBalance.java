package com.gengsc.loadbalance;

import java.util.List;
import java.util.Random;

/**
 * @Description
 * @Author shichaogeng
 * @Create 2018-01-04 16:24
 */
public class RandomLoadBalance implements LoadBalance {
    @Override
    public NodeInfo doSelect(List<String> list) {
        int index = new Random().nextInt(list.size());
        String jsonStr = list.get(index);
        return NodeInfoUtil.getNodeInfo(jsonStr);
    }
}
