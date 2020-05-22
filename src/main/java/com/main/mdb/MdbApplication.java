package com.main.mdb;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = {"com.main.mdb"})
public class MdbApplication {

    private static final Logger log = LoggerFactory.getLogger(MdbApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MdbApplication.class);
    }
}
