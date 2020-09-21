package uk.gov.companieshouse.scanupondemand.orders.api.service;

import org.springframework.stereotype.Service;
import uk.gov.companieshouse.scanupondemand.orders.api.config.CostsConfig;
import uk.gov.companieshouse.scanupondemand.orders.api.model.ItemCostCalculation;
import uk.gov.companieshouse.scanupondemand.orders.api.model.ItemCosts;

import static java.util.Collections.singletonList;
import static uk.gov.companieshouse.scanupondemand.orders.api.model.ProductType.SCAN_UPON_DEMAND;

/**
 * Simple service class implemented for consistency across the three Item API implementations.
 */
@Service
public class ScanUponDemandCostCalculatorService {

    private static final String ZERO_DISCOUNT_APPLIED = "0";  // No rules have been devised for any SCUD discounting.
    private static final String ZERO_POSTAGE_COST = "0";      // Postage is not applicable to SCUD.

    private final CostsConfig costs;

    /**
     * Constructor.
     * @param costs the configured costs used by this in its calculations
     */
    public ScanUponDemandCostCalculatorService(final CostsConfig costs) {
        this.costs = costs;
    }

    /**
     * Calculates the scan upon demand item costs.
     * @param quantity the number of items
     * @return all of the relevant costs
     */
    public ItemCostCalculation calculateCosts(final int quantity) {
        checkArguments(quantity);
        final int calculatedCost = costs.getScanUponDemandItemCost();
        final String totalItemCost = Integer.toString(quantity * calculatedCost);
        return new ItemCostCalculation(
                singletonList(
                        new ItemCosts(ZERO_DISCOUNT_APPLIED,
                                      Integer.toString(costs.getScanUponDemandItemCost()),
                                      Integer.toString(calculatedCost),
                                      SCAN_UPON_DEMAND)),
                ZERO_POSTAGE_COST,
                totalItemCost);
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

