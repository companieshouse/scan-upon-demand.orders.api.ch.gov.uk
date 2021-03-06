package uk.gov.companieshouse.missingimagedelivery.orders.api.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.data.convert.ReadingConverter;

import static uk.gov.companieshouse.missingimagedelivery.orders.api.converter.EnumValueNameConverter.convertEnumValueJsonToName;

@ReadingConverter
public final class StringToEnumConverterFactory implements ConverterFactory<String, Enum> {

    public <T extends Enum> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToEnumConverter(targetType);
    }

    private final class StringToEnumConverter<T extends Enum> implements Converter<String, T> {
        private Class<T> enumType;

        public StringToEnumConverter(Class<T> enumType) {
            this.enumType = enumType;
        }

        @SuppressWarnings("squid:S1905") // SonarQube false positive - cast is necessary.
        public T convert(String source) {
            return (T) Enum.valueOf(this.enumType, convertEnumValueJsonToName(source));
        }
    }
}
