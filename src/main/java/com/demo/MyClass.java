package com.demo;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.log.LogSystem;

public class MyClass implements LogSystem {
    public MyClass(){
        try {
            /*
             *  register this class as a logger
             */
            Velocity.setProperty(Velocity.RUNTIME_LOG_LOGSYSTEM_CLASS, this );
            Velocity.init();
        } catch (Exception e) {
            /*
             *  do something
             */
        }
    }

    /**
     *  This init() will be invoked once by the LogManager
     *  to give you current RuntimeServices intance
     */
    public void init( RuntimeServices rsvc ) {
        // do nothing
    }

    /**
     *  This is the method that you implement for Velocity to call
     *  with log messages.
     */
    public void logVelocityMessage(int level, String message) {
        /*  do something useful */
        System.out.println();
    }
}
