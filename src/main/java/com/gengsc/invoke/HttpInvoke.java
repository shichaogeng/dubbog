package com.gengsc.invoke;

import com.alibaba.fastjson.JSONObject;
import com.gengsc.http.HttpRequest;
import com.gengsc.loadbalance.NodeInfo;

import java.util.Map;

/**
 * @Description
 * http协议调用
 * @Author shichaogeng
 * @Create 2018-01-02 22:15
 */
public class HttpInvoke implements Invoke {

    @Override
    public Object invoke(Invocation invocation) throws Exception{

        Map<String, Object> sendMessage = InvokeUtil.getSendMessage(invocation);
        NodeInfo nodeInfo = (NodeInfo) sendMessage.get("nodeInfo");
        JSONObject sendParams = (JSONObject) sendMessage.get("sendParams");

        //http://127.0.0.1:8080/provider/soa/service
        String url = "http://"+nodeInfo.getHost()+":"+nodeInfo.getPort()+nodeInfo.getContextpath();

        //调用生产者服务
        String resultStr = HttpRequest.sendPost(url, sendParams.toString());
        Object result = JSONObject.parse(resultStr);

        return result;
    }

}
