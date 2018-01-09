package com.gengsc.invoke;

import com.alibaba.fastjson.JSONObject;
import com.gengsc.loadbalance.NodeInfo;
import com.gengsc.netty.NettyUtil;

import java.util.Map;

/**
 * @Description
 * @Author shichaogeng
 * @Create 2018-01-05 16:03
 */
public class NettyInvoke implements Invoke {

    @Override
    public Object invoke(Invocation invocation) {

        Map<String, Object> sendMessage = InvokeUtil.getSendMessage(invocation);
        NodeInfo nodeInfo = (NodeInfo) sendMessage.get("nodeInfo");
        JSONObject sendParams = (JSONObject) sendMessage.get("sendParams");

        try {
            String result = NettyUtil.sendMsg(nodeInfo.getHost(), nodeInfo.getPort(), sendParams.toString());
            return JSONObject.parse(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
