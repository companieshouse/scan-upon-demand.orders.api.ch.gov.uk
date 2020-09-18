package uk.gov.companieshouse.scanupondemand.orders.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.companieshouse.scanupondemand.orders.api.dto.ScanUponDemandItemRequestDTO;
import uk.gov.companieshouse.scanupondemand.orders.api.dto.ScanUponDemandItemResponseDTO;
import uk.gov.companieshouse.scanupondemand.orders.api.logging.LoggingUtils;
import uk.gov.companieshouse.scanupondemand.orders.api.mapper.ScanUponDemandItemMapper;
import uk.gov.companieshouse.scanupondemand.orders.api.model.FilingHistoryDocument;
import uk.gov.companieshouse.scanupondemand.orders.api.model.ScanUponDemandItem;
import uk.gov.companieshouse.scanupondemand.orders.api.service.CompanyService;
import uk.gov.companieshouse.scanupondemand.orders.api.service.FilingHistoryDocumentService;
import uk.gov.companieshouse.scanupondemand.orders.api.service.ScanUponDemandItemService;
import uk.gov.companieshouse.scanupondemand.orders.api.util.EricHeaderHelper;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;
import static uk.gov.companieshouse.scanupondemand.orders.api.logging.LoggingUtils.COMPANY_NUMBER_LOG_KEY;
import static uk.gov.companieshouse.scanupondemand.orders.api.logging.LoggingUtils.REQUEST_ID_HEADER_NAME;
import static uk.gov.companieshouse.scanupondemand.orders.api.logging.LoggingUtils.SCAN_UPON_DEMAND_ID_LOG_KEY;
import static uk.gov.companieshouse.scanupondemand.orders.api.logging.LoggingUtils.STATUS_LOG_KEY;
import static uk.gov.companieshouse.scanupondemand.orders.api.logging.LoggingUtils.USER_ID_LOG_KEY;

@RestController
public class ScanUponDemandItemController {

    private final ScanUponDemandItemMapper mapper;
    private final CompanyService companyService;
    private final ScanUponDemandItemService scanUponDemandItemService;
    private final FilingHistoryDocumentService filingHistoryDocumentService;

    public ScanUponDemandItemController(final ScanUponDemandItemMapper mapper,
                                        final CompanyService companyService,
                                        final ScanUponDemandItemService scanUponDemandItemService,
                                        final FilingHistoryDocumentService filingHistoryDocumentService) {
        this.mapper = mapper;
        this.companyService = companyService;
        this.scanUponDemandItemService = scanUponDemandItemService;
        this.filingHistoryDocumentService = filingHistoryDocumentService;
    }

    @PostMapping("${uk.gov.companieshouse.scanupondemand.orders.api.home}")
    public ResponseEntity<Object> createScanUponDemandItem(
        final @Valid @RequestBody ScanUponDemandItemRequestDTO scanUponDemandItemRequestDTO,
        HttpServletRequest request,
        final @RequestHeader(REQUEST_ID_HEADER_NAME) String requestId) {

        Map<String, Object> logMap = LoggingUtils.createLoggingDataMap(requestId);
        LoggingUtils.getLogger().infoRequest(request, "create scan upon demand item request", logMap);

        ScanUponDemandItem item = mapper.scanUponDemandItemRequestDTOtoScanUponDemandItem(scanUponDemandItemRequestDTO);

        item.setUserId(EricHeaderHelper.getIdentity(request));
        final String companyName = companyService.getCompanyName(item.getCompanyNumber());
        item.setCompanyName(companyName);

        final FilingHistoryDocument filing =
            filingHistoryDocumentService.getFilingHistoryDocument(
                item.getData().getCompanyNumber(),
                item.getData().getItemOptions().getFilingHistoryId());

        item.getData().setItemOptions(filing);

        ScanUponDemandItem createdItem = scanUponDemandItemService.createScanUponDemandItem(item);

        logMap.put(USER_ID_LOG_KEY, createdItem.getUserId());
        logMap.put(COMPANY_NUMBER_LOG_KEY, createdItem.getCompanyNumber());
        logMap.put(SCAN_UPON_DEMAND_ID_LOG_KEY, createdItem.getId());
        logMap.put(STATUS_LOG_KEY, CREATED);
        LoggingUtils.getLogger().infoRequest(request, "scan upon demand item created", logMap);

        final ScanUponDemandItemResponseDTO createdScanUponDemandItemResponseDTO = mapper
            .scanUponDemandItemToScanUponDemandItemResponseDTO(createdItem.getData());

        return ResponseEntity.status(CREATED).body(createdScanUponDemandItemResponseDTO);

    }
}
