package com.oracleone.ducklib;

import com.oracleone.ducklib.model.BookData;
import com.oracleone.ducklib.principal.Principal;
import com.oracleone.ducklib.service.ConsumeAPI;
import com.oracleone.ducklib.service.DataConversion;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DucklibApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DucklibApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.showMenu();
	}
}
