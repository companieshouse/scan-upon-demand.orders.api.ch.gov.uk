package uk.gov.companieshouse.scanupondemand.orders.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import uk.gov.companieshouse.scanupondemand.orders.api.dto.ScanUponDemandItemRequestDTO;
import uk.gov.companieshouse.scanupondemand.orders.api.mapper.ScanUponDemandItemMapper;
import uk.gov.companieshouse.scanupondemand.orders.api.model.ScanUponDemandItem;
import uk.gov.companieshouse.scanupondemand.orders.api.model.ScanUponDemandItemData;
import uk.gov.companieshouse.scanupondemand.orders.api.service.ScanUponDemandItemService;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

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
	private ScanUponDemandItemRequestDTO dto;

	@Mock
	private ScanUponDemandItemMapper mapper;
	
	@Test
	@DisplayName("test1")
	void test1() {
	}

}