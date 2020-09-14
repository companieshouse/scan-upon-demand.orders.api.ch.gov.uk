package uk.gov.companieshouse.scanupondemand.orders.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.gov.companieshouse.scanupondemand.orders.api.dto.ScanUponDemandItemResponseDTO;
import uk.gov.companieshouse.scanupondemand.orders.api.mapper.ScanUponDemandItemMapper;
import uk.gov.companieshouse.scanupondemand.orders.api.model.ScanUponDemandItem;
import uk.gov.companieshouse.scanupondemand.orders.api.model.ScanUponDemandItemData;
import uk.gov.companieshouse.scanupondemand.orders.api.service.ScanUponDemandItemService;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static uk.gov.companieshouse.scanupondemand.orders.api.util.TestConstants.REQUEST_ID_VALUE;

@ExtendWith(MockitoExtension.class)
public class ScanUponDemandItemControllerTest {

	private static final String ID = "SCD-552015-995597";

	@InjectMocks
	private ScanUponDemandItemController controllerUnderTest;

	@Mock
	private ScanUponDemandItemService scanUponDemandItemService;

	@Mock
	private ScanUponDemandItem item;

	@Mock
	private ScanUponDemandItemData data;

	@Mock
	private ScanUponDemandItemResponseDTO dto;

	@Mock
	private ScanUponDemandItemMapper mapper;
	
	@Test
	@DisplayName("GET scan upon demand resource returns item")
	void getScanUponDemandItemPresent() {
		when(scanUponDemandItemService.getScanUponDemandItemById(ID)).thenReturn(Optional.of(item));
		when(item.getData()).thenReturn(data);
		when(mapper.scanUponDemandItemToScanUponDemandItemResponseDTO(data)).thenReturn(dto);
		ResponseEntity<Object> response = controllerUnderTest.getScanUponDemandItem(ID, REQUEST_ID_VALUE);

		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getBody(), is(dto));
	}

	@Test
	@DisplayName("Get scan upon demand resource returns HTTP NOT FOUND")
	void getCertifiedCopyItemNotFound() {
		when(scanUponDemandItemService.getScanUponDemandItemById(ID)).thenReturn(Optional.empty());
		ResponseEntity<Object> response = controllerUnderTest.getScanUponDemandItem(ID, REQUEST_ID_VALUE);

		assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
	}
}