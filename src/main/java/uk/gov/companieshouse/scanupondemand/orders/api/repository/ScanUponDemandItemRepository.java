package uk.gov.companieshouse.scanupondemand.orders.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uk.gov.companieshouse.scanupondemand.orders.api.model.ScanUponDemandItem;

@RepositoryRestResource
public interface ScanUponDemandItemRepository extends MongoRepository<ScanUponDemandItem, String> { }
