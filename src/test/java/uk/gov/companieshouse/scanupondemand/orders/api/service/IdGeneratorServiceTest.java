package uk.gov.companieshouse.scanupondemand.orders.api.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;

class IdGeneratorServiceTest {

    @Test
    @DisplayName("autoGenerateId returns in the format SCD-######-######")
    void autoGenerateIdGenerateIdInCorrectFormat() {

        final IdGeneratorService idGeneratorService = new IdGeneratorService();

        final String id = idGeneratorService.autoGenerateId();

        assertTrue(id.matches("^SCD-\\d{6}-\\d{6}$")); ;

    }

}
