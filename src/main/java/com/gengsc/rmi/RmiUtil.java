package com.gengsc.rmi;

import com.gengsc.loadbalance.NodeInfo;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * @Description
 * @Author shichaogeng
 * @Create 2018-01-05 16:05
 */
public class RmiUtil {

    /**
     * 启动rmi服务
     */
    public static void startRmiServer(String host, String port, String id) {
        try {
            SoaRmi soaRmi = new SoaRmiImpl();
            LocateRegistry.createRegistry(Integer.valueOf(port));
            //rmi://127.0.0.1:1135/fudisfuodsuf

            Naming.bind("rmi://" + host + ":" + port + "/" + id, soaRmi);
            System.out.println("rmi server start !!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SoaRmi startRmiClient(NodeInfo nodeinfo, String id) {
        String host = nodeinfo.getHost();
        String port = nodeinfo.getPort();

        try {
            return (SoaRmi) Naming.lookup("rmi://" + host + ":" + port + "/" + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
