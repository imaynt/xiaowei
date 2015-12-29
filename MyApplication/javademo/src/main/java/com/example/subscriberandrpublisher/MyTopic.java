package com.example.subscriberandrpublisher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liwei5 on 2015/12/21.
 */
public class MyTopic implements  subject {
    private List<observer> observers;
    private String message;
    private boolean changed;
    private final Object MUTEX = new Object();
    public MyTopic()
    {
        this.observers = new ArrayList<>();
    }


    @Override
    public void unregister(observer obj) {
        observers.remove(obj);
    }

    @Override
    public void register(observer obj) {
        if(obj==null)throw new NullPointerException("null observer");
        if(!(observers.contains(obj)))observers.add(obj);
    }

    @Override
    public void notifyObservers() {
        List<observer> observersLocal = null;
        //synchronization is used to make sure any observer registered after message is received is not notified
        synchronized (MUTEX) {
            if (!changed)
            return;
            observersLocal = new ArrayList<>(this.observers);
            this.changed=false;
        }
        for (observer obj : observersLocal) {
            obj.update();
        }

    }

    @Override
    public Object getUpdate(observer obj) {
        return this.message;
    }

    public void postMessage(String msg)
    {
        System.out.println("message mytopic:"+msg);
        this.message = msg;
        this.changed = true;
        notifyObservers();
    }
}
