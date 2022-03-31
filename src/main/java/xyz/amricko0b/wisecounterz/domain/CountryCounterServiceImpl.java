package xyz.amricko0b.wisecounterz.domain;

import com.neovisionaries.i18n.CountryCode;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Basic implementation of {@link CountryCounterService}.
 * Acts like a place to domain logic around counters storage.
 * Also acts like a transaction boundary.
 *
 * @author amricko0b
 */
@Service
public class CountryCounterServiceImpl implements CountryCounterService {

    /**
     * Reading all available counters in one TX.
     * <p>
     * {@inheritDoc}
     */
    @Override
    public Map<CountryCode, Long> getAllCounters() {
        return Map.of();
    }

    /**
     * Increment counter exclusively.
     * <p>
     * {@inheritDoc}
     */
    @Override
    public Long incrementCounterBy(CountryCode countryCode) {
        return Long.getLong("0");
    }
}
