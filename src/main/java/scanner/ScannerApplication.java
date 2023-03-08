package scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.cloud.openfeign.EnableFeignClients;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableJpaAuditing
@EnableSwagger2
@EnableFeignClients(basePackages = {"scanner.common.client"})
@SpringBootApplication
public class ScannerApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ScannerApplication.class);
		app.run(args);
	}
}
