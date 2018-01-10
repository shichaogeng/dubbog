package com.gengsc.invoke;

/**
 * @Description
 * @Author shichaogeng
 * @Create 2018-01-02 22:15
 */
public interface Invoke {

    //返回的json对象
    Object invoke(Invocation invocation) throws Exception;
}
