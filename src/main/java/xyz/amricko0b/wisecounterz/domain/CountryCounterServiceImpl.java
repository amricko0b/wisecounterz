package xyz.amricko0b.wisecounterz.domain;

import com.neovisionaries.i18n.CountryCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
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
@Slf4j
@Service
@RequiredArgsConstructor
public class CountryCounterServiceImpl implements CountryCounterService {

    private static final Collector<CountryCounter, ?, Map<CountryCode, Long>> COUNTRY_COUNTER_TO_MAP_COLLECTOR =
            Collectors.toMap(
                    CountryCounter::getCountryCode,
                    CountryCounter::getCounter
            );

    private final CountryCounterManager countryCounterManager;

    private final CountryCodeSanitizer countryCodeSanitizer;

    /**
     * Read all counters and place them in map
     * {@inheritDoc}
     */
    @Override
    public Map<CountryCode, Long> getAllCounters() {

        log.info("All counters info requested");

        return countryCounterManager.findAll()
                .stream()
                .collect(COUNTRY_COUNTER_TO_MAP_COLLECTOR);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<CountryCode, Long> incrementCounterByRaw(String countryCode) {

        log.info("Try to increment counter for {}", countryCode);

        final var sanitized = countryCodeSanitizer.sanitize(countryCode);
        final var current = countryCounterManager.incrementBy(sanitized);
        return Pair.of(sanitized, current);
    }
}
