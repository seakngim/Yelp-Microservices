package kh.edu.cstad.business;

import com.netflix.discovery.EurekaNamespace;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BusinessMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusinessMsApplication.class, args);
	}

}
