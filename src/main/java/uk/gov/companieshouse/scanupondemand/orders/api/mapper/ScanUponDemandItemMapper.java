package uk.gov.companieshouse.scanupondemand.orders.api.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import uk.gov.companieshouse.scanupondemand.orders.api.dto.ScanUponDemandItemRequestDTO;
import uk.gov.companieshouse.scanupondemand.orders.api.dto.ScanUponDemandItemResponseDTO;
import uk.gov.companieshouse.scanupondemand.orders.api.model.ScanUponDemandItem;
import uk.gov.companieshouse.scanupondemand.orders.api.model.ScanUponDemandItemData;

@Mapper(componentModel = "spring")
public interface ScanUponDemandItemMapper {

	ScanUponDemandItem scanUponDemandItemRequestDTOtoScanUponDemandItem(ScanUponDemandItemRequestDTO scanUponDemandItemDTO);

	ScanUponDemandItemResponseDTO scanUponDemandItemToScanUponDemandItemResponseDTO(
			ScanUponDemandItemData scanUponDemandItemData);

	@AfterMapping
	default void setDefaults(ScanUponDemandItemRequestDTO scanUponDemandItemDTO,
			@MappingTarget ScanUponDemandItem scanUponDemandItem) {
	}
}
