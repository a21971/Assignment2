package com.demo;

import org.apache.log4j.Category;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.Priority;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

class MemAppenderTest {

    @Test
    void test_memAppender_singleton_pattern(){
        MemAppender instance1 = MemAppender.getInstance(100, null, new PatternLayout());
        MemAppender instance2 = MemAppender.getInstance(200, null, new PatternLayout());
        Assertions.assertEquals(instance1.hashCode(), instance2.hashCode());
    }

    @Test
    void test_memAppender_with_null_layout(){
        MemAppender instance1 = MemAppender.getInstance(100, null,null);
        Assertions.assertNotEquals(null, instance1);
    }

    @Test
    void test_append(){
        MemAppender instance1 = getNewInstance();
        for(int i=1; i<=11; i++){
            LoggingEvent event = new LoggingEvent("string", Category.getInstance(MemAppender.class), Priority.INFO, "Object", new RuntimeException());
            instance1.append(event);
        }
        Assertions.assertEquals(1, instance1.getCurrentLogs().size());
    }

    @Test
    void test_getCurrentLogs(){
        MemAppender instance1 = getNewInstance();
        for(int i=1; i<=10; i++){
            LoggingEvent event = new LoggingEvent("string", Category.getInstance(MemAppender.class), Priority.INFO, "Object", new RuntimeException());
            instance1.append(event);
        }
        List<LoggingEvent> events = instance1.getCurrentLogs();
        Assertions.assertThrows(UnsupportedOperationException.class, ()->{
            events.remove(0);
        });
    }

    @Test
    void test_getEventStrings(){
        MemAppender instance1 = getNewInstance();
        for(int i=1; i<=10; i++){
            LoggingEvent event = new LoggingEvent("string", Category.getInstance(MemAppender.class), Priority.INFO, "Object", new RuntimeException());
            instance1.append(event);
        }
        List<String> events = instance1.getEventStrings();
        Assertions.assertThrows(UnsupportedOperationException.class, ()->{
            events.remove(0);
        });
    }

    @Test
    void test_printLogs(){
        MemAppender instance1 = getNewInstance();
        for(int i=1; i<=10; i++){
            LoggingEvent event = new LoggingEvent("string", Category.getInstance(MemAppender.class), Priority.INFO, "Object", new RuntimeException());
            instance1.append(event);
        }
        instance1.printLogs();
        Assertions.assertEquals(0, instance1.getCurrentLogs().size());
    }

    @Test
    void test_getDiscardedLogCount(){
        MemAppender instance1 = getNewInstance();
        for(int i=1; i<=15; i++){
            LoggingEvent event = new LoggingEvent("string", Category.getInstance(MemAppender.class), Priority.INFO, "Object", new RuntimeException());
            instance1.append(event);
        }
        Assertions.assertEquals(10, instance1.getDiscardedLogCount());
    }

    static MemAppender getNewInstance(){
        Constructor[] constructors = MemAppender.class.getDeclaredConstructors();
        MemAppender instance1 = null;
        try{
            for (Constructor constructor : constructors) {
                // Below code will destroy the singleton pattern
                constructor.setAccessible(true);
                instance1 = (MemAppender) constructor.newInstance(10);
                instance1.loggingEvents =  new ArrayList<>();
                instance1.setLayout(new PatternLayout());
                break;
            }
        }catch (Exception e){

        }
        return instance1;
    }
}
