package xyz.amricko0b.wisecounterz.data.jpa;

import com.neovisionaries.i18n.CountryCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import xyz.amricko0b.wisecounterz.data.CountryCounter;
import xyz.amricko0b.wisecounterz.data.CountryCounterManager;
import xyz.amricko0b.wisecounterz.data.jpa.entity.CountryCounterEntity;
import xyz.amricko0b.wisecounterz.data.jpa.repository.CountryCounterRepository;

/**
 * JPA implementation of {@link CountryCounterManager}.
 * Uses Spring Data JPA and spring transactions.
 *
 * @author amricko0b
 */
@Component
@RequiredArgsConstructor
public class CountryCounterJpaManager extends AbstractJpaManager<CountryCounter, CountryCounterEntity, CountryCode>
        implements CountryCounterManager {

    private final CountryCounterRepository repository;

    private final CountryCounterEntityFactory factory;

    /**
     * Supports current transaction or runs new.
     * <p>
     * {@inheritDoc}
     *
     * @param countryCode
     */
    @Override
    @Transactional
    public Long incrementBy(CountryCode countryCode) {

        repository.lockOn(countryCode.getNumeric());

        return repository.findById(countryCode)
                .map(counter -> {
                    final var incremented = counter.getCounter() + 1;
                    counter.setCounter(incremented);
                    repository.save(counter);
                    return incremented;
                }).orElseGet(() -> {
                    final var fresh = factory.createInitial(countryCode);
                    repository.save(fresh);
                    return fresh.getCounter();
                });
    }

    @Override
    protected CrudRepository<CountryCounterEntity, CountryCode> getRepository() {
        return repository;
    }
}
