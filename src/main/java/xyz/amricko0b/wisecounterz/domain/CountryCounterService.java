package xyz.amricko0b.wisecounterz.domain;

import com.neovisionaries.i18n.CountryCode;

import java.util.Map;

/**
 * Domain logic around country counters.
 *
 * @author amricko0b
 */
public interface CountryCounterService {

    /**
     * Find all available counters by country code
     *
     * @return map "country code -> current value"
     */
    Map<CountryCode, Long> getAllCounters();

    /**
     * Increment counter for provided country
     *
     * @param countryCode code of the country, which counter supposed to be increased
     * @return current value after incrementation
     */
    Long incrementCounterBy(CountryCode countryCode);
}
