package com.gengsc.http.servlet;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gengsc.configbean.ServiceBean;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Method;

/**
 * @Description
 * 提供者接受请求servlet
 * @Author shichaogeng
 * @Create 2018-01-05 11:40
 */
public class DispatcherServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        JSONObject requestparam = httpProcess(req, resp);

        //要从远程的生产者的spring容器中拿到对应的serviceid实例
        String serviceId = requestparam.getString("serviceId");
        String methodName = requestparam.getString("methodName");
        JSONArray paramTypes = requestparam.getJSONArray("paramTypes");
        //这个对应的方法参数
        JSONArray methodParams = requestparam.getJSONArray("methodParams");
        //这个就是反射的参数
        Class<?>[] paramTypeObjs = null;
        if (paramTypes != null) {
            paramTypeObjs = new Class[paramTypes.size()];
            int i = 0;
            for (Object o : paramTypes) {
                paramTypeObjs[i++] = (Class<?>) o;
            }
        }

        Object[] methodParamObjs = null;
        if (methodParams != null) {
            methodParamObjs = new Object[methodParams.size()];
            int i = 0;
            for (Object o : methodParams) {
                methodParamObjs[i++] = o;
            }
        }

        //spring上下文
        ApplicationContext applicationContext = ServiceBean.applicationContext;
        Object serviceBean = applicationContext.getBean(serviceId);

        //这个方法的获取
        try {
            Method method = serviceBean.getClass().getMethod(methodName, paramTypeObjs);
            PrintWriter pw = resp.getWriter();
            if (method != null) {
                Object result = method.invoke(serviceBean, methodParamObjs);
                pw.write(JSONObject.toJSONString(result));
            } else {
                pw.write("-------------------------nosuchmethod-----------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JSONObject httpProcess(HttpServletRequest req,
                                         HttpServletResponse resp) throws IOException {
        StringBuffer sb = new StringBuffer();
        InputStream is = req.getInputStream();

        BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
        String s = "";
        while ((s = br.readLine()) != null) {
            sb.append(s);
        }
        if (sb.toString().length() <= 0) {
            return null;
        }
        else {
            return JSONObject.parseObject(sb.toString());
        }
    }

}
