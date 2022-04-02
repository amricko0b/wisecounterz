package xyz.amricko0b.wisecounterz.domain;

import com.neovisionaries.i18n.CountryCode;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Basic implementation of {@link CountryCodeSanitizer}.
 *
 * @author amricko0b
 */
@Service
public class CountryCodeSanitizerImpl implements CountryCodeSanitizer {

    private static final int COUNTRY_CODE_LENGTH = 2;

    /**
     * Leverage the functionality of nv-i18n library.
     * This method also applies suitable validations.
     * <p>
     * {@inheritDoc}
     */
    @Override
    public CountryCode sanitize(String rawCountryCode) {

        // Library considers codes longer than 2 characters to be alpha3, but we need alpha2, so we add additional constraint.
        if (rawCountryCode.length() != COUNTRY_CODE_LENGTH) {
            throw new NoSuchCountryException(rawCountryCode);
        }

        return Optional.ofNullable(CountryCode.getByCodeIgnoreCase(rawCountryCode))
                .orElseThrow(() -> new NoSuchCountryException(rawCountryCode));
    }
}
