package uk.gov.companieshouse.missingimagedelivery.orders.api.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;

/**
 * Unit tests the {@link DescriptionProviderService} class.
 */
class DescriptionProviderServiceTest {

    private static final String COMPANY_NUMBER = "00006400";
    private static final String EXPECTED_DESCRIPTION = "missing image delivery for company " + COMPANY_NUMBER;

    @Test
    @DisplayName("Provides expected description")
    void getDescriptionProvidesExpectedDescription() {
        final DescriptionProviderService provider = new DescriptionProviderService();
        assertThat(provider.getDescription(COMPANY_NUMBER), is(EXPECTED_DESCRIPTION));
    }

    @Test
    @DisplayName("Returns null when orders description file not found")
    void getDescriptionFileNotFoundReturnsNull() {
        final DescriptionProviderService provider = new DescriptionProviderService(new File("notfound.yaml"));
        assertThat(provider.getDescription(COMPANY_NUMBER), is(nullValue()));
    }

    @Test
    @DisplayName("Returns null when missing image delivery descriptions section not found in orders description file")
    void getDescriptionIncorrectMissingImageDeliveryDescriptionsKeyReturnsNull() {
        final File file = getFile("/api-enumerations/orders_descriptions_incorrect_missing_image_delivery_descriptions_key.yaml");
        final DescriptionProviderService provider =
                new DescriptionProviderService(file);
        assertThat(provider.getDescription(COMPANY_NUMBER), is(nullValue()));
    }

    @Test
    @DisplayName("Returns null when company missing image delivery description not found in orders description file")
    void getDescriptionIncorrectCompanyMissingImageDeliveryCopyDescriptionKeyReturnsNull() {
        final File file = getFile("/api-enumerations/orders_descriptions_incorrect_company_missing_image_delivery_description_key.yaml");
        final DescriptionProviderService provider =
                new DescriptionProviderService(file);
        assertThat(provider.getDescription(COMPANY_NUMBER), is(nullValue()));
    }

    /**
     * Gets the file from the test resources directory
     * @param filePath the relative file path of the file within the test resources directory
     * @return the {@link File} representing the test resource file at the file path
     */
    private File getFile(final String filePath) {
        final File resourcesDirectory = new File("src/test/resources");
        return new File(resourcesDirectory.getAbsolutePath() + filePath);
    }
}

