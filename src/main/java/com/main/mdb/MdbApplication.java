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

    @Bean
    public CommandLineRunner demo(UserRepository repository) {
        return (args) -> {
            // save a few customers
            try{
                repository.save(new User("p3150134@aueb.gr", "12345"));
                repository.save(new User("vagelisp.97@gmail.com", "12345"));
                repository.save(new User("vagelisp.97@outlook.com", "12345"));
                repository.save(new User("vagelisp.97@icloud.com", "12345"));
            }catch (Exception e) {
                System.out.println(e);
            }

            // fetch all customers
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (User customer : repository.findAll()) {
                log.info(customer.toString());
            }
            log.info("");


            // fetch customers by last name
            log.info("Customer found with findByLastName('Bauer'):");
            log.info("--------------------------------------------");
            log.info(repository.findByEmail("vagelisp.97@gmail.com").toString());
            // for (Customer bauer : repository.findByLastName("Bauer")) {
            //  log.info(bauer.toString());
            // }
            log.info("");
        };
    }

}
