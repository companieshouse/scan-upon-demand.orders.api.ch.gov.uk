package uk.gov.companieshouse.scanupondemand.orders.api.service;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.scanupondemand.orders.api.model.ItemCostCalculation;
import uk.gov.companieshouse.scanupondemand.orders.api.model.ItemCosts;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
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
    private static final String MVP_TOTAL_ITEM_COST = "3";
    private static final String POST_MVP_TOTAL_ITEM_COST = "15";

    private static final int MVP_QUANTITY = 1;
    private static final int POST_MVP_QUANTITY = 5;
    private static final int INVALID_QUANTITY = 0;

    private static final ItemCostCalculation MVP_EXPECTED_CALCULATION = new ItemCostCalculation(
            singletonList(new ItemCosts(DISCOUNT_APPLIED, ITEM_COST, CALCULATED_COST, SCAN_UPON_DEMAND)),
            POSTAGE_COST,
            MVP_TOTAL_ITEM_COST);

    private static final ItemCostCalculation POST_MVP_EXPECTED_CALCULATION = new ItemCostCalculation(
            singletonList(new ItemCosts(DISCOUNT_APPLIED, ITEM_COST, CALCULATED_COST, SCAN_UPON_DEMAND)),
            POSTAGE_COST,
            POST_MVP_TOTAL_ITEM_COST);

    @InjectMocks
    private ScanUponDemandCostCalculatorService serviceUnderTest;

    @Test
    @DisplayName("calculateCosts produces expected results for MVP quantity")
    void calculateCostsProducesExpectedResultsForMvpQuantity() {
        assertThat(serviceUnderTest.calculateCosts(MVP_QUANTITY)).
                isEqualToComparingFieldByFieldRecursively(MVP_EXPECTED_CALCULATION);
    }

    @Test
    @DisplayName("calculateCosts produces expected results for a post MVP quantity")
    void calculateCostsProducesExpectedResultsForPostMvpQuantity() {
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

}
