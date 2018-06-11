package pl.asc.claimsservice;

import de.triology.cb.CommandBus;
import de.triology.cb.spring.Registry;
import de.triology.cb.spring.SpringCommandBus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class ClaimsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClaimsServiceApplication.class, args);
	}

	@Bean
	public Registry registry(ApplicationContext applicationContext) {
		return new Registry(applicationContext);
	}

	@Bean
	public CommandBus commandBus(Registry registry) {
		return new SpringCommandBus(registry);
	}

	@Bean
	public CommandLineRunner initData(CommandBus bus) {
		return (String ... args) -> {
			log.info("Data init starting ...");
		};
	}
}
