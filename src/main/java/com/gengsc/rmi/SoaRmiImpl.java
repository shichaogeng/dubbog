package com.gengsc.rmi;

import com.gengsc.invoke.InvokeUtil;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @Description
 * 提供者使用
 * @Author shichaogeng
 * @Create 2018-01-05 16:06
 */
public class SoaRmiImpl extends UnicastRemoteObject implements SoaRmi{

    private static final long serialVersionUID = 132456768L;

    protected SoaRmiImpl() throws RemoteException {
    }

    @Override
    public String invoke(String param) throws Exception {

        return InvokeUtil.invokeServiceMethod(param);
    }

}
