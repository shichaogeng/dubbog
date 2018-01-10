package com.gengsc.cluster;

import com.gengsc.invoke.Invocation;

/**
 * @Description
 * @Author shichaogeng
 * @Create 2018-01-10 15:57
 */
public interface Cluster {

    Object invoke(Invocation invocation) throws Exception;
}
