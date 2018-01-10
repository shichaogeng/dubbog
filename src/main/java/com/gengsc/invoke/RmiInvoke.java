package com.gengsc.invoke;

import com.alibaba.fastjson.JSONObject;
import com.gengsc.loadbalance.NodeInfo;
import com.gengsc.rmi.RmiUtil;
import com.gengsc.rmi.SoaRmi;

import java.util.Map;

/**
 * @Description
 * @Author shichaogeng
 * @Create 2018-01-05 15:31
 */
public class RmiInvoke implements Invoke {

    @Override
    public Object invoke(Invocation invocation) throws Exception {

        Map<String, Object> sendMessage = InvokeUtil.getSendMessage(invocation);
        NodeInfo nodeInfo = (NodeInfo) sendMessage.get("nodeInfo");
        JSONObject sendParams = (JSONObject) sendMessage.get("sendParams");

        //启动客户端
        SoaRmi soaRmi = RmiUtil.startRmiClient(nodeInfo, "soaofrmi");

        String result = soaRmi.invoke(sendParams.toJSONString());
        return JSONObject.parseObject(result);
    }
}
