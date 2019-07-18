package com.example.libbus;

import android.os.Handler;
import android.os.Looper;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * bus核心类
 */
public class EventBus {

    //存储订阅类及方法参数
    private Map<Object,List<Subscriber>> subscribeMethod;
    //线程调度
    private Handler mHandler;
    //线程池
    private ExecutorService executorService;

    private static EventBus myBus;

    public static EventBus getInstance(){
        if (myBus==null){
            synchronized (EventBus.class){
                if (myBus==null){
                    myBus = new EventBus();
                }
            }
        }
        return myBus;
    }

    private EventBus(){
        subscribeMethod = new HashMap<>();
        mHandler = new Handler(Looper.getMainLooper());
        executorService = Executors.newCachedThreadPool();
    }

    public void register(Object obj){
        if (obj==null){
            return;
        }
        Class<?> mclazz = obj.getClass();
        //获取本类所有方法
        Method[] methods = mclazz.getDeclaredMethods();
        List<Subscriber> methods1 = new ArrayList<>();
        for (Method method : methods){
            //获取带有我们Subscribe注解的方法
            Subscribe subscribe = method.getAnnotation(Subscribe.class);
            if (subscribe==null){
                continue;
            }
            //获取参数类型集合
            Class<?>[] typeVariable = method.getParameterTypes();
            if (typeVariable.length!=1){
                continue;
            }
            ThreadModel threadModel = subscribe.thread();
            Subscriber busMethod = new Subscriber(method,threadModel,typeVariable[0]);
            methods1.add(busMethod);
        }
        if (methods1.size()>0){
            subscribeMethod.put(obj,methods1);
        }
    }

    public void postEvent(Object eventParam){
        Set<Object> set = subscribeMethod.keySet();
        Iterator<Object> iterable =set.iterator();
        while (iterable.hasNext()){
            Object obj = iterable.next();
            List<Subscriber> busMethodList = subscribeMethod.get(obj);
            for (Subscriber busMethod : busMethodList){
                if(busMethod.getParamsType() == eventParam.getClass()){
                    invoke(obj,busMethod,eventParam);
                }
            }
        }
    }

    private void invoke(final Object obj, final Subscriber busMethod, final Object eventParam){
        switch (busMethod.getThreadModel()){
            case MAIN:
                //通过handler调度到主线程
                mHandler.post(new EventRunable(busMethod, obj, eventParam));
                break;
            default:
                //交由线程池处理
                executorService.execute(new EventRunable(busMethod, obj, eventParam));
                break;
        }
    }

    public void unRegister(Object object){
        if (subscribeMethod.containsKey(object)){
            subscribeMethod.remove(object);
        }
    }
}
