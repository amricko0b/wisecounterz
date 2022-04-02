package xyz.amricko0b.wisecounterz.data.jpa.repository;

import com.neovisionaries.i18n.CountryCode;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import xyz.amricko0b.wisecounterz.data.jpa.entity.CountryCounterEntity;

/**
 * Spring Data JPA repository for {@link xyz.amricko0b.wisecounterz.data.CountryCounter}.
 *
 * @author amricko0b
 */
@Repository
public interface CountryCounterRepository extends CrudRepository<CountryCounterEntity, CountryCode> {

    @Query(value = "select cast(pg_advisory_xact_lock(?1) as varchar)", nativeQuery = true)
    void lockOn(long countryCodeNumeric);

    @Modifying
    @Query(value = "update CountryCounter set counter = counter + 1 where countryCode = ?1")
    void increment(CountryCode countryCode);
}
