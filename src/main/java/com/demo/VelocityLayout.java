package com.demo;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;

import java.util.Date;

public class VelocityLayout extends PatternLayout {

    public VelocityLayout(){
        super();
    }

    public VelocityLayout(String pattern){
        super(pattern);
    }

    /*
    c (category)
    d (date using the default toString() representation)
    m (message)
    p (priority)
    t (thread)
    n (line separator)

     */
    @Override
    public String format(LoggingEvent event) {
        String pattern = "[%s] %s %s %s: %s %n";
        return String.format(pattern, new Date(event.getTimeStamp()).toString(), event.getThreadName(), event.getLevel().toString(), event.getLogger().getName(), event.getRenderedMessage());
    }
}
