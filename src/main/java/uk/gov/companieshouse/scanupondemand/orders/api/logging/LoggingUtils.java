package uk.gov.companieshouse.scanupondemand.orders.api.logging;

import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

public class LoggingUtils {
	
	private LoggingUtils() { }
	
	public static final String APPLICATION_NAMESPACE = "scan-upon-demand.orders.api.ch.gov.uk";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(APPLICATION_NAMESPACE);
	
	public static Logger getLogger() {
		return LOGGER;
	}
}