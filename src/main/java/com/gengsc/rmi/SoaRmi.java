package com.gengsc.rmi;

import java.rmi.Remote;

/**
 * @Description
 * @Author shichaogeng
 * @Create 2018-01-05 16:06
 */
public interface SoaRmi extends Remote{

    String invoke(String param) throws Exception;
}
