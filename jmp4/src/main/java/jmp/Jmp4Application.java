package jmp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"jmp.services", "jmp.resources", "jmp.dao"})
public class Jmp4Application {

	public static void main(String[] args) {
		SpringApplication.run(Jmp4Application.class, args);
	}
}
