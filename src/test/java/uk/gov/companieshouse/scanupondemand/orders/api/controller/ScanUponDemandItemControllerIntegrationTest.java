package uk.gov.companieshouse.scanupondemand.orders.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.companieshouse.scanupondemand.orders.api.dto.FilingHistoryDocumentRequestDTO;
import uk.gov.companieshouse.scanupondemand.orders.api.dto.ScanUponDemandItemRequestDTO;
import uk.gov.companieshouse.scanupondemand.orders.api.model.FilingHistoryDocument;
import uk.gov.companieshouse.scanupondemand.orders.api.model.Links;
import uk.gov.companieshouse.scanupondemand.orders.api.model.ScanUponDemandItem;
import uk.gov.companieshouse.scanupondemand.orders.api.repository.ScanUponDemandItemRepository;
import uk.gov.companieshouse.scanupondemand.orders.api.service.ApiClientService;
import uk.gov.companieshouse.scanupondemand.orders.api.service.CompanyService;
import uk.gov.companieshouse.scanupondemand.orders.api.service.FilingHistoryDocumentService;
import uk.gov.companieshouse.scanupondemand.orders.api.service.IdGeneratorService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.gov.companieshouse.api.util.security.EricConstants.ERIC_IDENTITY;
import static uk.gov.companieshouse.api.util.security.EricConstants.ERIC_IDENTITY_TYPE;
import static uk.gov.companieshouse.scanupondemand.orders.api.logging.LoggingUtils.REQUEST_ID_HEADER_NAME;
import static uk.gov.companieshouse.scanupondemand.orders.api.util.TestConstants.ERIC_IDENTITY_TYPE_OAUTH2_VALUE;
import static uk.gov.companieshouse.scanupondemand.orders.api.util.TestConstants.ERIC_IDENTITY_VALUE;
import static uk.gov.companieshouse.scanupondemand.orders.api.util.TestConstants.FILING_HISTORY_TYPE_CH01;
import static uk.gov.companieshouse.scanupondemand.orders.api.util.TestConstants.REQUEST_ID_VALUE;
import static uk.gov.companieshouse.scanupondemand.orders.api.util.TestConstants.SCAN_UPON_DEMAND_URL;

@AutoConfigureMockMvc

@SpringBootTest
class ScanUponDemandItemControllerIntegrationTest {

    private static final String SCAN_UPON_DEMAND_ID = "SCD-462515-995726";

    private static final String COMPANY_NUMBER = "00006400";
    private static final String COMPANY_NAME = "THE GIRLS' DAY SCHOOL TRUST";
    private static final String CUSTOMER_REFERENCE = "SCUD Item ordered by Yiannis";
    private static final int QUANTITY_1 = 1;
    private static final String FILING_HISTORY_ID = "MzAwOTM2MDg5OWFkaXF6a2N5";
    private static final String FILING_HISTORY_DATE = "2010-02-12";
    private static final String FILING_HISTORY_DESCRIPTION = "change-person-director-company-with-change-date";
    private static final Map<String, Object> FILING_HISTORY_DESCRIPTION_VALUES;

    private static final Links LINKS;

    static {
        FILING_HISTORY_DESCRIPTION_VALUES = new HashMap<>();
        FILING_HISTORY_DESCRIPTION_VALUES.put("change_date", "2010-02-12");
        FILING_HISTORY_DESCRIPTION_VALUES.put("officer_name", "Thomas David Wheare");
        LINKS = new Links();
        LINKS.setSelf(SCAN_UPON_DEMAND_URL + "/" + SCAN_UPON_DEMAND_ID);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ScanUponDemandItemRepository repository;

    @MockBean
    private IdGeneratorService idGeneratorService;

    @MockBean
    private CompanyService companyService;

    @MockBean
    private ApiClientService apiClientService;

    @MockBean
    private FilingHistoryDocumentService filingHistoryDocumentService;

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test

    @DisplayName("Successfully creates scan upon demand item")
    void createScanUponDemandItemSuccessfullyCreatesScanUponDemandItem() throws Exception {

        final FilingHistoryDocumentRequestDTO filingHistoryDocumentRequestDTO
            = new FilingHistoryDocumentRequestDTO();
        filingHistoryDocumentRequestDTO.setFilingHistoryId(FILING_HISTORY_ID);

        final ScanUponDemandItemRequestDTO scanUponDemandItemDTORequest = new ScanUponDemandItemRequestDTO();
        scanUponDemandItemDTORequest.setCompanyNumber(COMPANY_NUMBER);
        scanUponDemandItemDTORequest.setCustomerReference(CUSTOMER_REFERENCE);
        scanUponDemandItemDTORequest.setItemOptions(filingHistoryDocumentRequestDTO);
        scanUponDemandItemDTORequest.setQuantity(QUANTITY_1);

        final FilingHistoryDocument filing =
            new FilingHistoryDocument(FILING_HISTORY_DATE,
                FILING_HISTORY_DESCRIPTION,
                FILING_HISTORY_DESCRIPTION_VALUES,
                FILING_HISTORY_ID,
                FILING_HISTORY_TYPE_CH01);

        when(idGeneratorService.autoGenerateId()).thenReturn(SCAN_UPON_DEMAND_ID);
        when(companyService.getCompanyName(COMPANY_NUMBER)).thenReturn(COMPANY_NAME);
        when(filingHistoryDocumentService.getFilingHistoryDocument(eq(COMPANY_NUMBER), anyString())).thenReturn(filing);

        mockMvc.perform(post(SCAN_UPON_DEMAND_URL)
            .header(REQUEST_ID_HEADER_NAME, REQUEST_ID_VALUE)
            .header(ERIC_IDENTITY_TYPE, ERIC_IDENTITY_TYPE_OAUTH2_VALUE)
            .header(ERIC_IDENTITY, ERIC_IDENTITY_VALUE)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(scanUponDemandItemDTORequest)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.company_number", is(COMPANY_NUMBER)))
            .andExpect(jsonPath("$.company_name", is(COMPANY_NAME)))
            .andExpect(jsonPath("$.customer_reference", is(CUSTOMER_REFERENCE)))
            .andExpect(jsonPath("$.item_options.filing_history_date",
                is(FILING_HISTORY_DATE)))
            .andExpect(jsonPath("$.item_options.filing_history_description",
                is(FILING_HISTORY_DESCRIPTION)))
            .andExpect(jsonPath("$.item_options.filing_history_description_values",
                is(FILING_HISTORY_DESCRIPTION_VALUES)))
            .andExpect(jsonPath("$.item_options.filing_history_id",
                is(FILING_HISTORY_ID)))
            .andExpect(jsonPath("$.item_options.filing_history_type",
                is(FILING_HISTORY_TYPE_CH01)));

        final ScanUponDemandItem retrievedCopy = assertItemSavedCorrectly(SCAN_UPON_DEMAND_ID);
    }

    /**
     * Verifies that the scan upon demand item can be retrieved from the database
     * using its expected ID value.
     *
     * @param scanUponDemandId the expected ID of the newly created item
     * @return the retrieved scanUponDemand item, for possible further verification
     */
    private ScanUponDemandItem assertItemSavedCorrectly(final String scanUponDemandId) {
        final Optional<ScanUponDemandItem> retrievedScanUponDemandItem = repository.findById(scanUponDemandId);
        assertThat(retrievedScanUponDemandItem.isPresent(), is(true));
        assertThat(retrievedScanUponDemandItem.get().getId(), is(scanUponDemandId));
        return retrievedScanUponDemandItem.get();
    }
}
