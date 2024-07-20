package ALTERCAST.aLterMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ALterMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ALterMsApplication.class, args);
	}

}
