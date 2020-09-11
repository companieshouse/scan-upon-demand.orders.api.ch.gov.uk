package uk.gov.companieshouse.scanupondemand.orders.api.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.scanupondemand.orders.api.model.ItemCostCalculation;
import uk.gov.companieshouse.scanupondemand.orders.api.model.ItemCosts;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.companieshouse.scanupondemand.orders.api.model.ProductType.SCAN_UPON_DEMAND;

/**
 * Unit tests the {@link ScanUponDemandCostCalculatorService} class.
 */
@ExtendWith(MockitoExtension.class)
class ScanUponDemandCostCalculatorServiceTest {

    private static final String DISCOUNT_APPLIED = "0";
    private static final String ITEM_COST = "3";
    private static final String CALCULATED_COST = "3";
    private static final String POSTAGE_COST = "0";
    private static final String TOTAL_ITEM_COST = "3";

    private static final ItemCostCalculation EXPECTED_CALCULATION = new ItemCostCalculation(
            singletonList(new ItemCosts(DISCOUNT_APPLIED, ITEM_COST, CALCULATED_COST, SCAN_UPON_DEMAND)),
            POSTAGE_COST,
            TOTAL_ITEM_COST);

    @InjectMocks
    private ScanUponDemandCostCalculatorService serviceUnderTest;

    @Test
    @DisplayName("calculateCosts produces expected results")
    void calculateCostsProducesExpectedResults() {
        assertThat(serviceUnderTest.calculateCosts()).isEqualToComparingFieldByFieldRecursively(EXPECTED_CALCULATION);
    }
}
