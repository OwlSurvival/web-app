package ru.vsu.zlata.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@EnableAutoConfiguration
//@ComponentScan({"com.spingboot.examples"})
//@Configuration
//@EnableTransactionManagement - Do not need
@ImportResource(locations = {"classpath:cntx/beans.xml"})
@SpringBootApplication
public class StartApp {

    private static final Logger LOG = LoggerFactory.getLogger(StartApp.class);

    public static void main(String[] args) throws Exception {
    	ApplicationContext context = SpringApplication.run(StartApp.class, args);
        LOG.info("Application webapp has been started!");
    }
}