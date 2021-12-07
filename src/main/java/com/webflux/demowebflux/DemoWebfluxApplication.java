package com.webflux.demowebflux;

import com.webflux.demowebflux.file.send.SendFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoWebfluxApplication implements CommandLineRunner {

	@Autowired
	SendFile sendFile;

	public static void main(String[] args) {
		SpringApplication.run(DemoWebfluxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
			sendFile.sendFileWebServiceAPI();
	}
}
