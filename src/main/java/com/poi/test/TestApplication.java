package com.poi.test;

import com.poi.test.excel.upload;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestApplication {

	public static void main(String[] args) {

		SpringApplication.run(TestApplication.class, args);

		upload upload = new upload();

		upload.readFile();

	}



}
