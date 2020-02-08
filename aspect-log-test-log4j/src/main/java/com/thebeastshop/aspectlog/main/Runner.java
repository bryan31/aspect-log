package com.thebeastshop.aspectlog.main;

import com.thebeastshop.aspectlog.enhance.AspectLogEnhance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class Runner {

    static {AspectLogEnhance.enhance();}

    public static void main(String[] args) {
        try {
            SpringApplication.run(Runner.class, args);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                Thread.sleep(60000);
            } catch (Throwable e) {
            	e.printStackTrace();
            }
        }
    }
}
