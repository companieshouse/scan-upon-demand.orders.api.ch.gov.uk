package uk.gov.companieshouse.scanupondemand.orders.api.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.gov.companieshouse.scanupondemand.orders.api.util.TestConstants.SCAN_UPON_DEMAND_ITEM_COST;

/**
 * Unit tests the {@link CostsConfig} class.
 */
@SpringBootTest
class CostsConfigTest {

    @Autowired
    private CostsConfig configUnderTest;

    @Test
    @DisplayName("The configured costs have their expected values")
    void costsAreConfiguredCorrectly() {
        assertThat(configUnderTest.getScanUponDemandItemCost(), is(SCAN_UPON_DEMAND_ITEM_COST));
    }

}
