package com.demo;

import org.apache.log4j.*;
import org.apache.log4j.spi.LoggingEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class MemAppender extends AppenderSkeleton {

    private Lock lock = new ReentrantLock();
    protected List<LoggingEvent> loggingEvents;
    private int maxSize;
    private int currentSize = 0;
    private long discardedLogCount = 0;
    private static MemAppender instance;

    //maxSize customizable for user
    private MemAppender(int maxSize){
        this.maxSize = maxSize;
    }

    /*
       In memory data holder type and layout are injectable (DI)
     */
    public static MemAppender getInstance(int maxSize, List<LoggingEvent> logHolderDataType, Layout layout){
        if(null == instance){
            if(null == layout) {
                layout = new PatternLayout(PatternLayout.DEFAULT_CONVERSION_PATTERN);
            }
            instance = new MemAppender(maxSize);
            instance.setLayout(layout);
            if(null == logHolderDataType){
                instance.loggingEvents = new ArrayList<>();
            }else{
                instance.loggingEvents = logHolderDataType;
            }
            return instance;
        }
        return instance;
    }

    @Override
    protected void append(LoggingEvent event) {
        try{
            lock.lock();
            this.layout.format(event); //Velocity for logs formatting
            if (currentSize >= maxSize) {
                discardedLogCount = discardedLogCount + loggingEvents.size();
                loggingEvents.clear();
                currentSize = 0;
            }
            loggingEvents.add(event);
            currentSize++;
        }finally {
            lock.unlock();
        }
    }

    @Override
    public void close() {
        //nothing to do
    }

    @Override
    public boolean requiresLayout() {
        return false;
    }

    public List<LoggingEvent> getCurrentLogs(){
        try{
            lock.lock();
            return Collections.unmodifiableList(loggingEvents);
        } finally {
            lock.unlock();
        }
    }

    public List<String> getEventStrings(){
        try{
            lock.lock();
            return loggingEvents.stream().map(LoggingEvent::getRenderedMessage).collect(Collectors.toUnmodifiableList());
        } finally {
            lock.unlock();
        }
    }

    public void printLogs(){
        try{
            lock.lock();
            List<String> logs = loggingEvents.stream().map(LoggingEvent::getRenderedMessage).collect(Collectors.toList());
            logs.forEach(System.out::println);
            loggingEvents.clear();
        } finally {
            lock.unlock();
        }
    }

    public long getDiscardedLogCount(){
        try{
            lock.lock();
            return discardedLogCount;
        } finally {
            lock.unlock();
        }
    }

}
