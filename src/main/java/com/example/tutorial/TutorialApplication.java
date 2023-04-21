package com.example.tutorial;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

// import spring-boot-starter-security package will allow the spring boot project homepage redirect to the /login page
// and it will prevent you to access all of API
// so to avoid this, we will need to remove the security package from pom.xml
// or we can add "exclude = { SecurityAutoConfiguration.class }"
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableMongock
public class TutorialApplication {

	public static void main(String[] args) {
		SpringApplication.run(TutorialApplication.class, args);
	}

}
