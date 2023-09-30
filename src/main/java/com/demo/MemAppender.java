package com.demo;

import org.apache.log4j.*;
import org.apache.log4j.spi.ErrorHandler;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class MemAppender extends AppenderSkeleton {

    private Lock lock = new ReentrantLock();

    private List<LoggingEvent> loggingEvents = new ArrayList<>();
    private List<String> loggingEventsString = new ArrayList<>();

    private int maxSize;

    private int currentSize = 0;

    private long discardedLogCount = 0;

    private static MemAppender instance;

    private MemAppender(int maxSize){
        this.maxSize = maxSize;
    }

    public static MemAppender getInstance(int maxSize, Layout layout){
        if(null == instance){
            if(null == layout) {
                System.out.println("Creating default pattern");
                layout = new PatternLayout(PatternLayout.DEFAULT_CONVERSION_PATTERN);
            }
            instance = new MemAppender(maxSize);
            instance.setLayout(layout);
            return instance;
        }
        return instance;
    }

    @Override
    protected void append(LoggingEvent event) {
        try{
            lock.lock();
            String data = this.layout.format(event);
            if(currentSize <= maxSize){
                loggingEvents.add(event);
                loggingEventsString.add(data);
                currentSize++;
            }else{
                discardedLogCount = discardedLogCount + loggingEvents.size();
                loggingEvents.clear();
                loggingEventsString.clear();
                currentSize = 0;
                loggingEvents.add(event);
                loggingEventsString.add(data);
                currentSize++;
            }
        }finally {
            lock.unlock();
        }
    }

    @Override
    public void close() {

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
            return Collections.unmodifiableList(loggingEventsString.stream().collect(Collectors.toList()));
        } finally {
            lock.unlock();
        }
    }

    public void printLogs(){
        try{
            lock.lock();
            loggingEventsString.stream().forEach(e-> System.out.println(e));
            loggingEvents.clear();
            loggingEventsString.clear();
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
