package uk.gov.companieshouse.scanupondemand.orders.api.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.scanupondemand.orders.api.model.FilingHistoryDocument;
import uk.gov.companieshouse.scanupondemand.orders.api.model.ScanUponDemandItem;
import uk.gov.companieshouse.scanupondemand.orders.api.repository.ScanUponDemandItemRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests the {@link ScanUponDemandItemService} class.
 */
@ExtendWith(MockitoExtension.class)
public class ScanUponDemandItemServiceTest {

    private static final String ID = "CCD-123456-123456";
    private static final String COMPANY_NUMBER = "00000000";
    private static final String DESCRIPTION = "certified copy for company 00000000";
    private static final String DESCRIPTION_IDENTIFIER = "certified-copy";
    private static final String COMPANY_NUMBER_KEY = "company_number";

    private static final String ITEM_COST = "10";
    private static final String CALCULATED_COST = "10";
    private static final String POSTAGE_COST = "0";
    private static final String TOTAL_ITEM_COST = "10";

    private static final String FILING_HISTORY_ID = "1";
    private static final String FILING_HISTORY_DATE = "2010-02-12";
    private static final String FILING_HISTORY_DESCRIPTION = "change-person-director-company-with-change-date";
    private static final Map<String, Object> FILING_HISTORY_DESCRIPTION_VALUES = new HashMap<>();
    private static final String FILING_HISTORY_TYPE = "CH01";

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

    @Test
    @DisplayName("createScanUponDemandItem creates and saves the scud item with id, timestamps, etag and links")
    void createCertifiedCopyItemPopulatesAndSavesItem() {
        when(idGeneratorService.autoGenerateId()).thenReturn(ID);

        final FilingHistoryDocument scudItemOptions = new FilingHistoryDocument(
            FILING_HISTORY_DATE,
            FILING_HISTORY_DESCRIPTION,
            FILING_HISTORY_DESCRIPTION_VALUES,
            FILING_HISTORY_ID,
            FILING_HISTORY_TYPE);

        final ScanUponDemandItem scudItem = new ScanUponDemandItem();
        scudItem.setItemOptions(scudItemOptions);
        scudItem.setQuantity(1);

        when(repository.save(scudItem)).thenReturn(scudItem);

        final LocalDateTime intervalStart = LocalDateTime.now();

        serviceUnderTest.createScanUponDemandItem(scudItem);

        final LocalDateTime intervalEnd = LocalDateTime.now();

        verifyCreationTimestampsWithinExecutionInterval(scudItem, intervalStart, intervalEnd);
        assertThat(scudItem.getId(), is(ID));
        verify(etagGenerator).generateEtag();
        verify(linksGenerator).generateLinks(ID);
    }
    /**
     * Verifies that the item created at and updated at timestamps are within the expected interval
     * for item creation.
     * @param itemCreated the item created
     * @param intervalStart roughly the start of the test
     * @param intervalEnd roughly the end of the test
     */
    private void verifyCreationTimestampsWithinExecutionInterval(final ScanUponDemandItem itemCreated,
                                                                 final LocalDateTime intervalStart,
                                                                 final LocalDateTime intervalEnd) {
        assertThat(itemCreated.getCreatedAt().isAfter(intervalStart) ||
            itemCreated.getCreatedAt().isEqual(intervalStart), is(true));
        assertThat(itemCreated.getCreatedAt().isBefore(intervalEnd) ||
            itemCreated.getCreatedAt().isEqual(intervalEnd), is(true));
        assertThat(itemCreated.getUpdatedAt().isAfter(intervalStart) ||
            itemCreated.getUpdatedAt().isEqual(intervalStart), is(true));
        assertThat(itemCreated.getUpdatedAt().isBefore(intervalEnd) ||
            itemCreated.getUpdatedAt().isEqual(intervalEnd), is(true));
    }
}

