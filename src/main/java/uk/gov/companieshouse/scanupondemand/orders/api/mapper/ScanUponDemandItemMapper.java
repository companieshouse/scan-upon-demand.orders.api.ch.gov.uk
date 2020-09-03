package uk.gov.companieshouse.scanupondemand.orders.api.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import uk.gov.companieshouse.scanupondemand.orders.api.dto.ScanUponDemandItemDTO;
import uk.gov.companieshouse.scanupondemand.orders.api.model.ScanUponDemandItem;

@Mapper(componentModel = "spring")
public interface ScanUponDemandItemMapper {
	ScanUponDemandItem scanUponDemandItemDTOtoScanUponDemandItem(ScanUponDemandItemDTO scanUponDemandItemDTO);

	ScanUponDemandItemDTO scanUponDemandItemToScanUponDemandItemDTO(ScanUponDemandItem scanUponDemandItem);

	@AfterMapping
	default void setDefaults(ScanUponDemandItemDTO scanUponDemandItemDTO,
			@MappingTarget ScanUponDemandItem scanUponDemandItem) {
		int quantity = scanUponDemandItemDTO.getQuantity();
		scanUponDemandItem.setQuantity(quantity > 0 ? quantity : 1);
	}
}