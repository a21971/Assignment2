package com.demo;

import org.apache.log4j.*;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Enumeration;

public class Test {

    static Logger logger = Logger.getLogger(Test.class);

    public static void main(String[] args) throws IOException {

        Layout velocityLayout = new VelocityLayout();
        MemAppender memAppender = MemAppender.getInstance(20, velocityLayout);
        Logger.getRootLogger().addAppender(memAppender);

        for (int i=0; i<10; i++){
            logger.info(i);
        }

        memAppender.printLogs();
    }
}
