package xyz.amricko0b.wisecounterz.domain;

import com.neovisionaries.i18n.CountryCode;

/**
 * Domain logic for country codes.
 * Provides a way to create country codes from other values.
 *
 * @author amricko0b
 */
public interface CountryCodeFactory {

    /**
     * Build an enum value from raw string.
     *
     * @param rawCountryCode country code as string
     * @return an enum value
     * @throws NoSuchCountryException when country code is unsupported
     */
    CountryCode createBy(String rawCountryCode);
}
