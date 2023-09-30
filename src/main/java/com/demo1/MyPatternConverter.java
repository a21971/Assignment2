package com.demo1;

import org.apache.log4j.helpers.PatternConverter;
import org.apache.log4j.spi.LoggingEvent;

public class MyPatternConverter extends PatternConverter {

    @Override
    protected String convert(LoggingEvent evt) {
        // For simplicity, assume this information is retrieved from somewhere.
        return evt.getThreadName() + " User1";
    }
}
