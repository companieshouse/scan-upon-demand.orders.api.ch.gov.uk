package uk.gov.companieshouse.missingimagedelivery.orders.api.model;

import uk.gov.companieshouse.missingimagedelivery.orders.api.converter.EnumValueNameConverter;

public enum FilingHistoryCategory {
    ACCOUNTS() {
        @Override
        public ProductType getProductType() {
            return ProductType.MISSING_IMAGE_DELIVERY_ACCOUNTS;
        }
    },
    ANNUAL_RETURN() {
        @Override
        public ProductType getProductType() {
            return ProductType.MISSING_IMAGE_DELIVERY_ANNUAL_RETURN;
        }
    },
    CONFIRMATION_STATEMENT() {
        @Override
        public ProductType getProductType() {
            return ProductType.MISSING_IMAGE_DELIVERY_ANNUAL_RETURN;
        }
    },
    RETURN() {
        @Override
        public ProductType getProductType() {
            return ProductType.MISSING_IMAGE_DELIVERY_ANNUAL_RETURN;
        }
    },
    OFFICERS() {
        @Override
        public ProductType getProductType() {
            return ProductType.MISSING_IMAGE_DELIVERY_APPOINTMENT;
        }
    },
    OFFICER() {
        @Override
        public ProductType getProductType() {
            return ProductType.MISSING_IMAGE_DELIVERY_APPOINTMENT;
        }
    },
    ADDRESS() {
        @Override
        public ProductType getProductType() {
            return ProductType.MISSING_IMAGE_DELIVERY_REGISTERED_OFFICE;
        }
    },
    MORTGAGE() {
        @Override
        public ProductType getProductType() {
            return ProductType.MISSING_IMAGE_DELIVERY_MORTGAGE;
        }
    },
    INSOLVENCY() {
        @Override
        public ProductType getProductType() {
            return ProductType.MISSING_IMAGE_DELIVERY_LIQUIDATION;
        }
    },
    LIQUIDATION() {
        @Override
        public ProductType getProductType() {
            return ProductType.MISSING_IMAGE_DELIVERY_LIQUIDATION;
        }
    },
    INCORPORATION() {
        @Override
        public ProductType getProductType() {
            return ProductType.MISSING_IMAGE_DELIVERY_INCORPORATION;
        }
    },
    CHANGE_OF_NAME() {
        @Override
        public ProductType getProductType() {
            return ProductType.MISSING_IMAGE_DELIVERY_CHANGE_OF_NAME;
        }
    },
    CAPITAL() {
        @Override
        public ProductType getProductType() {
            return ProductType.MISSING_IMAGE_DELIVERY_CAPITAL;
        }
    },
    RESOLUTION() {
        @Override
        public ProductType getProductType() {
            return ProductType.MISSING_IMAGE_DELIVERY_MISC;
        }
    },
    MISCELLANEOUS() {
        @Override
        public ProductType getProductType() {
            return ProductType.MISSING_IMAGE_DELIVERY_MISC;
        }
    },
    AUDITORS() {
        @Override
        public ProductType getProductType() {
            return ProductType.MISSING_IMAGE_DELIVERY_MISC;
        }
    },
    GAZETTE() {
        @Override
        public ProductType getProductType() {
            return ProductType.MISSING_IMAGE_DELIVERY_MISC;
        }
    },
    OTHER() {
        @Override
        public ProductType getProductType() {
            return ProductType.MISSING_IMAGE_DELIVERY_MISC;
        }
    },
    CERTIFICATE() {
        @Override
        public ProductType getProductType() {
            return ProductType.MISSING_IMAGE_DELIVERY_MISC;
        }
    },
    DISSOLUTION() {
        @Override
        public ProductType getProductType() {
            return ProductType.MISSING_IMAGE_DELIVERY_MISC;
        }
    },
    REREGISTRATION() {
        @Override
        public ProductType getProductType() {
            return ProductType.MISSING_IMAGE_DELIVERY_MISC;
        }
    },
    HISTORICAL() {
        @Override
        public ProductType getProductType() {
            return ProductType.MISSING_IMAGE_DELIVERY_MISC;
        }
    },
    RESTORATION() {
        @Override
        public ProductType getProductType() {
            return ProductType.MISSING_IMAGE_DELIVERY_MISC;
        }
    },
    COURT_ORDER() {
        @Override
        public ProductType getProductType() {
            return ProductType.MISSING_IMAGE_DELIVERY_MISC;
        }
    },
    SOCIAL_LANDLORD() {
        @Override
        public ProductType getProductType() {
            return ProductType.MISSING_IMAGE_DELIVERY_MISC;
        }
    },
    CHANGE_OF_CONSTITUTION() {
        @Override
        public ProductType getProductType() {
            return ProductType.MISSING_IMAGE_DELIVERY_MISC;
        }
    },
    DOCUMENT_REPLACEMENT() {
        @Override
        public ProductType getProductType() {
            return ProductType.MISSING_IMAGE_DELIVERY_MISC;
        }
    },
    PERSONS_WITH_SIGNIFICANT_CONTROL() {
        @Override
        public ProductType getProductType() {
            return ProductType.MISSING_IMAGE_DELIVERY_MISC;
        }
    },
    ANNOTATION() {
        @Override
        public ProductType getProductType() {
            return ProductType.MISSING_IMAGE_DELIVERY_MISC;
        }
    },
    UNHANDLED_CATEGORY() {
        @Override
        public ProductType getProductType() {
            return null;
        }
    };

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
        return null;
    }
}
