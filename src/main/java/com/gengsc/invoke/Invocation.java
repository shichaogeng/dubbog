package com.gengsc.invoke;

import com.gengsc.configbean.ReferenceBean;

import java.lang.reflect.Method;

/**
 * @Description
 * @Author shichaogeng
 * @Create 2018-01-02 22:17
 */
public class Invocation {

    private Method method;

    private Object[] args;

    private ReferenceBean referenceBean;

    private Invoke invoke;

    public Invoke getInvoke() {
        return invoke;
    }

    public void setInvoke(Invoke invoke) {
        this.invoke = invoke;
    }

    public ReferenceBean getReferenceBean() {
        return referenceBean;
    }

    public void setReferenceBean(ReferenceBean referenceBean) {
        this.referenceBean = referenceBean;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
