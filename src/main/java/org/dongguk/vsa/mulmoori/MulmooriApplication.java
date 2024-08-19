package org.dongguk.vsa.mulmoori;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class MulmooriApplication {

    public static void main(String[] args) {
        SpringApplication.run(MulmooriApplication.class, args);
    }

}
