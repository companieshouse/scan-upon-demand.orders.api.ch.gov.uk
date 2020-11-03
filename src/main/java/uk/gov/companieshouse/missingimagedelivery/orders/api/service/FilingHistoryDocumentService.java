package uk.gov.companieshouse.missingimagedelivery.orders.api.service;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriTemplate;
import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.model.filinghistory.FilingApi;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.missingimagedelivery.orders.api.logging.LoggingUtils;
import uk.gov.companieshouse.missingimagedelivery.orders.api.model.MissingImageDeliveryItemOptions;

import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static uk.gov.companieshouse.missingimagedelivery.orders.api.logging.LoggingUtils.createLogMapWithCompanyNumber;
import static uk.gov.companieshouse.missingimagedelivery.orders.api.logging.LoggingUtils.logErrorWithStatus;

@Service
public class FilingHistoryDocumentService {

    private static final Logger LOGGER = LoggingUtils.getLogger();

    private static final UriTemplate
            GET_FILING_HISTORY_DOCUMENT =
            new UriTemplate("/company/{companyNumber}/filing-history/{filingHistoryId}");

    private final ApiClientService apiClientService;

    public FilingHistoryDocumentService(final ApiClientService apiClientService) {
        this.apiClientService = apiClientService;
    }

    /**
     * Gets the fully populated filing history document for the filing history document ID provided.
     * @param companyNumber the company number
     * @param filingHistoryDocumentId the filing history document ID
     * @return fully populated document
     */
    public MissingImageDeliveryItemOptions getFilingHistoryDocument(
            final String companyNumber,
            final String filingHistoryDocumentId) {

        final Map<String, Object> logMap = createLogMapWithCompanyNumber(companyNumber);
        LOGGER.info("Getting filing history document " + filingHistoryDocumentId + " for company number "
                + companyNumber + ".", logMap);
        final ApiClient apiClient = apiClientService.getInternalApiClient();
        final String uri = GET_FILING_HISTORY_DOCUMENT.expand(companyNumber, filingHistoryDocumentId).toString();
        try {
            final FilingApi filing = apiClient.filing().get(uri).execute().getData();
            return new MissingImageDeliveryItemOptions(filing.getDate().toString(),
                        filing.getDescription(),
                        filing.getDescriptionValues(),
                        filing.getTransactionId(),
                        filing.getType(),
                        filing.getCategory(),
                        filing.getBarcode());
        } catch (ApiErrorResponseException ex) {
            throw getResponseStatusException(ex, apiClient, companyNumber, filingHistoryDocumentId, uri);
        } catch (URIValidationException ex) {
            // Should this happen (unlikely), it is a broken contract, hence 500.
            final String error = "Invalid URI " + uri + " for filing";
            logErrorWithStatus(logMap, error, INTERNAL_SERVER_ERROR);
            LOGGER.error(error, ex, logMap);
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, error);
        }

    }

    /**
     * Creates an appropriate exception to report the underlying problem.
     * @param apiException the API exception caught
     * @param client the API client
     * @param companyNumber the number of the company for which the filing history is looked up
     * @param filingHistoryDocumentId the filing history document ID
     * @param uri the URI used to communicate with the company filing history API
     * @return the {@link ResponseStatusException} exception to report the problem
     */
    private ResponseStatusException getResponseStatusException(final ApiErrorResponseException apiException,
                                                               final ApiClient client,
                                                               final String companyNumber,
                                                               final String filingHistoryDocumentId,
                                                               final String uri) {

        final Map<String, Object> logMap = createLogMapWithCompanyNumber(companyNumber);
        final ResponseStatusException propagatedException;
        if (apiException.getStatusCode() >= INTERNAL_SERVER_ERROR.value()) {
            final String error = "Error sending request to "
                    + client.getBasePath() + uri + ": " + apiException.getStatusMessage();
            logErrorWithStatus(logMap, error, INTERNAL_SERVER_ERROR);
            LOGGER.error(error, apiException, logMap);
            propagatedException = new ResponseStatusException(INTERNAL_SERVER_ERROR, error);
        } else {
            final String error = "Error getting filing history document " + filingHistoryDocumentId +
                    " for company number " + companyNumber + ".";
            logErrorWithStatus(logMap, error, BAD_REQUEST);
            LOGGER.error(error, apiException, logMap);
            propagatedException =  new ResponseStatusException(BAD_REQUEST, error);
        }
        return propagatedException;
    }

}
