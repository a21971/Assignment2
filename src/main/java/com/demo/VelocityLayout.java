package com.demo;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.apache.velocity.runtime.resource.util.StringResourceRepository;

import java.io.StringWriter;
import java.util.Date;

public class VelocityLayout extends PatternLayout {

    static final String TEMPLATE_NAME = "myCustomTemplate";
    static final String LOG_PATTERN = "[$d] $t $c $p : $m";
    static VelocityEngine velocityEngine;
    static {
        // Initialize the engine
        velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(Velocity.RESOURCE_LOADER, "string");
        velocityEngine.setProperty("resource.loader.string.class", StringResourceLoader.class.getName());
        velocityEngine.setProperty("resource.loader.string.cache", true);
        velocityEngine.setProperty("resource.loader.string.modification_check_interval", 60);
        velocityEngine.init();

        // Add template to repository
        StringResourceRepository repository = StringResourceLoader.getRepository();
        repository.putStringResource(TEMPLATE_NAME, LOG_PATTERN);
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
        // Set the parameters
        VelocityContext context = new VelocityContext();
        context.put("d", new Date(event.getTimeStamp()).toString());
        context.put("t", event.getThreadName());
        context.put("c", event.getLevel().toString());
        context.put("p", event.getLogger().getName());
        context.put("m", event.getRenderedMessage());

        // Process the template
        StringWriter writer = new StringWriter();
        velocityEngine.getTemplate(TEMPLATE_NAME).merge( context, writer );

        return writer.toString();
    }
}
