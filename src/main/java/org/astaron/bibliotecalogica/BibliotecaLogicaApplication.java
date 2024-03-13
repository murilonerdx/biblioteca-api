package org.astaron.bibliotecalogica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class BibliotecaLogicaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BibliotecaLogicaApplication.class, args);
	}

}
