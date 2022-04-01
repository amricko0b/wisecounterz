package xyz.amricko0b.wisecounterz.domain;

import com.neovisionaries.i18n.CountryCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import xyz.amricko0b.wisecounterz.data.CountryCounter;
import xyz.amricko0b.wisecounterz.data.CountryCounterManager;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Basic implementation of {@link CountryCounterService}.
 * Acts like a place to domain logic around counters storage.
 * Also acts like a transaction boundary.
 *
 * @author amricko0b
 */
@Service
@RequiredArgsConstructor
public class CountryCounterServiceImpl implements CountryCounterService {

    private static final Collector<CountryCounter, ?, Map<CountryCode, Long>> COUNTRY_COUNTER_TO_MAP_COLLECTOR =
            Collectors.toMap(
                    CountryCounter::getCountryCode,
                    CountryCounter::getCounter
            );

    private final CountryCounterManager countryCounterManager;

    /**
     * Reading all available counters in one TX.
     * Always starts new transaction!
     * <p>
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Map<CountryCode, Long> getAllCounters() {
        return countryCounterManager.findAll()
                .stream()
                .collect(COUNTRY_COUNTER_TO_MAP_COLLECTOR);
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
