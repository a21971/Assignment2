package com.demo;

import org.apache.log4j.Layout;
import org.apache.log4j.Logger;

public class Test4 {

    static Logger logger = Logger.getLogger(Test4.class);

    public static void main(String[] args) {

        Layout velocityLayout = new VelocityLayout();

        MyAppender myAppender = new MyAppender();
        myAppender.setLayout(velocityLayout);

        Logger.getRootLogger().addAppender(myAppender);

        for (int i=0; i<10; i++){
            logger.info(i);
        }

        myAppender.eventsList.stream().forEach(e-> System.out.print(e));
    }
}
