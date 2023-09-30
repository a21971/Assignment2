package com.demo;

import org.apache.log4j.*;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;

import java.io.IOException;

public class Test2 {

    public static String CATEGORY_NAME = "velexample";

    public static void main(String[] args) throws IOException {
        /*
         *  configure log4j to log to console
         */

        BasicConfigurator.configure();
        Logger logger = Logger.getLogger(Test2.class.getName());
        logger.addAppender(new FileAppender());
        logger.info("Hello world!");

        Category log = Category.getInstance(Test2.class.getName());

        log.info("Hello from Log4jCategoryExample - ready to start velocity");

        /*
         *  now create a new VelocityEngine instance, and
         *  configure it to use the category
         */

        VelocityEngine ve = new VelocityEngine();

        ve.setProperty( RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, "org.apache.velocity.runtime.log.Log4JLogChute" );
        ve.setProperty("runtime.log.logsystem.log4j.LOGGER", Test2.class.getName());
        ve.init();

        log.info("this should follow the initialization output from velocity");
    }
}
