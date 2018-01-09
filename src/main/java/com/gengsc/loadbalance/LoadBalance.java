package com.gengsc.loadbalance;

import java.util.List;

/**
 * @Description
 * @Author shichaogeng
 * @Create 2018-01-04 16:19
 */
public interface LoadBalance {

    NodeInfo doSelect(List<String> list);
}
