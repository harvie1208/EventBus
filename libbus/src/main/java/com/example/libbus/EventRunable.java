package com.example.libbus;

import java.lang.reflect.InvocationTargetException;

/**
 * 事件执行线程体
 */
public class EventRunable implements Runnable {

    private Subscriber busMethod;
    private Object obj;
    private Object eventParam;

    public EventRunable(Subscriber busMethod, Object obj, Object eventParam) {
        this.busMethod = busMethod;
        this.obj = obj;
        this.eventParam = eventParam;
    }

    @Override
    public void run() {
        try {
            busMethod.getMethod().invoke(obj,eventParam);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
