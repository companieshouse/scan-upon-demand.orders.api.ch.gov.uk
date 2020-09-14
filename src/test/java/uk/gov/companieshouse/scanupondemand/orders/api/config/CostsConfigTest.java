package uk.gov.companieshouse.scanupondemand.orders.api.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests the {@link CostsConfig} class.
 */
@SpringBootTest
class CostsConfigTest {

    private static final int SCAN_UPON_DEMAND_ITEM_COST = 3;

    @Autowired
    private CostsConfig configUnderTest;

    @Test
    @DisplayName("The configured costs have their expected values")
    void costsAreConfiguredCorrectly() {
        assertThat(configUnderTest.getScanUponDemandItemCost(), is(SCAN_UPON_DEMAND_ITEM_COST));
    }

}
