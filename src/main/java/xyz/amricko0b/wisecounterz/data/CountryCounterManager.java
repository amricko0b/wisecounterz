package xyz.amricko0b.wisecounterz.data;

import com.neovisionaries.i18n.CountryCode;

/**
 * Data Manager for country counters.
 *
 * @author amricko0b
 */
public interface CountryCounterManager extends DataManager<CountryCounter> {

    /**
     * Increment counter and return current value after operation
     *
     * @param countryCode counter to increment
     * @return value after increment
     */
    Long incrementBy(CountryCode countryCode);
}
