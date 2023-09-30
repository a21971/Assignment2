package com.demo1;

import com.demo.VelocityLayout;
import org.apache.log4j.*;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {

        Layout myPatternLayout = new MyPatternLayout();
        Layout patternLayout = new PatternLayout("%d : %m%n");
        Layout velocityLayout = new VelocityLayout();

        ConsoleAppender consoleAppender = new ConsoleAppender();
        consoleAppender.setLayout(velocityLayout);
        consoleAppender.setThreshold(Level.ALL);
        consoleAppender.activateOptions();

        Logger.getRootLogger().addAppender(consoleAppender);

        logger.info("Hello World");
        logger.debug("Hello World");
        logger.warn("Hello World");
        logger.error("Hello World");

    }
}
