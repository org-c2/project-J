/*
 * This source is not MIT, Please do not share it on internet
 */
package dcnet.tutorial.c01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * AppEntry
 *
 * @author Dcnet.F
 */
@SpringBootApplication
public class AppEntry {

    public static void main(String... args) {
        SpringApplication springApplication = new SpringApplication(AppEntry.class);
        springApplication.run(args);
    }

}
