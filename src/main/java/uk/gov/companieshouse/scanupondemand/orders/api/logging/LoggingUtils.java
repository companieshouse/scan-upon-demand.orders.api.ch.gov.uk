package uk.gov.companieshouse.scanupondemand.orders.api.logging;

import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

public class LoggingUtils {

	private LoggingUtils() {
	}

	public static final String APPLICATION_NAMESPACE = "scan-upon-demand.orders.api.ch.gov.uk";
	public static final String COMPANY_NUMBER_LOG_KEY = "company_number";
	public static final String REQUEST_ID_LOG_KEY = "request_id";
	public static final String SCAN_UPON_DEMAND_ID_LOG_KEY = "scan_upon_demand_id";
	public static final String USER_ID_LOG_KEY = "user_id";
	public static final String STATUS_LOG_KEY = "status";
	public static final String ERRORS_LOG_KEY = "errors";
	public static final String IDENTITY_LOG_KEY = "identity";
	public static final String REQUEST_ID_HEADER_NAME = "X-Request-ID";

	private static final Logger LOGGER = LoggerFactory.getLogger(APPLICATION_NAMESPACE);

	public static Logger getLogger() {
		return LOGGER;
	}
}