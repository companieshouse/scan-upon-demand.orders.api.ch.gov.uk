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
import uk.gov.companieshouse.scanupondemand.orders.api.dto.ScanUponDemandItemRequestDTO;
import uk.gov.companieshouse.scanupondemand.orders.api.dto.ScanUponDemandItemResponseDTO;
import uk.gov.companieshouse.scanupondemand.orders.api.model.ItemCostCalculation;
import uk.gov.companieshouse.scanupondemand.orders.api.model.ItemCosts;
import uk.gov.companieshouse.scanupondemand.orders.api.model.Links;
import uk.gov.companieshouse.scanupondemand.orders.api.model.ScanUponDemandItem;
import uk.gov.companieshouse.scanupondemand.orders.api.repository.ScanUponDemandItemRepository;
import uk.gov.companieshouse.scanupondemand.orders.api.service.CompanyService;
import uk.gov.companieshouse.scanupondemand.orders.api.service.EtagGeneratorService;
import uk.gov.companieshouse.scanupondemand.orders.api.service.IdGeneratorService;
import uk.gov.companieshouse.scanupondemand.orders.api.service.ScanUponDemandCostCalculatorService;

import java.time.LocalDateTime;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.gov.companieshouse.scanupondemand.orders.api.logging.LoggingUtils.REQUEST_ID_HEADER_NAME;
import static uk.gov.companieshouse.scanupondemand.orders.api.model.ProductType.SCAN_UPON_DEMAND;
import static uk.gov.companieshouse.scanupondemand.orders.api.util.TestConstants.REQUEST_ID_VALUE;
import static uk.gov.companieshouse.scanupondemand.orders.api.util.TestConstants.SCAN_UPON_DEMAND_URL;
import static uk.gov.companieshouse.scanupondemand.orders.api.util.TestUtils.verifyCreationTimestampsWithinExecutionInterval;

@AutoConfigureMockMvc

@SpringBootTest
class ScanUponDemandItemControllerIntegrationTest {

	private static final String SCAN_UPON_DEMAND_ID = "SCD-462515-995726";

	private static final String COMPANY_NUMBER = "00006400";
	private static final String COMPANY_NAME = "THE GIRLS' DAY SCHOOL TRUST";
	private static final String CUSTOMER_REFERENCE = "SCUD Item ordered by Yiannis";
	private static final int QUANTITY_1 = 1;
	private static final String ETAG = "49336c4c011b3b568c9b346d71e4159893a5e888";

	private static final String DISCOUNT_APPLIED = "0";
	private static final String ITEM_COST = "3";
	private static final String CALCULATED_COST = "3";
	private static final String POSTAGE_COST = "0";
	private static final String TOTAL_ITEM_COST = "3";

	private static final ItemCostCalculation CALCULATION = new ItemCostCalculation(
			singletonList(new ItemCosts(DISCOUNT_APPLIED, ITEM_COST, CALCULATED_COST, SCAN_UPON_DEMAND)),
			POSTAGE_COST,
			TOTAL_ITEM_COST);

	private static final Links LINKS;

	static {
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
	private EtagGeneratorService etagGeneratorService;

	@MockBean
	private ScanUponDemandCostCalculatorService calculatorService;

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
		when(etagGeneratorService.generateEtag()).thenReturn(ETAG);
		when(calculatorService.calculateCosts()).thenReturn(CALCULATION);
		when(companyService.getCompanyName(COMPANY_NUMBER)).thenReturn(COMPANY_NAME);

		final ScanUponDemandItemResponseDTO expectedItem = new ScanUponDemandItemResponseDTO();
		expectedItem.setId(SCAN_UPON_DEMAND_ID);
		expectedItem.setEtag(ETAG);
		expectedItem.setLinks(LINKS);
		expectedItem.setCompanyNumber(COMPANY_NUMBER);
		expectedItem.setCompanyName(COMPANY_NAME);
		expectedItem.setCustomerReference(CUSTOMER_REFERENCE);
		expectedItem.setQuantity(QUANTITY_1);
		expectedItem.setItemCosts(CALCULATION.getItemCosts());
		expectedItem.setPostageCost(CALCULATION.getPostageCost());
		expectedItem.setTotalItemCost(CALCULATION.getTotalItemCost());

		final LocalDateTime intervalStart = LocalDateTime.now();

		mockMvc.perform(post(SCAN_UPON_DEMAND_URL).header(REQUEST_ID_HEADER_NAME, REQUEST_ID_VALUE)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(scanUponDemandItemDTORequest))).andExpect(status().isCreated())
				.andExpect(content().json(objectMapper.writeValueAsString(expectedItem)))
				.andExpect(jsonPath("$.company_number", is(COMPANY_NUMBER)))
				.andExpect(jsonPath("$.company_name", is(COMPANY_NAME)))
				.andExpect(jsonPath("$.customer_reference", is(CUSTOMER_REFERENCE)));

		final ScanUponDemandItem retrievedItem = assertItemSavedCorrectly(SCAN_UPON_DEMAND_ID);
		final LocalDateTime intervalEnd = LocalDateTime.now();
		verifyCreationTimestampsWithinExecutionInterval(retrievedItem, intervalStart, intervalEnd);
		assertThat(retrievedItem.getEtag(), is(ETAG));
		assertThat(retrievedItem.getLinks(), is(LINKS));
		assertThat(retrievedItem.getCompanyNumber(), is(COMPANY_NUMBER));
		assertThat(retrievedItem.getCompanyName(), is(COMPANY_NAME));
		assertThat(retrievedItem.getCustomerReference(), is(CUSTOMER_REFERENCE));
		assertThat(retrievedItem.getQuantity(), is(QUANTITY_1));

		assertThat(retrievedItem.getItemCosts().size(), is(1));
		assertThat(retrievedItem.getItemCosts().size(), is(expectedItem.getItemCosts().size()));
		org.assertj.core.api.Assertions.assertThat(retrievedItem.getItemCosts().get(0))
				.isEqualToComparingFieldByField(CALCULATION.getItemCosts().get(0));

		assertThat(retrievedItem.getPostageCost(), is(CALCULATION.getPostageCost()));
		assertThat(retrievedItem.getTotalItemCost(), is(CALCULATION.getTotalItemCost()));
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
