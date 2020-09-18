package uk.gov.companieshouse.scanupondemand.orders.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * An instance of this represents a scan upon demand item.
 */

@Document(collection = "scan_upon_demands")
public class ScanUponDemandItem {

    @Id
    private String id;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private ScanUponDemandItemData data = new ScanUponDemandItemData();

    private String userId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        data.setId(id);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ScanUponDemandItemData getData() {
        return data;
    }

    public void setData(ScanUponDemandItemData data) {
        this.data = data;
    }

    public void setCompanyName(String companyName) {
        data.setCompanyName(companyName);
    }

    public String getCompanyNumber() {
        return data.getCompanyNumber();
    }

    public void setCompanyNumber(String companyNumber) {
        data.setCompanyNumber(companyNumber);
    }

    public void setCustomerReference(String companyReference) {
        data.setCustomerReference(companyReference);
    }

    public void setEtag(String etag) {
        data.setEtag(etag);
    }

    public void setItemOptions(FilingHistoryDocument itemOptions) {
        data.setItemOptions(itemOptions);
    }

    public void setKind(String kind) {
        data.setKind(kind);
    }

    public void setLinks(Links links) {
        data.setLinks(links);
    }

    public void setQuantity(Integer quantity) {
        data.setQuantity(quantity);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
