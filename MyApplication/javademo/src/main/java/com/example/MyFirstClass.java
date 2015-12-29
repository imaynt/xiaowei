package com.example;

import com.example.subscriberandrpublisher.MyTopic;
import com.example.subscriberandrpublisher.MyTopicSubscriber;
import com.example.subscriberandrpublisher.observer;

public class MyFirstClass {
    public static void main(String[] args)
    {
//        System.out.print("hello");
        MyTopic topic = new MyTopic();
        MyTopic topic1 = new MyTopic();
        observer obj1 = new MyTopicSubscriber("obj1");
        observer obj2 = new MyTopicSubscriber("obj2");
        observer obj3 = new MyTopicSubscriber("obj3");

        topic.register(obj1);
        topic.register(obj2);
        topic1.register(obj3);
        obj1.setSubject(topic);
        obj2.setSubject(topic);
        obj3.setSubject(topic1);

        obj1.update();
//        topic.unregister(obj1);
        topic.postMessage("news");
        topic1.postMessage("hello");


    }
}
