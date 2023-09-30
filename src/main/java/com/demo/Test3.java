package com.demo;

import org.apache.log4j.*;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.apache.velocity.runtime.resource.util.StringResourceRepository;

import java.io.StringWriter;

public class Test3 {

    public static void main(String[] args) {
        velocityWithStringTemplateExample();
    }

    private static void velocityWithStringTemplateExample() {
        // Initialize the engine.
        VelocityEngine engine = new VelocityEngine();
        engine.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, "org.apache.velocity.runtime.log.Log4JLogChute");
        engine.setProperty("runtime.log.logsystem.log4j.logger", ConsoleAppender.class.getName());
        engine.setProperty(Velocity.RESOURCE_LOADER, "string");
        engine.addProperty("string.resource.loader.class", StringResourceLoader.class.getName());
        engine.addProperty("string.resource.loader.repository.static", "false");
        engine.init();

        // Initialize my template repository. You can replace the "Hello $w" with your String.
        StringResourceRepository repo = (StringResourceRepository) engine.getApplicationAttribute(StringResourceLoader.REPOSITORY_NAME_DEFAULT);
        repo.putStringResource("woogie2", "$c $d $p: $m $n");

        /**
         * c (category)
         * d (date using the default toString() representation)
         * m (message)
         * p (priority)
         * t (thread)
         * n (line separator)
         */
        // Set parameters for my template.
        VelocityContext context = new VelocityContext();
        context.put("c", "category");
        context.put("d", "date");
        context.put("m", "message");
        context.put("p", "priority");
        context.put("t", "thread!");
        context.put("n", "/n");

        // Get and merge the template with my parameters.
        Template template = engine.getTemplate("woogie2");
        StringWriter writer = new StringWriter();
        template.merge(context, writer);

        // Show the result.
        System.out.println(writer.toString());
    }
}
