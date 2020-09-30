package uk.gov.companieshouse.missingimagedelivery.orders.api.model;

import com.google.gson.Gson;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * An instance of this represents a missing image delivery item.
 */

@Document(collection = "missing_image_deliveries")
public class MissingImageDeliveryItem {

    @Id
    private String id;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private MissingImageDeliveryItemData data = new MissingImageDeliveryItemData();

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

    public MissingImageDeliveryItemData getData() {
        return data;
    }

    public void setData(MissingImageDeliveryItemData data) {
        this.data = data;
    }

    public String getCompanyName() {
        return data.getCompanyName();
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

    public String getCustomerReference() {
        return data.getCustomerReference();
    }

    public void setCustomerReference(String companyReference) {
        data.setCustomerReference(companyReference);
    }

    public void setDescription(String description) {
        data.setDescription(description);
    }

    public void setDescriptionIdentifier(String descriptionIdentifier) {
        data.setDescriptionIdentifier(descriptionIdentifier);
    }

    public void setDescriptionValues(Map<String, String> descriptionValues) {
        data.setDescriptionValues(descriptionValues);
    }

    public void setEtag(String etag) {
        data.setEtag(etag);
    }

    public void setItemOptions(MissingImageDeliveryItemOptions itemOptions) { data.setItemOptions(itemOptions); }

    public MissingImageDeliveryItemOptions getItemOptions() {
        return data.getItemOptions();
    }

    public void setLinks(Links links) {
        data.setLinks(links);
    }
    public String getEtag() {
        return data.getEtag();
    }

    public Links getLinks() {
        return data.getLinks();
    }

    public void setPostageCost(String postageCost) { data.setPostageCost(postageCost); }

    public void setQuantity(Integer quantity) {
        data.setQuantity(quantity);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostageCost() {
        return data.getPostageCost();
    }

    public Integer getQuantity() {
        return data.getQuantity();
    }

    public String getTotalItemCost() {
        return data.getTotalItemCost();
    }

    public void setTotalItemCost(String totalItemCost) {
        data.setTotalItemCost(totalItemCost);
    }

    public List<ItemCosts> getItemCosts() {
        return data.getItemCosts();
    }

    public void setItemCosts(List<ItemCosts> itemCosts) {
        data.setItemCosts(itemCosts);
    }

    public Boolean isPostalDelivery() {
        return data.isPostalDelivery();
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
