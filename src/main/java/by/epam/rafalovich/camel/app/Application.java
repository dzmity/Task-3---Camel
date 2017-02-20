package by.epam.rafalovich.camel.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Application class represents start point for creation spring.context.
 */
public class Application {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {

        LOG.info("*****Starting route operations.");
        try{

        new ClassPathXmlApplicationContext("classpath:beans.xml");
        Thread.sleep(4000);

        } catch (InterruptedException e) {
            LOG.error("*****Exception in main method.", e);
        }
    }
}
