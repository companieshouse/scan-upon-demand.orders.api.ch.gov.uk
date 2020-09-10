package uk.gov.companieshouse.scanupondemand.orders.api.service;

import org.springframework.stereotype.Service;
import uk.gov.companieshouse.scanupondemand.orders.api.model.ItemCostCalculation;
import uk.gov.companieshouse.scanupondemand.orders.api.model.ItemCosts;

import static java.util.Collections.singletonList;
import static uk.gov.companieshouse.scanupondemand.orders.api.model.ProductType.SCAN_UPON_DEMAND;

// TODO GCI-1366 Is this service justified?
@Service
public class ScanUponDemandCostCalculatorService {
    /**
     * Calculates the scan upon demand item costs.
     * @return all of the relevant costs
     */
    public ItemCostCalculation calculateCosts() {
        // TODO GCI-1366 If it is this simple, then at least use named constants.
        return new ItemCostCalculation(
                singletonList(new ItemCosts("0", "3", "3", SCAN_UPON_DEMAND)),
                "0", "0");
    }
}

