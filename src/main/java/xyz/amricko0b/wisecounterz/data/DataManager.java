package xyz.amricko0b.wisecounterz.data;

import java.util.Set;

/**
 * Data manager.
 * We use this to encapsulate actual implementation of Data-access layer.
 *
 * @param <T> actual data access DTO
 */
public interface DataManager<T> {

    /**
     * @return set of all objects in storage or empty set
     */
    Set<T> findAll();
}
