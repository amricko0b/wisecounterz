package xyz.amricko0b.wisecounterz.data.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import xyz.amricko0b.wisecounterz.data.DataManager;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Abstract superclass for all JPA base data managers.
 * Assumes that we use Spring Data JPA repositories.
 *
 * @param <T>  data access DTO class
 * @param <E>  actual entity class
 * @param <ID> actual identifier
 */
public abstract class AbstractJpaManager<T, E extends T, ID> implements DataManager<T> {

    /**
     * Supports current transaction or runs new.
     * <p>
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Set<T> findAll() {
        final var it = getRepository().findAll();
        return StreamSupport.stream(it.spliterator(), false)
                .collect(Collectors.toSet());
    }

    abstract protected CrudRepository<E, ID> getRepository();
}
