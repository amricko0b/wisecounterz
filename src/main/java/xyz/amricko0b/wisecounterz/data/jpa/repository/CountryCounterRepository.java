package xyz.amricko0b.wisecounterz.data.jpa.repository;

import com.neovisionaries.i18n.CountryCode;
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
}
