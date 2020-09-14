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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import uk.gov.companieshouse.scanupondemand.orders.api.dto.ScanUponDemandItemRequestDTO;
import uk.gov.companieshouse.scanupondemand.orders.api.dto.ScanUponDemandItemResponseDTO;
import uk.gov.companieshouse.scanupondemand.orders.api.model.Links;
import uk.gov.companieshouse.scanupondemand.orders.api.model.ScanUponDemandItem;
import uk.gov.companieshouse.scanupondemand.orders.api.model.ScanUponDemandItemData;
import uk.gov.companieshouse.scanupondemand.orders.api.model.ScanUponDemandItemOptions;
import uk.gov.companieshouse.scanupondemand.orders.api.repository.ScanUponDemandItemRepository;
import uk.gov.companieshouse.scanupondemand.orders.api.service.ApiClientService;
import uk.gov.companieshouse.scanupondemand.orders.api.service.CompanyService;
import uk.gov.companieshouse.scanupondemand.orders.api.service.IdGeneratorService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.gov.companieshouse.api.util.security.EricConstants.ERIC_IDENTITY;
import static uk.gov.companieshouse.api.util.security.EricConstants.ERIC_IDENTITY_TYPE;
import static uk.gov.companieshouse.scanupondemand.orders.api.logging.LoggingUtils.REQUEST_ID_HEADER_NAME;
import static uk.gov.companieshouse.scanupondemand.orders.api.util.TestConstants.ERIC_IDENTITY_TYPE_OAUTH2_VALUE;
import static uk.gov.companieshouse.scanupondemand.orders.api.util.TestConstants.ERIC_IDENTITY_VALUE;
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
	private static final Links LINKS;
	private static final String TOKEN_ETAG = "9d39ea69b64c80ca42ed72328b48c303c4445e28";
	private static final String POSTAGE_COST = "0";

	private static final String FILING_HISTORY_ID = "1";
	private static final String FILING_HISTORY_DATE = "2010-02-12";
	private static final String FILING_HISTORY_DESCRIPTION = "change-person-director-company-with-change-date";
	private static final Map<String, Object> FILING_HISTORY_DESCRIPTION_VALUES;
	public static final String FILING_HISTORY_TYPE_CH01 = "CH01";

	static {
		LINKS = new Links();
		LINKS.setSelf(SCAN_UPON_DEMAND_URL + "/" + SCAN_UPON_DEMAND_ID);
		FILING_HISTORY_DESCRIPTION_VALUES = new HashMap<>();
		FILING_HISTORY_DESCRIPTION_VALUES.put("change_date", "2010-02-12");
		FILING_HISTORY_DESCRIPTION_VALUES.put("officer_name", "Thomas David Wheare");
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

	@AfterEach
	void tearDown() {
		repository.deleteAll();
	}

	@Test
	@DisplayName("Successfully creates scan upon demand item")
	void createScanUponDemandItemSuccessfullyCreatesScanUponDemandItem() throws Exception {
		final ScanUponDemandItemRequestDTO scanUponDemandItemDTORequest = new ScanUponDemandItemRequestDTO();
		scanUponDemandItemDTORequest.setCompanyNumber(COMPANY_NUMBER);
		scanUponDemandItemDTORequest.setCustomerReference(CUSTOMER_REFERENCE);
		scanUponDemandItemDTORequest.setQuantity(QUANTITY_1);

		when(idGeneratorService.autoGenerateId()).thenReturn(SCAN_UPON_DEMAND_ID);
		when(companyService.getCompanyName(COMPANY_NUMBER)).thenReturn(COMPANY_NAME);

		mockMvc.perform(post(SCAN_UPON_DEMAND_URL).header(REQUEST_ID_HEADER_NAME, REQUEST_ID_VALUE)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(scanUponDemandItemDTORequest))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.company_number", is(COMPANY_NUMBER)))
				.andExpect(jsonPath("$.company_name", is(COMPANY_NAME)))
				.andExpect(jsonPath("$.customer_reference", is(CUSTOMER_REFERENCE)));

		final ScanUponDemandItem retrievedCopy = assertItemSavedCorrectly(SCAN_UPON_DEMAND_ID);
	}

	@Test
	@DisplayName("Successfully gets a scan upon demand item")
	void getScanUponDemandItemSuccessfully() throws Exception {
		final ScanUponDemandItemData itemData = new ScanUponDemandItemData();
		itemData.setCompanyName(COMPANY_NAME);
		itemData.setCompanyNumber(COMPANY_NUMBER);
		itemData.setId(SCAN_UPON_DEMAND_ID);
		itemData.setQuantity(QUANTITY_1);
		itemData.setCustomerReference(CUSTOMER_REFERENCE);
		itemData.setEtag(TOKEN_ETAG);
		itemData.setLinks(LINKS);
		itemData.setPostageCost(POSTAGE_COST);
		final ScanUponDemandItem item = new ScanUponDemandItem();
		item.setData(itemData);
		item.setId(SCAN_UPON_DEMAND_ID);
		item.setUserId(ERIC_IDENTITY_VALUE);
		item.setCompanyName(COMPANY_NAME);
		item.setCompanyNumber(COMPANY_NUMBER);
		item.setCustomerReference(CUSTOMER_REFERENCE);
		item.setQuantity(QUANTITY_1);
		item.setEtag(TOKEN_ETAG);
		item.setLinks(LINKS);
		item.setPostageCost(POSTAGE_COST);
		final ScanUponDemandItemOptions itemOptions = new ScanUponDemandItemOptions(FILING_HISTORY_DATE,
				FILING_HISTORY_DESCRIPTION, FILING_HISTORY_DESCRIPTION_VALUES, FILING_HISTORY_ID, FILING_HISTORY_TYPE_CH01);
		item.setItemOptions(itemOptions);
		repository.save(item);

		final ScanUponDemandItemResponseDTO expectedItem = new ScanUponDemandItemResponseDTO();
		expectedItem.setCompanyNumber(COMPANY_NUMBER);
		expectedItem.setCompanyName(COMPANY_NAME);
		expectedItem.setQuantity(QUANTITY_1);
		expectedItem.setId(SCAN_UPON_DEMAND_ID);
		expectedItem.setCustomerReference(CUSTOMER_REFERENCE);
		expectedItem.setEtag(TOKEN_ETAG);
		expectedItem.setLinks(LINKS);
		expectedItem.setPostageCost(POSTAGE_COST);
		expectedItem.setItemOptions(itemOptions);

		// When and then
		mockMvc.perform(get(SCAN_UPON_DEMAND_URL + "/" + SCAN_UPON_DEMAND_ID)
				.header(REQUEST_ID_HEADER_NAME, REQUEST_ID_VALUE)
				.header(ERIC_IDENTITY_TYPE, ERIC_IDENTITY_TYPE_OAUTH2_VALUE)
				.header(ERIC_IDENTITY, ERIC_IDENTITY_VALUE)
				.header(REQUEST_ID_HEADER_NAME, REQUEST_ID_VALUE)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(expectedItem), true))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	@DisplayName("Returns not found when a scan upon demand item does not exist")
	void getScanUponDemandItemReturnsNotFound() throws Exception {
		// When and then
		mockMvc.perform(get(SCAN_UPON_DEMAND_URL + "/" + SCAN_UPON_DEMAND_ID)
				.header(REQUEST_ID_HEADER_NAME, REQUEST_ID_VALUE)
				.header(ERIC_IDENTITY_TYPE, ERIC_IDENTITY_TYPE_OAUTH2_VALUE)
				.header(ERIC_IDENTITY, ERIC_IDENTITY_VALUE)
				.header(REQUEST_ID_HEADER_NAME, REQUEST_ID_VALUE)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andDo(MockMvcResultHandlers.print());
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
