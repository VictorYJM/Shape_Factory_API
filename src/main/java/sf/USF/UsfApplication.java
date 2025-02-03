package sf.USF;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "sf.USF.Repository")
@EntityScan(basePackages = "sf.USF.Entity")
public class UsfApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsfApplication.class, args);
	}

}

// openssl genrsa -out app.key 2048