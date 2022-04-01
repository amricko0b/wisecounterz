package xyz.amricko0b.wisecounterz.data.jpa;

import com.neovisionaries.i18n.CountryCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import xyz.amricko0b.wisecounterz.data.CountryCounter;
import xyz.amricko0b.wisecounterz.data.CountryCounterManager;
import xyz.amricko0b.wisecounterz.data.jpa.entity.CountryCounterEntity;
import xyz.amricko0b.wisecounterz.data.jpa.repository.CountryCounterRepository;

import java.util.Optional;

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
     * Expected to be called from existed transaction.
     * Otherwise, will fail.
     * <p>
     * {@inheritDoc}
     */
    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Optional<CountryCounter> findByExclusive(CountryCode countryCode) {
        return Optional.empty();
    }

    /**
     * Supports current transaction or runs new.
     * <p>
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Long increment(CountryCounter countryCounter) {
        return null;
    }

    /**
     * Supports current transaction or runs new.
     * <p>
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void initiate(CountryCode countryCode) {
        final var entity = factory.createInitial(countryCode);
        getRepository().save(entity);
    }

    @Override
    protected CrudRepository<CountryCounterEntity, CountryCode> getRepository() {
        return repository;
    }
}
