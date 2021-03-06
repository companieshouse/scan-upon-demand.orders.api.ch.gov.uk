package uk.gov.companieshouse.missingimagedelivery.orders.api.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

class FilingHistoryCategoryTest {
    private static final String ACCOUNTS = "accounts";
    private static final String[] ANNUAL_RETURN = {"annual-return", "confirmation-statement", "return"};
    private static final String[] APPOINTMENT = {"officers", "officer"};
    private static final String ADDRESS = "address";
    private static final String MORTGAGE = "mortgage";
    private static final String[] LIQUIDATION = {"insolvency", "liquidation"};
    private static final String INCORPORATION = "incorporation";
    private static final String CHANGE_OF_NAME = "change-of-name";
    private static final String CAPITAL = "capital";
    private static final String[] MISC = {"resolution", "miscellaneous", "auditors", "gazette", "other", "certificate",
            "dissolution", "reregistration", "historical", "restoration", "court-order", "social-landlord",
            "change-of-constitution", "document-replacement", "persons-with-significant-control", "annotation"};
    private static final String UNHANDLED_CATEGORY = "unhandled-category";

    @Test
    void returnsNullForIncorrectUnknownOrUnhandledCategory(){
        assertThat(FilingHistoryCategory.enumValueOf(UNHANDLED_CATEGORY).getProductType(), is(nullValue()));
    }

    @Test
    void mapsAccountsCategorySuccessfully(){
        assertThat(FilingHistoryCategory.enumValueOf(ACCOUNTS).getProductType(), is(ProductType.MISSING_IMAGE_DELIVERY_ACCOUNTS));
        assertThat(FilingHistoryCategory.ACCOUNTS.toString(), is("accounts"));
    }

    @Test
    void mapsAnnualReturnsCategoriesSuccessfully(){
        Arrays.stream(ANNUAL_RETURN).forEach(c -> assertThat(FilingHistoryCategory.enumValueOf(c).getProductType(), is(ProductType.MISSING_IMAGE_DELIVERY_ANNUAL_RETURN)));
        assertThat(FilingHistoryCategory.ANNUAL_RETURN.toString(), is("annual-return"));
        assertThat(FilingHistoryCategory.CONFIRMATION_STATEMENT.toString(), is("confirmation-statement"));
        assertThat(FilingHistoryCategory.RETURN.toString(), is("return"));
    }

    @Test
    void mapsAppointmentCategoriesSuccessfully(){
        Arrays.stream(APPOINTMENT).forEach(c -> assertThat(FilingHistoryCategory.enumValueOf(c).getProductType(), is(ProductType.MISSING_IMAGE_DELIVERY_APPOINTMENT)));
        assertThat(FilingHistoryCategory.OFFICER.toString(), is("officer"));
        assertThat(FilingHistoryCategory.OFFICERS.toString(), is("officers"));
    }

    @Test
    void mapsAddressCategorySuccessfully(){
        assertThat(FilingHistoryCategory.enumValueOf(ADDRESS).getProductType(), is(ProductType.MISSING_IMAGE_DELIVERY_REGISTERED_OFFICE));
        assertThat(FilingHistoryCategory.ADDRESS.toString(), is("address"));
    }

    @Test
    void mapsMortgageCategorySuccessfully(){
        assertThat(FilingHistoryCategory.enumValueOf(MORTGAGE).getProductType(), is(ProductType.MISSING_IMAGE_DELIVERY_MORTGAGE));
        assertThat(FilingHistoryCategory.MORTGAGE.toString(), is("mortgage"));
    }

    @Test
    void mapsLiquidationCategoriesSuccessfully(){
        Arrays.stream(LIQUIDATION).forEach(c -> assertThat(FilingHistoryCategory.enumValueOf(c).getProductType(), is(ProductType.MISSING_IMAGE_DELIVERY_LIQUIDATION)));
        assertThat(FilingHistoryCategory.LIQUIDATION.toString(), is("liquidation"));
        assertThat(FilingHistoryCategory.INSOLVENCY.toString(), is("insolvency"));
    }

    @Test
    void mapsIncorporationCategorySuccessfully(){
        assertThat(FilingHistoryCategory.enumValueOf(INCORPORATION).getProductType(), is(ProductType.MISSING_IMAGE_DELIVERY_NEW_INCORPORATION));
        assertThat(FilingHistoryCategory.INCORPORATION.toString(), is("incorporation"));
    }

    @Test
    void mapsChangeOfNameCategorySuccessfully(){
        assertThat(FilingHistoryCategory.enumValueOf(CHANGE_OF_NAME).getProductType(), is(ProductType.MISSING_IMAGE_DELIVERY_CHANGE_OF_NAME));
        assertThat(FilingHistoryCategory.CHANGE_OF_NAME.toString(), is("change-of-name"));
    }

    @Test
    void mapsCapitalCategorySuccessfully(){
        assertThat(FilingHistoryCategory.enumValueOf(CAPITAL).getProductType(), is(ProductType.MISSING_IMAGE_DELIVERY_CAPITAL));
        assertThat(FilingHistoryCategory.CAPITAL.toString(), is("capital"));
    }

    @Test
    void mapsMiscellaneousCategoriesSuccessfully(){
        Arrays.stream(MISC).forEach(c -> assertThat(FilingHistoryCategory.enumValueOf(c).getProductType(), is(ProductType.MISSING_IMAGE_DELIVERY_MISC)));
        assertThat(FilingHistoryCategory.RESOLUTION.toString(), is("resolution"));
        assertThat(FilingHistoryCategory.MISCELLANEOUS.toString(), is("miscellaneous"));
        assertThat(FilingHistoryCategory.AUDITORS.toString(), is("auditors"));
        assertThat(FilingHistoryCategory.GAZETTE.toString(), is("gazette"));
        assertThat(FilingHistoryCategory.OTHER.toString(), is("other"));
        assertThat(FilingHistoryCategory.CERTIFICATE.toString(), is("certificate"));
        assertThat(FilingHistoryCategory.DISSOLUTION.toString(), is("dissolution"));
        assertThat(FilingHistoryCategory.REREGISTRATION.toString(), is("reregistration"));
        assertThat(FilingHistoryCategory.HISTORICAL.toString(), is("historical"));
        assertThat(FilingHistoryCategory.RESTORATION.toString(), is("restoration"));
        assertThat(FilingHistoryCategory.COURT_ORDER.toString(), is("court-order"));
        assertThat(FilingHistoryCategory.SOCIAL_LANDLORD.toString(), is("social-landlord"));
        assertThat(FilingHistoryCategory.CHANGE_OF_CONSTITUTION.toString(), is("change-of-constitution"));
        assertThat(FilingHistoryCategory.DOCUMENT_REPLACEMENT.toString(), is("document-replacement"));
        assertThat(FilingHistoryCategory.PERSONS_WITH_SIGNIFICANT_CONTROL.toString(), is("persons-with-significant-control"));
        assertThat(FilingHistoryCategory.ANNOTATION.toString(), is("annotation"));
    }
}
