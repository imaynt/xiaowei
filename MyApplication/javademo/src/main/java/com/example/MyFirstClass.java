package com.example;

import com.example.subscriberandrpublisher.MyTopic;
import com.example.subscriberandrpublisher.MyTopicSubscriber;
import com.example.subscriberandrpublisher.observer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
//        String x = "Wed Jan 13 13:12:54 CST 2016";
//        SimpleDateFormat sdf1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.US);
//        try
//        {
//            Date date=sdf1.parse(x);
//            long a = date.getTime();
//            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String sDate=sdf.format(date);
//            System.out.println(sDate+","+date);
//        }
//        catch (ParseException e)
//        {
//            e.printStackTrace();
//        }
        String email = "21233sabc@gamil.com";
        Pattern p = Pattern.compile("(\\w{3})(\\w+)(\\w{3})(@\\w+)");
        Matcher m = p.matcher(email);
        System.out.println(m.replaceAll("$1...$3$4"));

    }
}
