package com.example.libbus;

import java.lang.reflect.Method;

/**
 * 事件订阅者相关信息
 */
public class Subscriber {

    private Method method; //订阅方法名

    private ThreadModel threadModel; // 方法执行线程

    private Class<?> paramsType; //参数类型

    public Subscriber(Method methodName, ThreadModel threadModel, Class<?> paramsType) {
        this.method = methodName;
        this.threadModel = threadModel;
        this.paramsType = paramsType;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method methodName) {
        this.method = methodName;
    }

    public ThreadModel getThreadModel() {
        return threadModel;
    }

    public void setThreadModel(ThreadModel threadModel) {
        this.threadModel = threadModel;
    }

    public Class<?> getParamsType() {
        return paramsType;
    }

    public void setParamsType(Class<?> paramsType) {
        this.paramsType = paramsType;
    }
}
