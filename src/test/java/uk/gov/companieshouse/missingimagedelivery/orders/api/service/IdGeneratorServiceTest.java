package uk.gov.companieshouse.missingimagedelivery.orders.api.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;

class IdGeneratorServiceTest {

    @Test
    @DisplayName("autoGenerateId returns in the format MID-######-######")
    void autoGenerateIdGenerateIdInCorrectFormat() {

        final IdGeneratorService idGeneratorService = new IdGeneratorService();

        final String id = idGeneratorService.autoGenerateId();

        assertTrue(id.matches("^MID-\\d{6}-\\d{6}$")); ;

    }

}
