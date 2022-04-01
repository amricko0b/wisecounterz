package xyz.amricko0b.wisecounterz.data;

import com.neovisionaries.i18n.CountryCode;

/**
 * Factory for {@link CountryCounter} DTO creation
 *
 * @param <T> actual implementation of DTO
 * @author amricko0b
 */
public abstract class CountryCounterFactory<T extends CountryCounter> {

    /**
     * Initial value for counter
     */
    protected static final Long DEFAULT_VALUE = Long.valueOf("1");

    /**
     * Creates actual implementation of DTO with default counter value
     *
     * @param countryCode code of the country fow which we create counter
     * @return actual implementation of DTO
     */
    public T createInitial(CountryCode countryCode) {
        final var counter = allocate();
        counter.setCountryCode(countryCode);
        counter.setCounter(DEFAULT_VALUE);

        return counter;
    }

    /**
     * @return actual implementation of DTO
     */
    public abstract T allocate();
}
