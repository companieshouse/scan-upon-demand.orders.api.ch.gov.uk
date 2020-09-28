package uk.gov.companieshouse.missingimagedelivery.orders.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uk.gov.companieshouse.missingimagedelivery.orders.api.model.MissingImageDeliveryItem;

@RepositoryRestResource
public interface MissingImageDeliveryItemRepository extends MongoRepository<MissingImageDeliveryItem, String> {
}
