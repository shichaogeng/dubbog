package com.gengsc.loadbalance;

import com.alibaba.fastjson.JSONObject;

import java.util.Collection;

/**
 * @Description
 * @Author shichaogeng
 * @Create 2018-01-04 16:39
 */
public class NodeInfoUtil {
    public static NodeInfo getNodeInfo(String jsonStr) {
        JSONObject registryJo = JSONObject.parseObject(jsonStr);

        Collection values = registryJo.values();
        JSONObject node = null;
        for (Object value : values) {
            node = JSONObject.parseObject(value.toString());
        }

        JSONObject protocolConfig = node.getJSONObject("protocolConfig");
        NodeInfo nodeinfo = new NodeInfo();
        nodeinfo.setHost(protocolConfig.get("host") != null ? protocolConfig.getString("host")
                : "");
        nodeinfo.setPort(protocolConfig.get("port") != null ? protocolConfig.getString("port")
                : "");
        nodeinfo.setContextpath(protocolConfig.get("contextpath") != null ? protocolConfig.getString("contextpath")
                : "");
        return nodeinfo;
    }
}
