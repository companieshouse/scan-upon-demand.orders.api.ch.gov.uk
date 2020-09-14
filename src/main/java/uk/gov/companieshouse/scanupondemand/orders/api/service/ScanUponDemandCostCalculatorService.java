package uk.gov.companieshouse.scanupondemand.orders.api.service;

import org.springframework.stereotype.Service;
import uk.gov.companieshouse.scanupondemand.orders.api.model.ItemCostCalculation;
import uk.gov.companieshouse.scanupondemand.orders.api.model.ItemCosts;

import static java.util.Collections.singletonList;
import static uk.gov.companieshouse.scanupondemand.orders.api.model.ProductType.SCAN_UPON_DEMAND;

/**
 * Simple service class implemented for consistency across the three Item API implementations.
 */
@Service
public class ScanUponDemandCostCalculatorService {

    private static final String DISCOUNT_APPLIED = "0";
    private static final int ITEM_COST = 3;
    private static final String ITEM_COST_STRING = Integer.toString(ITEM_COST);
    private static final String CALCULATED_COST = "3";
    private static final String POSTAGE_COST = "0";

    /**
     * Calculates the scan upon demand item costs.
     * @param quantity the number of items
     * @return all of the relevant costs
     */
    public ItemCostCalculation calculateCosts(final int quantity) {
        checkArguments(quantity);
        final String totalItemCost = Integer.toString(quantity * ITEM_COST);
        return new ItemCostCalculation(
                singletonList(new ItemCosts(DISCOUNT_APPLIED, ITEM_COST_STRING, CALCULATED_COST, SCAN_UPON_DEMAND)),
                POSTAGE_COST, totalItemCost);
    }

    /**
     * Utility method that checks the arguments provided to it. Throws an {@link IllegalArgumentException} should these
     * be outside of the range of reasonable values.
     * @param quantity the quantity of certificate items specified
     */
    private void checkArguments(final int quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException("quantity must be greater than or equal to 1!");
        }
    }

}

