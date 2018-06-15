package pl.asc.claimsservice;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import pl.asc.claimsservice.commands.registerpolicy.RegisterPolicyCommand;
import pl.asc.claimsservice.commands.registerpolicy.dto.*;
import pl.asc.claimsservice.shared.cqs.CommandBus;
import pl.asc.claimsservice.shared.cqs.Registry;
import pl.asc.claimsservice.shared.cqs.SpringCommandBus;

import java.math.BigDecimal;
import java.time.LocalDate;

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

			PolicyVersionDto pv = new PolicyVersionDto(
					"POL-101010",
					"PAKIET_GOLD",
					new PersonDto("Jan","Nowak","11111111116"),
					"9000000009",
					LocalDate.of(2018,1,1),
					LocalDate.of(2018,12,31),
					1L,
					LocalDate.of(2018,1,1),
					LocalDate.of(2018,12,31),
					Lists.newArrayList(
							new CoverDto("KONS",
									Lists.newArrayList(
											new ServiceDto(
													"KONS_INTERNISTA",
													new CoPaymentDto(new BigDecimal("0.25"), null),
													new LimitDto("POLICY_YEAR", null, new BigDecimal("100"))),
											new ServiceDto(
													"KONS_PEDIATRA",
													new CoPaymentDto(null, new BigDecimal("10")),
													new LimitDto("POLICY_YEAR", new BigDecimal("20"), new BigDecimal("100")))
									))
					)
			);

			bus.executeCommand(new RegisterPolicyCommand(pv));

			log.info("Data init succeeded");
		};
	}
}
