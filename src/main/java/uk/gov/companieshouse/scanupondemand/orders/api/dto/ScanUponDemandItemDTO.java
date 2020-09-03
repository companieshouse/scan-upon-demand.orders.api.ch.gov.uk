package uk.gov.companieshouse.scanupondemand.orders.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.Gson;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * An instance of this represents the JSON serializable certificate item for use
 * in REST requests and responses.
 */
@JsonPropertyOrder(alphabetic = true)
public class ScanUponDemandItemDTO extends ItemDTO {

	@Null
	@JsonProperty("company_name")
	private String companyName;

	@NotNull
	@JsonProperty("company_number")
	private String companyNumber;

	@JsonProperty("customer_reference")
	private String customerReference;

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyNumber(String companyNumber) {
		this.companyNumber = companyNumber;
	}

	public String getCompanyNumber() {
		return companyNumber;
	}

	public void setCustomerReference(String customerReference) {
		this.customerReference = customerReference;
	}

	public String getCustomerReference() {
		return customerReference;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

}
