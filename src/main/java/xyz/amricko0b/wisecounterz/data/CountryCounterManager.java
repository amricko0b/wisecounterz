package xyz.amricko0b.wisecounterz.data;

import com.neovisionaries.i18n.CountryCode;

import java.util.Optional;

/**
 * Data Manager for country counters.
 *
 * @author amricko0b
 */
public interface CountryCounterManager extends DataManager<CountryCounter> {

    /**
     * Find a counter by country.
     * The contract of this method is EXCLUSIVE.
     * Another concurrent threads must wait.
     *
     * @param countryCode code of country for which we are looking up counter
     * @return counter or empty
     */
    Optional<CountryCounter> findByExclusive(CountryCode countryCode);

    /**
     * Increment counter and return current value after operation
     *
     * @param countryCounter counter to increment
     * @return value after increment
     */
    Long increment(CountryCounter countryCounter);

    /**
     * Creates counter for provided country.
     *
     * @param countryCode country code for initial counter
     */
    void initiate(CountryCode countryCode);
}
