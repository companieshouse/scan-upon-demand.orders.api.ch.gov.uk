package uk.gov.companieshouse.missingimagedelivery.orders.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import uk.gov.companieshouse.missingimagedelivery.orders.api.model.MissingImageDeliveryItem;

@Repository
public interface MissingImageDeliveryItemRepository extends MongoRepository<MissingImageDeliveryItem, String> {
}
