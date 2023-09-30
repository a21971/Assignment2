package com.demo;

import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class Test4 {

    static Logger logger = Logger.getLogger(Test4.class);

    public static void main(String[] args) {

        Layout velocityLayout = new VelocityLayout();
        Layout patternLayout = new PatternLayout("%d : %m%n");

        MyAppender myAppender = new MyAppender();
        myAppender.setLayout(velocityLayout);

        MemAppender memAppender = MemAppender.getInstance(100, velocityLayout);

        Logger.getRootLogger().addAppender(memAppender);

        for (int i=0; i<9900; i++){
            logger.info(i);
        }

        myAppender.eventsList.stream().forEach(e-> System.out.print(e));

        memAppender.printLogs();
        System.out.println(memAppender.getDiscardedLogCount());
    }
}
