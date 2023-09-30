package com.demo1;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.helpers.PatternParser;

public class MyPatternLayout extends PatternLayout {

    @Override
    protected PatternParser createPatternParser(String pattern) {
        return super.createPatternParser(pattern);
    }
}
