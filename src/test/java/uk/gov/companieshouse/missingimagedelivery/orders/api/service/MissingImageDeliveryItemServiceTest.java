package uk.gov.companieshouse.missingimagedelivery.orders.api.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.missingimagedelivery.orders.api.model.ItemCostCalculation;
import uk.gov.companieshouse.missingimagedelivery.orders.api.model.ItemCosts;
import uk.gov.companieshouse.missingimagedelivery.orders.api.model.MissingImageDeliveryItem;
import uk.gov.companieshouse.missingimagedelivery.orders.api.model.MissingImageDeliveryItemOptions;
import uk.gov.companieshouse.missingimagedelivery.orders.api.repository.MissingImageDeliveryItemRepository;
import uk.gov.companieshouse.missingimagedelivery.orders.api.util.TestConstants;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.gov.companieshouse.missingimagedelivery.orders.api.model.ProductType.MISSING_IMAGE_DELIVERY;
import static uk.gov.companieshouse.missingimagedelivery.orders.api.util.TestConstants.DISCOUNT_APPLIED;
import static uk.gov.companieshouse.missingimagedelivery.orders.api.util.TestConstants.MISSING_IMAGE_DELIVERY_ITEM_COST_STRING;
import static uk.gov.companieshouse.missingimagedelivery.orders.api.util.TestUtils.verifyCreationTimestampsWithinExecutionInterval;

/**
 * Unit tests the {@link MissingImageDeliveryItemService} class.
 */
@ExtendWith(MockitoExtension.class)
public class MissingImageDeliveryItemServiceTest {

    private static final String ID = "SCD-822015-998103";
    private static final String CALCULATED_COST = "10";
    private static final String POSTAGE_COST = "0";
    private static final String FILING_HISTORY_ID = "1";
    private static final String FILING_HISTORY_DATE = "2010-02-12";
    private static final String FILING_HISTORY_DESCRIPTION = "change-person-director-company-with-change-date";
    private static final Map<String, Object> FILING_HISTORY_DESCRIPTION_VALUES = new HashMap<>();
    private static final String FILING_HISTORY_TYPE = "CH01";
    private static final int QUANTITY = 1;

    private static final ItemCosts ITEM_COSTS =
            new ItemCosts(DISCOUNT_APPLIED, MISSING_IMAGE_DELIVERY_ITEM_COST_STRING, CALCULATED_COST, MISSING_IMAGE_DELIVERY);
    private static final ItemCostCalculation CALCULATION = new ItemCostCalculation(
            singletonList(ITEM_COSTS),
            POSTAGE_COST,
            TestConstants.TOTAL_ITEM_COST);


    @InjectMocks
    private MissingImageDeliveryItemService serviceUnderTest;

    @Mock
    private EtagGeneratorService etagGenerator;

    @Mock
    private LinksGeneratorService linksGenerator;

    @Mock
    private IdGeneratorService idGeneratorService;

    @Mock
    private MissingImageDeliveryItemRepository repository;

    @Mock
    private MissingImageDeliveryCostCalculatorService costCalculatorService;

    @Test
    @DisplayName("createMissingImageDeliveryItem creates and saves item with id, timestamps, etag and links, returns item with costs and item options")
    void createMissingImageDeliveryItemPopulatesAndSavesItem() {

        // Given
        final MissingImageDeliveryItemOptions scudItemOptions = new MissingImageDeliveryItemOptions(
            FILING_HISTORY_DATE,
            FILING_HISTORY_DESCRIPTION,
            FILING_HISTORY_DESCRIPTION_VALUES,
            FILING_HISTORY_ID,
            FILING_HISTORY_TYPE);
        when(idGeneratorService.autoGenerateId()).thenReturn(ID);
        when(costCalculatorService.calculateCosts(QUANTITY)).thenReturn(CALCULATION);
        MissingImageDeliveryItem scanUponDemandItem = new MissingImageDeliveryItem();
        scanUponDemandItem.setQuantity(QUANTITY);
        scanUponDemandItem.setItemOptions(scudItemOptions);
        when(repository.save(scanUponDemandItem)).thenReturn(scanUponDemandItem);

        final LocalDateTime intervalStart = LocalDateTime.now();

        // When
        scanUponDemandItem = serviceUnderTest.createMissingImageDeliveryItem(scanUponDemandItem);

        // Then
        final LocalDateTime intervalEnd = LocalDateTime.now();

        verifyCreationTimestampsWithinExecutionInterval(scanUponDemandItem, intervalStart, intervalEnd);
        assertThat(scanUponDemandItem.getId(), is(ID));
        verify(etagGenerator).generateEtag();
        verify(linksGenerator).generateLinks(ID);
        assertThat(scanUponDemandItem.getId(), is(ID));
        verify(costCalculatorService).calculateCosts(QUANTITY);
        assertThat(scanUponDemandItem.getItemCosts(), is(singletonList(ITEM_COSTS)));
        assertThat(scanUponDemandItem.getPostageCost(), is(POSTAGE_COST));
        assertThat(scanUponDemandItem.getTotalItemCost(), is(TestConstants.TOTAL_ITEM_COST));
        assertThat(scanUponDemandItem.getItemOptions().getFilingHistoryDate(), is (FILING_HISTORY_DATE));
        assertThat(scanUponDemandItem.getItemOptions().getFilingHistoryDescription(), is (FILING_HISTORY_DESCRIPTION));
        assertThat(scanUponDemandItem.getItemOptions().getFilingHistoryDescriptionValues(), is(FILING_HISTORY_DESCRIPTION_VALUES));
        assertThat(scanUponDemandItem.getItemOptions().getFilingHistoryId(), is(FILING_HISTORY_ID));
        assertThat(scanUponDemandItem.getItemOptions().getFilingHistoryType(), is(FILING_HISTORY_TYPE));
        verify(repository).save(scanUponDemandItem);
    }

}
