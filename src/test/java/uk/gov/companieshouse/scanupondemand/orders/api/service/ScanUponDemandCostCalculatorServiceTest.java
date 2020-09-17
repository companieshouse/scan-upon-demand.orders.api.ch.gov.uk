package uk.gov.companieshouse.scanupondemand.orders.api.service;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.scanupondemand.orders.api.config.CostsConfig;
import uk.gov.companieshouse.scanupondemand.orders.api.model.ItemCostCalculation;
import uk.gov.companieshouse.scanupondemand.orders.api.model.ItemCosts;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static uk.gov.companieshouse.scanupondemand.orders.api.model.ProductType.SCAN_UPON_DEMAND;
import static uk.gov.companieshouse.scanupondemand.orders.api.util.TestConstants.CALCULATED_COST;
import static uk.gov.companieshouse.scanupondemand.orders.api.util.TestConstants.DISCOUNT_APPLIED;
import static uk.gov.companieshouse.scanupondemand.orders.api.util.TestConstants.POSTAGE_COST;
import static uk.gov.companieshouse.scanupondemand.orders.api.util.TestConstants.SCAN_UPON_DEMAND_ITEM_COST;
import static uk.gov.companieshouse.scanupondemand.orders.api.util.TestConstants.SCAN_UPON_DEMAND_ITEM_COST_STRING;

/**
 * Unit tests the {@link ScanUponDemandCostCalculatorService} class.
 */
@ExtendWith(MockitoExtension.class)
class ScanUponDemandCostCalculatorServiceTest {

    private static final String MVP_TOTAL_ITEM_COST = "3";
    private static final String POST_MVP_TOTAL_ITEM_COST = "15";
    private static final int ADJUSTED_SCAN_UPON_DEMAND_ITEM_COST = 5;
    private static final String ADJUSTED_SCAN_UPON_DEMAND_ITEM_COST_STRING = "5";
    private static final String ADJUSTED_ITEM_COST_CALCULATED_COST = "5";
    private static final String ADJUSTED_ITEM_COST_TOTAL_ITEM_COST = "5";

    private static final int MVP_QUANTITY = 1;
    private static final int POST_MVP_QUANTITY = 5;
    private static final int INVALID_QUANTITY = 0;

    private static final ItemCostCalculation MVP_EXPECTED_CALCULATION = new ItemCostCalculation(
            singletonList(new ItemCosts(DISCOUNT_APPLIED,
                                        SCAN_UPON_DEMAND_ITEM_COST_STRING,
                                        CALCULATED_COST,
                                        SCAN_UPON_DEMAND)),
            POSTAGE_COST,
            MVP_TOTAL_ITEM_COST);

    private static final ItemCostCalculation POST_MVP_EXPECTED_CALCULATION = new ItemCostCalculation(
            singletonList(new ItemCosts(DISCOUNT_APPLIED,
                                        SCAN_UPON_DEMAND_ITEM_COST_STRING,
                                        CALCULATED_COST,
                                        SCAN_UPON_DEMAND)),
            POSTAGE_COST,
            POST_MVP_TOTAL_ITEM_COST);

    private static final ItemCostCalculation ADJUSTED_ITEM_COST_EXPECTED_CALCULATION = new ItemCostCalculation(
            singletonList(new ItemCosts(DISCOUNT_APPLIED,
                    ADJUSTED_SCAN_UPON_DEMAND_ITEM_COST_STRING,
                    ADJUSTED_ITEM_COST_CALCULATED_COST,
                    SCAN_UPON_DEMAND)),
            POSTAGE_COST,
            ADJUSTED_ITEM_COST_TOTAL_ITEM_COST);

    @InjectMocks
    private ScanUponDemandCostCalculatorService serviceUnderTest;

    @Mock
    private CostsConfig costs;

    @Test
    @DisplayName("calculateCosts produces expected results for MVP quantity")
    void calculateCostsProducesExpectedResultsForMvpQuantity() {
        when(costs.getScanUponDemandItemCost()).thenReturn(SCAN_UPON_DEMAND_ITEM_COST);
        assertThat(serviceUnderTest.calculateCosts(MVP_QUANTITY)).
                isEqualToComparingFieldByFieldRecursively(MVP_EXPECTED_CALCULATION);
    }

    @Test
    @DisplayName("calculateCosts produces expected results for a post MVP quantity")
    void calculateCostsProducesExpectedResultsForPostMvpQuantity() {
        when(costs.getScanUponDemandItemCost()).thenReturn(SCAN_UPON_DEMAND_ITEM_COST);
        assertThat(serviceUnderTest.calculateCosts(POST_MVP_QUANTITY))
                .isEqualToComparingFieldByFieldRecursively(POST_MVP_EXPECTED_CALCULATION);
    }

    @Test
    @DisplayName("Incorrect quantity results in IllegalArgumentException")
    void incorrectQuantityTriggersIllegalArgumentException() {
        final IllegalArgumentException exception =
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> serviceUnderTest.calculateCosts(INVALID_QUANTITY));
        MatcherAssert.assertThat(exception.getMessage(), is("quantity must be greater than or equal to 1!"));
    }

    @Test
    @DisplayName("Calculated cost should be the item cost")
    void calculatedCostIsItemCost() {
        when(costs.getScanUponDemandItemCost()).thenReturn(ADJUSTED_SCAN_UPON_DEMAND_ITEM_COST);
        assertThat(serviceUnderTest.calculateCosts(MVP_QUANTITY)).
                isEqualToComparingFieldByFieldRecursively(ADJUSTED_ITEM_COST_EXPECTED_CALCULATION);
    }

}
