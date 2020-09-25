package uk.gov.companieshouse.missingimagedelivery.orders.api.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.gov.companieshouse.missingimagedelivery.orders.api.util.TestConstants.MISSING_IMAGE_DELIVERY_ITEM_COST;

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
        assertThat(configUnderTest.getMissingImageDeliveryItemCost(), is(MISSING_IMAGE_DELIVERY_ITEM_COST));
    }

}
