// Copyright (c) 2024 Preponderous Software
// MIT License

package preponderous.viron;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import preponderous.viron.config.ServiceConfig;
import preponderous.viron.config.VironConfig;

@SpringBootApplication
@Import({VironConfig.class, ServiceConfig.class})
public class VironApplication {

	public static void main(String[] args) {
		SpringApplication.run(VironApplication.class, args);
	}

}
