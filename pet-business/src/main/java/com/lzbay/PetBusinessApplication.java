package com.lzbay;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

@SpringBootApplication
//@MapperScan(value)
@Slf4j
public class PetBusinessApplication {

    public static final String COMPONENT_SCAN = "com.lzbay";

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication application = new SpringApplication(PetBusinessApplication.class);
        application.setApplicationStartup(new BufferingApplicationStartup(2048));
        ConfigurableApplicationContext applicationContext = application.run(args);

        ConfigurableEnvironment env = applicationContext.getEnvironment();
        String contextPath = Objects.equals(env.getProperty("server.servlet.context-path"), "/") ?
            "" :
            env.getProperty("server.servlet.context-path");
        log.info(
            "\n----------------------------------------------------------\n\t" +
                "Access URLs:\n\t" +
                "Local: \t\thttp://localhost:{}\n\t" +
                "External: \thttp://{}:{}\n\t" +
                "Local Swagger UI: \thttp://localhost:{}{}/doc.html\n\t" +
                "Swagger UI: \t\thttp://{}:{}{}/doc.html\n\t" +
                "Application '{}' started successfully! \n" +
                "----------------------------------------------------------",
            env.getProperty("server.port"),
            InetAddress.getLocalHost().getHostAddress(),
            env.getProperty("server.port"),
            env.getProperty("server.port"),
            contextPath,
            InetAddress.getLocalHost().getHostAddress(),
            env.getProperty("server.port"),
            contextPath,
            env.getProperty("ruoyi.name"));
    }

}
