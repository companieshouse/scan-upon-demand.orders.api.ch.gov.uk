package uk.gov.companieshouse.scanupondemand.orders.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScanUponDemandApiApplication {

	public static final String APPLICATION_NAMESPACE = "scan-upon-demand.orders.api.ch.gov.uk";

	public static void main(String[] args) {
		SpringApplication.run(ScanUponDemandApiApplication.class, args);
	}

}
