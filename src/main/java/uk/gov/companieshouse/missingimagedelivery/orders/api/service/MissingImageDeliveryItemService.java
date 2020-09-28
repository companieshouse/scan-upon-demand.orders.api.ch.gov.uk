package uk.gov.companieshouse.missingimagedelivery.orders.api.service;

import org.springframework.stereotype.Service;
import uk.gov.companieshouse.missingimagedelivery.orders.api.model.ItemCostCalculation;
import uk.gov.companieshouse.missingimagedelivery.orders.api.model.MissingImageDeliveryItem;
import uk.gov.companieshouse.missingimagedelivery.orders.api.repository.MissingImageDeliveryItemRepository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Service for the management and storage of missing image delivery items.
 */
@Service
public class MissingImageDeliveryItemService {

    private final MissingImageDeliveryItemRepository repository;
    private final IdGeneratorService idGenerator;
    private final EtagGeneratorService etagGenerator;
    private final LinksGeneratorService linksGenerator;
    private final MissingImageDeliveryCostCalculatorService calculator;

    private static final String KIND = "item#missing-image-delivery";

    public MissingImageDeliveryItemService(final MissingImageDeliveryItemRepository repository,
                                     final IdGeneratorService idGenerator,
                                     final EtagGeneratorService etagGenerator,
                                     final LinksGeneratorService linksGenerator,
                                     final MissingImageDeliveryCostCalculatorService calculator) {
        this.repository = repository;
        this.idGenerator = idGenerator;
        this.etagGenerator = etagGenerator;
        this.linksGenerator = linksGenerator;
        this.calculator = calculator;
    }

    /**
     * Creates the missing image delivery item in the database.
     *
     * @param item the item to be created
     * @return the created item
     */
    public MissingImageDeliveryItem createMissingImageDeliveryItem(final MissingImageDeliveryItem item) {
        item.setId(idGenerator.autoGenerateId());
        setCreationDateTimes(item);
        item.setEtag(etagGenerator.generateEtag());
        item.setLinks(linksGenerator.generateLinks(item.getId()));
        item.setKind(KIND);
        final ItemCostCalculation costs = calculator.calculateCosts(item.getQuantity());
        item.setItemCosts(costs.getItemCosts());
        item.setPostageCost(costs.getPostageCost());
        item.setTotalItemCost(costs.getTotalItemCost());
        return repository.save(item);
    }

    /**
     * Sets the created at and updated at date time 'timestamps' to now.
     *
     * @param item the item to be 'timestamped'
     */
    void setCreationDateTimes(final MissingImageDeliveryItem item) {
        final LocalDateTime now = LocalDateTime.now();
        item.setCreatedAt(now);
        item.setUpdatedAt(now);
    }

    /**
     * Returns missing image delivery item
     * @param id id of the requested item
     * @return
     */
    public Optional<MissingImageDeliveryItem> getMissingImageDeliveryItemById(String id) {
        return repository.findById(id);
    }
}
