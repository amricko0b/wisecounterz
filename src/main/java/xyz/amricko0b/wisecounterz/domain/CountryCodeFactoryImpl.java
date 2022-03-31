package xyz.amricko0b.wisecounterz.domain;

import com.neovisionaries.i18n.CountryCode;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Basic implementation of {@link CountryCodeFactory}.
 *
 * @author amricko0b
 */
@Service
public class CountryCodeFactoryImpl implements CountryCodeFactory {

    private static final int COUNTRY_CODE_LENGTH = 2;

    /**
     * Leverage the functionality of nv-i18n library.
     * This method also applies suitable validations.
     * <p>
     * Library considers codes longer than 2 characters to be alpha3, but we need alpha2, so we add additional constraint.
     * <p>
     * {@inheritDoc}
     */
    @Override
    public CountryCode createBy(String rawCountryCode) {
        if (rawCountryCode.length() != COUNTRY_CODE_LENGTH) {
            throw new NoSuchCountryException(rawCountryCode);
        }

        return Optional.ofNullable(CountryCode.getByCodeIgnoreCase(rawCountryCode))
                .orElseThrow(() -> new NoSuchCountryException(rawCountryCode));
    }
}
