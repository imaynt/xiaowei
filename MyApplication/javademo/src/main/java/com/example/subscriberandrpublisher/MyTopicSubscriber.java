package com.example.subscriberandrpublisher;

/**
 * Created by liwei5 on 2015/12/21.
 */
public class MyTopicSubscriber implements observer {
    private String name;
    private subject topic;
    public MyTopicSubscriber(String nm)
    {
        this.name = nm;
    }



    @Override
    public void update() {
        String msg = (String) topic.getUpdate(this);
        if(null == msg)
        {
            System.out.println(name+":: NO new message");
        }else
        {
            System.out.println(name+"::Consuming message::"+msg);
        }
    }

    @Override
    public void setSubject(subject sub) {
        this.topic = sub;
    }
}
