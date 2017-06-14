package ch.com.mazad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ComponentScan("ch.com.mazad")
@EnableFeignClients
@EnableHystrix
@EnableAsync
@EnableJpaRepositories("ch.com.mazad.repository")
public class MazadServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MazadServiceApplication.class, args);
	}
}
