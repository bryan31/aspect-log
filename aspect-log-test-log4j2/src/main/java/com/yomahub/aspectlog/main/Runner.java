package com.yomahub.aspectlog.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Runner {

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
