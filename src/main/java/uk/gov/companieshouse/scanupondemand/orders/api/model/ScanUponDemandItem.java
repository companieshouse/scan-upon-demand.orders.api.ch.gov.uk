package uk.gov.companieshouse.scanupondemand.orders.api.model;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * An instance of this represents a scan upon demand item.
 */

@Document(collection = "scan-upon-demands")
public class ScanUponDemandItem extends Item { }