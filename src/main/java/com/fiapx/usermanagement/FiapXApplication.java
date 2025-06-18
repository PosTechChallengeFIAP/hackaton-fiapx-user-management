package com.fiapx.usermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class FiapXApplication {

	public static void main(String[] args) {
		Environment env = SpringApplication.run(FiapXApplication.class, args).getEnvironment();
	}

}
