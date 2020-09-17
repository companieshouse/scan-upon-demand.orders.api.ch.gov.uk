package uk.gov.companieshouse.scanupondemand.orders.api.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.gov.companieshouse.scanupondemand.orders.api.model.Links;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class LinksGeneratorServiceTest {

    private static final String SELF_PATH = "/orderable/scans-upon-demand";
    private static final String ITEM_ID = "SCD-462515-995726";

    @Test
    @DisplayName("Generates links correctly with valid inputs")
    void generatesLinksCorrectlyWithValidInputs() {

        final LinksGeneratorService generatorUnderTest = new LinksGeneratorService(SELF_PATH);

        final Links links = generatorUnderTest.generateLinks(ITEM_ID);

        assertThat(links.getSelf(), is(SELF_PATH + "/" + ITEM_ID));

    }

    @Test
    @DisplayName("Unpopulated scan upon demand id argument results in an IllegalArgumentException")
    void itemIdMustNotBeBlank() {

        final LinksGeneratorService generatorUnderTest = new LinksGeneratorService(SELF_PATH);

        final IllegalArgumentException exception =
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> generatorUnderTest.generateLinks(null));

        assertThat(exception.getMessage(), is("Scan Upon Demand Item ID not populated!"));

    }

    @Test
    @DisplayName("Unpopulated path to self URI results in an IllegalArgumentException")
    void selfPathMustNotBeBlank() {

        final IllegalArgumentException exception =
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> new LinksGeneratorService(null));

        assertThat(exception.getMessage(), is("Path to self URI not configured!"));
    }

}
