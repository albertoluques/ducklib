package com.oracleone.ducklib;

import com.oracleone.ducklib.model.BookListData;
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
		var consumeAPI = new ConsumeAPI();
		var json = consumeAPI.obtainData("https://gutendex.com/books/?search=pride");
		System.out.println(json);

		DataConversion conversor = new DataConversion();
		var data = conversor.obtainData(json, BookListData.class);
		System.out.println(data);
	}
}
