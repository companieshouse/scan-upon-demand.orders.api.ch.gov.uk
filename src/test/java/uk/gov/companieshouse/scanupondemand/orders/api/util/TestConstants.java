package uk.gov.companieshouse.scanupondemand.orders.api.util;

import static java.util.Collections.singletonList;

public class TestConstants {
    public static final String REQUEST_ID_VALUE = "f058ebd6-02f7-4d3f-942e-904344e8cde5";
    public static final String ERIC_IDENTITY_VALUE = "Y2VkZWVlMzhlZWFjY2M4MzQ3MT";
    public static final String ERIC_IDENTITY_TYPE_OAUTH2_VALUE = "oauth2";
    public static final String SCAN_UPON_DEMAND_URL = "/orderable/scan-upon-demand";
    public static final ApiErrorResponsePayload FILING_NOT_FOUND =
        new ApiErrorResponsePayload(singletonList(new Error("ch:service", "filing-history-item-not-found")));
    public static final String FILING_HISTORY_TYPE_CH01 = "CH01";
}
