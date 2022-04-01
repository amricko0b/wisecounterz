package xyz.amricko0b.wisecounterz.data.jpa.converter;

import com.neovisionaries.i18n.CountryCode;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Attribute converter for {@link CountryCode}.
 * Uses alpha2 representation as value for database column.
 *
 * @author amricko0b
 */
@Converter
public class CountryCodeAttributeConverter implements AttributeConverter<CountryCode, String> {

    @Override
    public String convertToDatabaseColumn(CountryCode attribute) {
        return attribute.getAlpha2().toLowerCase();
    }

    @Override
    public CountryCode convertToEntityAttribute(String dbData) {
        return CountryCode.getByCodeIgnoreCase(dbData);
    }
}
