package uk.gov.companieshouse.scanupondemand.orders.api.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.scanupondemand.orders.api.model.ItemCostCalculation;
import uk.gov.companieshouse.scanupondemand.orders.api.model.ItemCosts;
import uk.gov.companieshouse.scanupondemand.orders.api.model.ScanUponDemandItem;
import uk.gov.companieshouse.scanupondemand.orders.api.repository.ScanUponDemandItemRepository;

import java.time.LocalDateTime;

import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.gov.companieshouse.scanupondemand.orders.api.model.ProductType.SCAN_UPON_DEMAND;
import static uk.gov.companieshouse.scanupondemand.orders.api.util.TestUtils.verifyCreationTimestampsWithinExecutionInterval;

/**
 * Unit tests the {@link ScanUponDemandItemService} class.
 */
@ExtendWith(MockitoExtension.class)
class ScanUponDemandItemServiceTest {

    private static final String ID = "SCD-822015-998103";

    private static final String DISCOUNT_APPLIED = "0";
    private static final String ITEM_COST = "3";
    private static final String CALCULATED_COST = "3";
    private static final String POSTAGE_COST = "0";
    private static final String TOTAL_ITEM_COST = "3";

    private static final int QUANTITY = 1;

    private static final ItemCosts ITEM_COSTS =
            new ItemCosts(DISCOUNT_APPLIED, ITEM_COST, CALCULATED_COST, SCAN_UPON_DEMAND);
    private static final ItemCostCalculation CALCULATION = new ItemCostCalculation(
            singletonList(ITEM_COSTS),
            POSTAGE_COST,
            TOTAL_ITEM_COST);

    @InjectMocks
    private ScanUponDemandItemService serviceUnderTest;

    @Mock
    private EtagGeneratorService etagGenerator;

    @Mock
    private LinksGeneratorService linksGenerator;

    @Mock
    private IdGeneratorService idGeneratorService;

    @Mock
    private ScanUponDemandItemRepository repository;

    @Mock
    private ScanUponDemandCostCalculatorService costCalculatorService;

    @Test
    @DisplayName("createScanUponDemandItem creates and saves item with id, timestamps, etag and links, returns item with costs")
    void createScanUponDemandItemPopulatesAndSavesItem() {

        // Given
        when(idGeneratorService.autoGenerateId()).thenReturn(ID);
        when(costCalculatorService.calculateCosts(QUANTITY)).thenReturn(CALCULATION);
        ScanUponDemandItem scanUponDemandItem = new ScanUponDemandItem();
        scanUponDemandItem.setQuantity(QUANTITY);
        when(repository.save(scanUponDemandItem)).thenReturn(scanUponDemandItem);

        final LocalDateTime intervalStart = LocalDateTime.now();

        // When
        scanUponDemandItem = serviceUnderTest.createScanUponDemandItem(scanUponDemandItem);

        // Then
        final LocalDateTime intervalEnd = LocalDateTime.now();

        verifyCreationTimestampsWithinExecutionInterval(scanUponDemandItem, intervalStart, intervalEnd);
        assertThat(scanUponDemandItem.getId(), is(ID));
        verify(etagGenerator).generateEtag();
        verify(linksGenerator).generateLinks(ID);
        verify(costCalculatorService).calculateCosts(QUANTITY);
        assertThat(scanUponDemandItem.getItemCosts(), is(singletonList(ITEM_COSTS)));
        assertThat(scanUponDemandItem.getPostageCost(), is(POSTAGE_COST));
        assertThat(scanUponDemandItem.getTotalItemCost(), is(TOTAL_ITEM_COST));
        verify(repository).save(scanUponDemandItem);
    }

}
