package com.demo;

import org.apache.log4j.*;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class StressTest {

    private static int LOAD_DATA = 10000;

    @Test
    void test_memAppender_with_ArrayList() throws Exception{
        MemAppender instance1 = MemAppenderTest.getNewInstance();
        for(int i=1; i<=LOAD_DATA; i++){
            LoggingEvent event = new LoggingEvent("string", Category.getInstance(MemAppender.class), Priority.INFO, "Object", new RuntimeException());
            instance1.append(event);
        }
        Assertions.assertEquals(10, instance1.getCurrentLogs().size());
    }

    @Test
    void test_memAppender_with_LinkedList() throws Exception{
        MemAppender instance1 = MemAppenderTest.getNewInstance();
        for(int i=1; i<=LOAD_DATA; i++){
            LoggingEvent event = new LoggingEvent("string", Category.getInstance(MemAppender.class), Priority.INFO, "Object", new RuntimeException());
            instance1.append(event);
        }
        Assertions.assertEquals(10, instance1.getCurrentLogs().size());
    }

    @Test
    @Disabled //disabled temporarily to avoid console logging
    void test_consoleAppender_with_velocityLayout() throws Exception{

        Logger logger = Logger.getLogger(StressTest.class);

        Layout velocityLayout = new VelocityLayout();

        ConsoleAppender consoleAppender = new ConsoleAppender();
        consoleAppender.setLayout(velocityLayout);
        consoleAppender.setThreshold(Level.ALL);
        consoleAppender.activateOptions();

        Logger.getRootLogger().addAppender(consoleAppender);

        for(int i=1; i<=LOAD_DATA; i++){
            logger.info("Hello World");
        }
    }

    @Test
    @Disabled //disabled temporarily to avoid console logging
    void test_consoleAppender_with_patternLayout() throws Exception{

        Logger logger = Logger.getLogger(StressTest.class);

        Layout patternLayout = new PatternLayout();

        ConsoleAppender consoleAppender = new ConsoleAppender();
        consoleAppender.setLayout(patternLayout);
        consoleAppender.setThreshold(Level.ALL);
        consoleAppender.activateOptions();

        Logger.getRootLogger().addAppender(consoleAppender);

        for(int i=1; i<=LOAD_DATA; i++){
            logger.info("Hello World");
        }
    }
}
