package uk.gov.companieshouse.scanupondemand.orders.api.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import uk.gov.companieshouse.scanupondemand.orders.api.dto.FilingHistoryDocumentRequestDTO;
import uk.gov.companieshouse.scanupondemand.orders.api.dto.ScanUponDemandItemRequestDTO;
import uk.gov.companieshouse.scanupondemand.orders.api.dto.ScanUponDemandItemResponseDTO;
import uk.gov.companieshouse.scanupondemand.orders.api.model.FilingHistoryDocument;
import uk.gov.companieshouse.scanupondemand.orders.api.model.Links;
import uk.gov.companieshouse.scanupondemand.orders.api.model.ScanUponDemandItem;
import uk.gov.companieshouse.scanupondemand.orders.api.model.ScanUponDemandItemData;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static uk.gov.companieshouse.scanupondemand.orders.api.util.TestConstants.FILING_HISTORY_TYPE_CH01;

@ExtendWith(SpringExtension.class)
@SpringJUnitConfig(ScanUponDemandItemMapperTest.Config.class)
public class ScanUponDemandItemMapperTest {

	private static final String COMPANY_NUMBER = "00006400";
	private static final String CUSTOMER_REFERENCE = "SCUD Item ordered by Yiannis";
	private static final Integer QUANTITY = 1;
	private static final String ID = "SCD-462515-995726";
	private static final String COMPANY_NAME = "THE GIRLS' DAY SCHOOL TRUST";
	private static final String KIND = "item#scan-on-demand";
	private static final String ETAG = "9d39ea69b64c80ca42ed72328b48c303c4445e28";
	private static final String POSTAGE_COST = "15";
	private static final boolean POSTAL_DELIVERY = true;
	private static final String DESCRIPTION_IDENTIFIER = "Description Identifier";
	private static final String DESCRIPTION = "Description";
    private static final Map<String, String> DESCRIPTION_VALUES = singletonMap("key1", "value1");
	private static final FilingHistoryDocument ITEM_OPTIONS;
	private static final String FILING_HISTORY_ID = "MzAwOTM2MDg5OWFkaXF6a2N5";
	private static final String FILING_HISTORY_DATE = "2010-02-12";
	private static final String FILING_HISTORY_DESCRIPTION = "change-person-director-company-with-change-date";
	private static final Map<String, Object> FILING_HISTORY_DESCRIPTION_VALUES;

	private static final Links LINKS;

	static {
		FILING_HISTORY_DESCRIPTION_VALUES = new HashMap<>();
		FILING_HISTORY_DESCRIPTION_VALUES.put("change_date", "2010-02-12");
		FILING_HISTORY_DESCRIPTION_VALUES.put("officer_name", "Thomas David Wheare");

		ITEM_OPTIONS = new FilingHistoryDocument();
		ITEM_OPTIONS.setFilingHistoryId(FILING_HISTORY_ID);
		ITEM_OPTIONS.setFilingHistoryType(FILING_HISTORY_TYPE_CH01);
		ITEM_OPTIONS.setFilingHistoryDescriptionValues(FILING_HISTORY_DESCRIPTION_VALUES);
		ITEM_OPTIONS.setFilingHistoryDescription(FILING_HISTORY_DESCRIPTION);
		ITEM_OPTIONS.setFilingHistoryDate(FILING_HISTORY_DATE);

		LINKS = new Links();
		LINKS.setSelf("self");
	}

	@Configuration
	@ComponentScan(basePackageClasses = ScanUponDemandItemMapperTest.class)
	static class Config {
	}

	@Autowired
	private ScanUponDemandItemMapper mapperUnderTest;

	@Test
	public void testScanUponDemandItemRequestDTOToScanUponDemandItem() {

		final FilingHistoryDocumentRequestDTO filingHistoryDocumentRequestDTO = new FilingHistoryDocumentRequestDTO();
		filingHistoryDocumentRequestDTO.setFilingHistoryId(FILING_HISTORY_ID);

		final ScanUponDemandItemRequestDTO dto = new ScanUponDemandItemRequestDTO();
		dto.setCompanyNumber(COMPANY_NUMBER);
		dto.setCustomerReference(CUSTOMER_REFERENCE);
		dto.setItemOptions(filingHistoryDocumentRequestDTO);
		dto.setQuantity(QUANTITY);

		final ScanUponDemandItem scanUponDemandItem = mapperUnderTest
				.scanUponDemandItemRequestDTOtoScanUponDemandItem(dto);

		ScanUponDemandItemData item = scanUponDemandItem.getData();

		assertThat(item, is(notNullValue()));
		assertThat(item.getCompanyNumber(), is(dto.getCompanyNumber()));
		assertThat(item.getCustomerReference(), is(dto.getCustomerReference()));
		assertThat((item.getItemOptions().getFilingHistoryId()), is(dto.getItemOptions().getFilingHistoryId()));
		assertThat(item.getQuantity(), is(dto.getQuantity()));
	}

	@Test
	public void testScanUponDemandItemDataToScanUponDemandItemDTO() {
		final ScanUponDemandItemData item = new ScanUponDemandItemData();
		item.setId(ID);
		item.setCompanyName(COMPANY_NAME);
		item.setCompanyNumber(COMPANY_NUMBER);
		item.setCustomerReference(CUSTOMER_REFERENCE);
		item.setLinks(LINKS);
		item.setQuantity(QUANTITY);
		item.setKind(KIND);
		item.setItemOptions(ITEM_OPTIONS);
		item.setEtag(ETAG);
		item.setPostageCost(POSTAGE_COST);
		item.setPostalDelivery(POSTAL_DELIVERY);
		item.setDescription(DESCRIPTION);
		item.setDescriptionIdentifier(DESCRIPTION_IDENTIFIER);
		item.setDescriptionValues(DESCRIPTION_VALUES);

		final ScanUponDemandItemResponseDTO dto = mapperUnderTest
				.scanUponDemandItemToScanUponDemandItemResponseDTO(item);

		assertThat(dto.getId(), is(item.getId()));
		assertThat(dto.getCompanyName(), is(item.getCompanyName()));
		assertThat(dto.getCompanyNumber(), is(item.getCompanyNumber()));
		assertThat(dto.getCustomerReference(), is(item.getCustomerReference()));
		assertThat(dto.getDescription(), is(item.getDescription()));
		assertThat(dto.getDescriptionIdentifier(), is(item.getDescriptionIdentifier()));
		assertThat(dto.getDescriptionValues(), is(item.getDescriptionValues()));
		assertThat(dto.getEtag(), is(item.getEtag()));
		assertThat(dto.getItemOptions().getFilingHistoryDate(), is(item.getItemOptions().getFilingHistoryDate()));
		assertThat(dto.getItemOptions().getFilingHistoryId(), is(item.getItemOptions().getFilingHistoryId()));
		assertThat(dto.getItemOptions().getFilingHistoryDescription(), is(item.getItemOptions().getFilingHistoryDescription()));
		assertThat(dto.getItemOptions().getFilingHistoryDescriptionValues(), is(item.getItemOptions().getFilingHistoryDescriptionValues()));
		assertThat(dto.getItemOptions().getFilingHistoryDate(), is(item.getItemOptions().getFilingHistoryDate()));
		assertThat(dto.getKind(), is(item.getKind()));
		assertThat(dto.getLinks().getSelf(), is(item.getLinks().getSelf()));
		assertThat(dto.getQuantity(), is(item.getQuantity()));

	}

}