package uk.gov.companieshouse.missingimagedelivery.orders.api.model;

import com.google.gson.Gson;

import java.util.Map;

public class MissingImageDeliveryItemOptions {
    public MissingImageDeliveryItemOptions(){}

    public MissingImageDeliveryItemOptions(final String filingHistoryDate,
                                     final String filingHistoryDescription,
                                     final Map<String, Object> filingHistoryDescriptionValues,
                                     final String filingHistoryId,
                                     final String filingHistoryType,
                                     final String filingHistoryCategory,
                                     final String filingHistoryBarcode) {
        this.filingHistoryDate = filingHistoryDate;
        this.filingHistoryDescription = filingHistoryDescription;
        this.filingHistoryDescriptionValues = filingHistoryDescriptionValues;
        this.filingHistoryId = filingHistoryId;
        this.filingHistoryType = filingHistoryType;
        this.filingHistoryCategory = filingHistoryCategory;
        this.filingHistoryBarcode = filingHistoryBarcode;
    }

    private String filingHistoryDate;

    private String filingHistoryDescription;

    private Map<String, Object> filingHistoryDescriptionValues;

    private String filingHistoryId;

    private String filingHistoryType;

    private String filingHistoryCategory;

    private String filingHistoryCost;

    private String filingHistoryBarcode;

    public String getFilingHistoryDate() {
        return filingHistoryDate;
    }

    public void setFilingHistoryDate(String filingHistoryDate) {
        this.filingHistoryDate = filingHistoryDate;
    }

    public String getFilingHistoryDescription() {
        return filingHistoryDescription;
    }

    public void setFilingHistoryDescription(String filingHistoryDescription) {
        this.filingHistoryDescription = filingHistoryDescription;
    }

    public Map<String, Object> getFilingHistoryDescriptionValues() {
        return filingHistoryDescriptionValues;
    }

    public void setFilingHistoryDescriptionValues(Map<String, Object> filingHistoryDescriptionValues) {
        this.filingHistoryDescriptionValues = filingHistoryDescriptionValues;
    }

    public String getFilingHistoryId() {
        return filingHistoryId;
    }

    public void setFilingHistoryId(String filingHistoryId) {
        this.filingHistoryId = filingHistoryId;
    }

    public String getFilingHistoryType() {
        return filingHistoryType;
    }

    public void setFilingHistoryType(String filingHistoryType) {
        this.filingHistoryType = filingHistoryType;
    }

    public String getFilingHistoryCategory() {
        return filingHistoryCategory;
    }

    public void setFilingHistoryCategory(String filingHistoryCategory) { this.filingHistoryCategory = filingHistoryCategory; }

    public String getFilingHistoryCost() {
        return filingHistoryCost;
    }

    public void setFilingHistoryCost(String filingHistoryCost) {
        this.filingHistoryCost = filingHistoryCost;
    }

    public String getFilingHistoryBarcode() {
        return filingHistoryBarcode;
    }

    public void setFilingHistoryBarcode(String filingHistoryBarcode) {
        this.filingHistoryBarcode = filingHistoryBarcode;
    }

    @Override
    public String toString() { return new Gson().toJson(this); }
}
