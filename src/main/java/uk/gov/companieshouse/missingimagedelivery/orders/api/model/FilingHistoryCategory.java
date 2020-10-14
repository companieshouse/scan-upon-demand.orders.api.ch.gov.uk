package uk.gov.companieshouse.missingimagedelivery.orders.api.model;

import uk.gov.companieshouse.missingimagedelivery.orders.api.converter.EnumValueNameConverter;

public enum FilingHistoryCategory {
    ACCOUNTS(ProductType.MISSING_IMAGE_DELIVERY_ACCOUNTS),
    ANNUAL_RETURN(ProductType.MISSING_IMAGE_DELIVERY_ANNUAL_RETURN),
    CONFIRMATION_STATEMENT(ProductType.MISSING_IMAGE_DELIVERY_ANNUAL_RETURN),
    RETURN(ProductType.MISSING_IMAGE_DELIVERY_ANNUAL_RETURN),
    OFFICERS(ProductType.MISSING_IMAGE_DELIVERY_APPOINTMENT),
    OFFICER(ProductType.MISSING_IMAGE_DELIVERY_APPOINTMENT),
    ADDRESS(ProductType.MISSING_IMAGE_DELIVERY_REGISTERED_OFFICE),
    MORTGAGE(ProductType.MISSING_IMAGE_DELIVERY_MORTGAGE),
    INSOLVENCY(ProductType.MISSING_IMAGE_DELIVERY_LIQUIDATION),
    LIQUIDATION(ProductType.MISSING_IMAGE_DELIVERY_LIQUIDATION),
    INCORPORATION(ProductType.MISSING_IMAGE_DELIVERY_NEW_INCORPORATION),
    CHANGE_OF_NAME(ProductType.MISSING_IMAGE_DELIVERY_CHANGE_OF_NAME),
    CAPITAL(ProductType.MISSING_IMAGE_DELIVERY_CAPITAL),
    RESOLUTION(ProductType.MISSING_IMAGE_DELIVERY_MISC),
    MISCELLANEOUS(ProductType.MISSING_IMAGE_DELIVERY_MISC),
    AUDITORS(ProductType.MISSING_IMAGE_DELIVERY_MISC),
    GAZETTE(ProductType.MISSING_IMAGE_DELIVERY_MISC),
    OTHER(ProductType.MISSING_IMAGE_DELIVERY_MISC),
    CERTIFICATE(ProductType.MISSING_IMAGE_DELIVERY_MISC),
    DISSOLUTION(ProductType.MISSING_IMAGE_DELIVERY_MISC),
    REREGISTRATION(ProductType.MISSING_IMAGE_DELIVERY_MISC),
    HISTORICAL(ProductType.MISSING_IMAGE_DELIVERY_MISC),
    RESTORATION(ProductType.MISSING_IMAGE_DELIVERY_MISC),
    COURT_ORDER(ProductType.MISSING_IMAGE_DELIVERY_MISC),
    SOCIAL_LANDLORD(ProductType.MISSING_IMAGE_DELIVERY_MISC),
    CHANGE_OF_CONSTITUTION(ProductType.MISSING_IMAGE_DELIVERY_MISC),
    DOCUMENT_REPLACEMENT(ProductType.MISSING_IMAGE_DELIVERY_MISC),
    PERSONS_WITH_SIGNIFICANT_CONTROL(ProductType.MISSING_IMAGE_DELIVERY_MISC),
    ANNOTATION(ProductType.MISSING_IMAGE_DELIVERY_MISC),
    UNHANDLED_CATEGORY(null);

    private ProductType productType;

    private FilingHistoryCategory(ProductType productType) {
        this.productType = productType;
    }

    public static FilingHistoryCategory enumValueOf(String v) {
        try {
            return FilingHistoryCategory.valueOf(EnumValueNameConverter.convertEnumValueJsonToName(v));
        } catch (IllegalArgumentException ex) {
            return UNHANDLED_CATEGORY;
        }
    }

    @Override
    public String toString() {
        return EnumValueNameConverter.convertEnumValueNameToJson(this);
    }

    public ProductType getProductType(){
        return this.productType;
    }
}
