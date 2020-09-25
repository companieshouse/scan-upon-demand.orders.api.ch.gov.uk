package uk.gov.companieshouse.missingimagedelivery.orders.api.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import uk.gov.companieshouse.missingimagedelivery.orders.api.dto.MissingImageDeliveryItemRequestDTO;
import uk.gov.companieshouse.missingimagedelivery.orders.api.dto.MissingImageDeliveryItemResponseDTO;
import uk.gov.companieshouse.missingimagedelivery.orders.api.model.MissingImageDeliveryItem;
import uk.gov.companieshouse.missingimagedelivery.orders.api.model.MissingImageDeliveryItemData;

@Mapper(componentModel = "spring")
public interface MissingImageDeliveryItemMapper {

	MissingImageDeliveryItem missingImageDeliveryItemRequestDTOtoMissingImageDeliveryItem(MissingImageDeliveryItemRequestDTO missingImageDeliveryItemDTO);

	MissingImageDeliveryItemResponseDTO missingImageDeliveryItemToMissingImageDeliveryItemResponseDTO(
			MissingImageDeliveryItemData missingImageDeliveryItemData);

	@AfterMapping
	default void setDefaults(MissingImageDeliveryItemRequestDTO missingImageDeliveryItemDTO,
			@MappingTarget MissingImageDeliveryItem missingImageDeliveryItem) {
	}
}
