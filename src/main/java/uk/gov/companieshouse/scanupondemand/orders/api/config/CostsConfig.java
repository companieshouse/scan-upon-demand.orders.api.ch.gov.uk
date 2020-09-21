package uk.gov.companieshouse.scanupondemand.orders.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;

@Configuration
@Component
@PropertySource(value = "classpath:costs.yaml", factory = YamlPropertyLoaderFactory.class)
@ConfigurationProperties(prefix = "costs")
@Validated
public class CostsConfig {

    @Min(1)
    private int scanUponDemandItemCost;

    public int getScanUponDemandItemCost() {
        return scanUponDemandItemCost;
    }

    public void setScanUponDemandItemCost(int scanUponDemandItemCost) {
        this.scanUponDemandItemCost = scanUponDemandItemCost;
    }

}
