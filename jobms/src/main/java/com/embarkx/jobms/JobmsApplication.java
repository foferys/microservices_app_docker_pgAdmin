package com.embarkx.jobms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

//annotation @EnableFeignClients che serve per comunicare  a spring che usiamo openFeign che serve per usare
//il metodo openFeign per far comunicare due microservizi
@EnableFeignClients
@SpringBootApplication
public class JobmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobmsApplication.class, args);
	}

}
