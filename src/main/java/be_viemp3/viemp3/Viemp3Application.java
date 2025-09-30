package be_viemp3.viemp3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Viemp3Application {

	public static void main(String[] args) {
		SpringApplication.run(Viemp3Application.class, args);
	}

}
