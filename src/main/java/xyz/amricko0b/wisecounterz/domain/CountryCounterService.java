package xyz.amricko0b.wisecounterz.domain;

import com.neovisionaries.i18n.CountryCode;
import org.springframework.data.util.Pair;

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
     * Try to increment counter for provided raw country code.
     *
     * @param countryCode country code as string. Can be invalid
     * @return pair of country code and current value after increment
     * @throws NoSuchCountryException when code is invalid
     */
    Pair<CountryCode, Long> incrementCounterByRaw(String countryCode);
}
