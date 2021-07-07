package com.wipro.learning;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import com.wipro.reglogin.model.RegistrationRequest;
import com.wipro.reglogin.service.AuthService;

@SpringBootApplication
@EntityScan(basePackages = { "com.wipro.learning.domain", "com.wipro.reglogin.domain" })
@EnableJpaRepositories(basePackages = { "com.wipro.learning.repository", "com.wipro.reglogin.repository" })
@ComponentScan(basePackages = { "com.wipro.learning.*", "com.wipro.reglogin.*" })
public class LearningServiceApplication {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private AuthService authService;

	public static void main(String[] args) {
		SpringApplication.run(LearningServiceApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void loadData() {
		ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false, "UTF-8",
				new ClassPathResource("data.sql"));
		resourceDatabasePopulator.execute(dataSource);

		// Load Admins
		RegistrationRequest regRequest = new RegistrationRequest();
		regRequest.setEmail("naveen@gmail.com");
		regRequest.setPassword("naveen");
		regRequest.setUsername("navedev");

		authService.registerUser(regRequest, true);
	}

}
