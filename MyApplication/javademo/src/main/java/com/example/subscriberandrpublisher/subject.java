package com.example.subscriberandrpublisher;

/**
 * Created by liwei5 on 2015/12/21.
 */
public interface subject {
    public void unregister(observer obj);
    public void register(observer obj);
    public void notifyObservers();
    public Object getUpdate(observer obj);


}
