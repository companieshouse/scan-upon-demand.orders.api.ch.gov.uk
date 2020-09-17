package uk.gov.companieshouse.scanupondemand.orders.api.model;

import com.google.gson.Gson;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

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

    public void setEtag(String etag) {
        data.setEtag(etag);
    }

    public void setItemOptions(ScanUponDemandItemOptions itemOptions) { data.setItemOptions(itemOptions); }

    public void setLinks(Links links) {
        data.setLinks(links);
    }
	public String getEtag() {
		return data.getEtag();
	}

	public void setEtag(String etag) {
		data.setEtag(etag);
	}

	public Links getLinks() {
		return data.getLinks();
	}

	public void setLinks(Links links) {
		data.setLinks(links);
	}

    public void setPostageCost(String postageCost) { data.setPostageCost(postageCost); }

    public void setQuantity(Integer quantity) {
        data.setQuantity(quantity);
    }
	public String getPostageCost() {
		return data.getPostageCost();
	}

	public void setPostageCost(String postageCost) {
		data.setPostageCost(postageCost);
	}

	public Integer getQuantity() {
		return data.getQuantity();
	}

	public void setQuantity(Integer quantity) {
		data.setQuantity(quantity);
	}

    public String getUserId() {
        return userId;
    }
	public String getTotalItemCost() {
		return data.getTotalItemCost();
	}

	public void setTotalItemCost(String totalItemCost) {
		data.setTotalItemCost(totalItemCost);
	}

	public String getUserId() {
		return userId;
	}

    public void setUserId(String userId) {
        this.userId = userId;
    }

	public List<ItemCosts> getItemCosts() {
		return data.getItemCosts();
	}

	public void setItemCosts(List<ItemCosts> itemCosts) {
		data.setItemCosts(itemCosts);
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
