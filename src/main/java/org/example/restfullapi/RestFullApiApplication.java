package org.example.restfullapi;

import java.lang.System.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


//является входной точкой для разворачивания SpringBoot приложения
@SpringBootApplication
@RestController
public class RestFullApiApplication {

	public static void main(String[] args) {
		Logger logger = System.getLogger(RestFullApiApplication.class.getName());
		logger.log( Logger.Level.INFO,"hello {0}", "Anastasya" );


		SpringApplication.run(RestFullApiApplication.class, args);
	}

	@GetMapping(path = "hello")
	public String hello() {
		return "Hello World";
	}

}
