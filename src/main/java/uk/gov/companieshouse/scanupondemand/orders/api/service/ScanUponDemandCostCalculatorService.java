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
    private static final String ITEM_COST = "3";
    private static final String CALCULATED_COST = "3";
    private static final String POSTAGE_COST = "0";
    private static final String TOTAL_ITEM_COST = "3";

    /**
     * Calculates the scan upon demand item costs.
     * @return all of the relevant costs
     */
    public ItemCostCalculation calculateCosts() {
        // No actual 'calculation' involved as yet.
        return new ItemCostCalculation(
                singletonList(new ItemCosts(DISCOUNT_APPLIED, ITEM_COST, CALCULATED_COST, SCAN_UPON_DEMAND)),
                POSTAGE_COST, TOTAL_ITEM_COST);
    }
}

