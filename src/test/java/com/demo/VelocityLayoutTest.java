package com.demo;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

class VelocityLayoutTest {

    @Test
    void test_velocityLayout_with_MemAppender(){
        MemAppender instance1 = MemAppender.getInstance(100, new LinkedList(), new VelocityLayout());
        Logger.getRootLogger().addAppender(instance1);
        Logger.getLogger(VelocityLayoutTest.class).info("testing something==========");
        Assertions.assertEquals(1, instance1.getCurrentLogs().size());
    }
}
