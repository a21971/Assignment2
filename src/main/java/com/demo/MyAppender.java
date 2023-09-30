package com.demo;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;

import java.util.ArrayList;

public class MyAppender extends AppenderSkeleton {

    ArrayList<String> eventsList = new ArrayList();

    MyAppender(){}

    MyAppender(Layout layout){
        super.layout = layout;
    }

    @Override
    protected void append(LoggingEvent event) {
        eventsList.add(this.layout.format(event));
    }

    public void close() {
    }

    public boolean requiresLayout() {
        return false;
    }

}
